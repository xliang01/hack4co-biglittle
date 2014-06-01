var appConfig = require('./passport-config'),
    passport = require('passport'),
    passportOauth = require('passport-oauth'),
    OAuthStrategy = passportOauth.OAuthStrategy,
    OAuth2Strategy = passportOauth.OAuth2Strategy,
    request = require('request');

var passportPlugin = {};

var storage = undefined;

var hasApi = function(apiName) {
  return typeof(appConfig[apiName]) === "object" 
         && appConfig[apiName].enabled === true;
};
passportPlugin.hasApi = hasApi;

var findUser = function(apiName,req,callback) { 
  var userId = undefined;
  if(req.session.user) {
    userId = req.session.user.id;
  } 
  else if(req.user) { // web hooks use basic auth
    userId = req.user.id;
  }

  if(!userId || typeof(userId) !== "string") {
    callback(undefined);
  }
  else if(!hasApi(apiName)) {
    callback(undefined);
  }
  else {
    if(appConfig[apiName].type == 'api-key' ||
       appConfig[apiName].type == 'public') {
      callback({ api : apiName });
    }
    else if(appConfig[apiName].type == 'composite') {
      callback({ isComposite : true, api : apiName });
    }
    else if(appConfig[apiName].type == 'webhook') {
      callback({ isWebhook : true, '_api' : apiName });
    }
    else {
      storage.findUserAccount({'_login' : userId, '_api' : apiName}, function(err,user){
        // todo - error handling
        callback(user);
      });
    }
  }
};
passportPlugin.findUser = findUser; 

var removeUser = function(apiName,req,callback) { 
  if(req.session.user == undefined) {
    callback(false);
  }
  else if(!hasApi(apiName)) {
    callback(false);
  }
  else {
    findUser(apiName,req, function(user) {
      if(user && user._api) { // these can be deleted
        storage.removeUserAccount({'_login' : req.session.user.id, '_api' : apiName}, function(err,count){
          callback(err, count);
        });
      }
      else {
        callback(false);
      }
    });
  }
};
passportPlugin.removeUser = removeUser; 

var apiDetails = function(apiId) { 
  if(!hasApi(apiId)) {
    return undefined;
  }

  var apiConfig = appConfig[apiId];
  var apiName = apiConfig.name ? apiConfig.name : apiId;
  var hookFlag = apiConfig.type === 'webhook';
  var authFlag = (hookFlag || apiConfig.type.match(/^oauth/))?true:false;
  var api = { 'id' : apiId, 'name' : apiName, 'authRequired' : authFlag, 'isWebhook' : hookFlag }; 

  return api;
};
passportPlugin.apiDetails=apiDetails;

var allApiDetails = function() { 
  var apis = [];
  for(var apiName in appConfig) {
    var api = apiDetails(apiName);
    if(api) {
      apis.push(api);
    }
  }
  apis.sort(function(a,b) {
    if(a.id==b.id) return 0;
    if(a.id<b.id) return -1;
    else return 1;
  });
  return apis;
};
passportPlugin.allApiDetails=allApiDetails;

var init = function(app, dataStorage) {
  storage = dataStorage;

  app.use(passport.initialize());
  app.use(passport.session());

  // store user in persistent storage for passport
  passport.serializeUser(function(user, done) {
    //console.log('serialize user ' + user._id);

    user.modifiedAt = new Date().getTime();

    storage.saveUserAccount(user, function(err,doc) {
      // todo - error handling
      done(null, user._id);
    });
  });

  // retrieve user from persistent store for passport 
  passport.deserializeUser(function(id, done) {
    //console.log('deserialize user ' + id);

    storage.findUserAccount({'_id' : id}, function(err,user){
      // todo - error handling
      done(null, user);
    });
  });

  // load all app configs
  for(var apiName in appConfig) {
    if(hasApi(apiName)) {
      console.log('register = ' + apiName);
      register(apiName); 
    }
  }

};  
passportPlugin.init = init;

var register = function(apiName) {

  var strategyCallback = function(req, token, tokenSecret, profile, done) {
    var fakeUser = { 'token' : token,
                     'tokenSecret' : tokenSecret }; 
    var options =  { 'method' : 'GET', 
                     'uri' : appConfig[apiName].defaultLoadUrl };
    var callback = function(user, data) {
      var fullUser = null; 

      //var profileUser = appConfig[apiName].userElementName?data[appConfig[apiName].userElementName]:data;
      var profileUserId = null;
      if(data) {
        var userIdPath = appConfig[apiName].userIdPath.split('.'); 
        var profileUser = data;
        for(var i=0;i<userIdPath.length;i++) {
          if(i==userIdPath.length-1) {
            profileUserId = profileUser[userIdPath[i]];
          }
          else {
            profileUser = profileUser[userIdPath[i]];
          }
        }
      }

      if(profileUserId) { 
        fullUser = {};
        fullUser.profile = data;
        fullUser._id = apiName + "~" + profileUserId; 
        fullUser.token = fakeUser.token;
        fullUser.tokenSecret = fakeUser.tokenSecret;
        fullUser._login = req.session.user.id;
        fullUser._api = apiName;
        fullUser.createdAt = new Date().getTime();
      } 
      done(null, fullUser);
    };
    handleRequest(apiName,fakeUser,options,callback);  
  };

  var apiCallbackUrl = appConfig._host + '/auth/' + apiName  + '/callback';
  if(appConfig[apiName].type == 'oauth-1.0') {
    // setup oauth1 through passport
    passport.use(apiName, 
      new OAuthStrategy({
          userAuthorizationURL: appConfig[apiName].authorizationUrl,
          requestTokenURL: appConfig[apiName].requestTokenUrl,
          accessTokenURL: appConfig[apiName].accessTokenUrl,
          consumerKey: appConfig[apiName].clientId,
          consumerSecret: appConfig[apiName].clientSecret,
          callbackURL: apiCallbackUrl,
          passReqToCallback: true
      }, strategyCallback
    ));
  }
  else if(appConfig[apiName].type == 'oauth-2.0') {
    // setup oauth2 through passport
    passport.use(apiName, 
      new OAuth2Strategy({
          authorizationURL: appConfig[apiName].authorizationUrl,
          tokenURL: appConfig[apiName].accessTokenUrl,
          clientID: appConfig[apiName].clientId,
          clientSecret: appConfig[apiName].clientSecret,
          callbackURL: apiCallbackUrl, 
          passReqToCallback: true,
          customHeaders: { 'User-Agent' : 'node-oauth' }
      }, strategyCallback
    ));
  }
  else if(appConfig[apiName].type == 'public') {
    // no registration action necessary
  } 
  else if(appConfig[apiName].type == 'api-key') {
    // no registration action necessary
  } 
  else if(appConfig[apiName].type == 'composite') {
    // no registration action necessary
  } 
  else if(appConfig[apiName].type == 'webhook') {
    // no registration action necessary
  } 
  else {
    console.log(apiName + " has unknown auth type of " + appConfig[apiName].type);
  }
};
passportPlugin.register = register;

// simplified request handling
var handleRequest = function(apiName, user, options, callback) {
  console.log('handling ' + options.method + ' request to '  + apiName + ': ' + options.uri);

  if(options.method.toUpperCase() !== 'GET') {
    console.log('Only handling GET request right now');
    callback(user,null);
    return;
  }

  var getCallback = function(error, body, response) {
    if(error) {
      console.log('handle request error ' + JSON.stringify(error));
      callback(user,null);
      return;
    }

    if (response.statusCode === 200) {
      //console.log("RESULT=" + body);
      callback(user,JSON.parse(body));
    } else {
      console.log('handle request error ' + response.statusCode);
      callback(user,response.statusCode);
    }
  };

  var uri = appConfig[apiName].baseUrl + options.uri;
  if(appConfig[apiName].type == 'oauth-1.0') {
    passport._strategy(apiName)._oauth.get(uri, user.token, user.tokenSecret, getCallback);
  }
  else if(appConfig[apiName].type == 'oauth-2.0') {
    passport._strategy(apiName)._oauth2.get(uri, user.token, getCallback);
  }
  else if(appConfig[apiName].type == 'api-key') {
    var headers =  {'Content-Type': 'application/json'};
    if(appConfig[apiName].isKeyInUrl === true) {
      uri += uri.indexOf('?')==-1 ? '?' : '&';
      uri += appConfig[apiName].keyName + '=' + appConfig[apiName].keyValue;
    }
    else {
      headers[appConfig[apiName].keyName] = appConfig[apiName].keyValue;
    }
    var requestOptions = { 'uri' : uri, 'method' : 'GET', 'headers' : headers };
    request(requestOptions,function (error, body, response) { getCallback(error,response,body) });
  }
  else if(appConfig[apiName].type == 'public') {
    var requestOptions = { 'uri' : uri, 'method' : 'GET', 'headers' : headers };
    request(requestOptions,function (error, body, response) { getCallback(error,response,body) });
  }
  else {
    console.log('No request handling for ' + appConfig[apiName].type);
    return;
  }
};
passportPlugin.handleRequest = handleRequest;

var handleComposite = function(apiName,options,req,done) {
  var compositeCallback = function(error, body) {
    if(error) {
      console.log('handle request error ' + JSON.stringify(error));
      done(null);
      return;
    }

    done(body);
  };

  var compositeAccess = {
    params : req.query,
    api : function(accessApiName,accessOptions, accessCallback) {
      var compositeAccessCallback = function(user) {
        handleRequest(accessApiName, user, accessOptions, function (user,data) {
         accessCallback(data); 
        });
      };
      findUser(accessApiName,req,compositeAccessCallback);
    },
    query : function(queryParams, queryCallback) {
      var meta = { 
        'user' : req.session.user.id
      };
      storage.queryUserData(meta, queryParams,queryCallback);
    },
    ingest : function(data, ingestCallback) {
      var meta = { 
        'user' : req.session.user.id,
        'api' : apiName,
        'url' : options.uri
      };
      storage.ingestUserData(meta, data, ingestCallback);
    }
  };

  appConfig[apiName].buildComposite(compositeAccess, options, compositeCallback);
};
passportPlugin.handleComposite = handleComposite;

var handleWebhook = function(apiName,options,req,done) {
  var webhookCallback = function(error, body) {
    if(error) {
      console.log('handle webhook request error ' + JSON.stringify(error));
      done(null);
      return;
    }

    done(body);
  };

  var webhookAccess = {
    params : req.query,
    body : req.body,
    api : function(accessApiName,accessOptions, accessCallback) {
      var webhookAccessCallback = function(user) {
        handleRequest(accessApiName, user, accessOptions, function (user,data) {
         accessCallback(data); 
        });
      };
      findUser(accessApiName,req,webhookAccessCallback);
    },
    query : function(queryParams, queryCallback) {
      var meta = { 
        'user' : req.user.id 
      };
      storage.queryUserData(meta, queryParams, queryCallback);
    },
    ingest : function(data, ingestCallback) {
      var meta = { 
        'user' : req.user.id,
        'api' : apiName,
        'url' : options.uri
      };
      storage.ingestUserData(meta, data, ingestCallback);
    }
  };

  appConfig[apiName].buildWebhook(webhookAccess, options, webhookCallback);
};
passportPlugin.handleWebhook = handleWebhook;

var auth = function(apiName,streams) {
  //console.log('auth attempt');
  passport.authenticate(apiName)(streams.req,streams.res,streams.next);
}
passportPlugin.auth = auth;

var authCallback = function(apiName,urls,streams) {
  //console.log('authCallback attempt');
  passport.authenticate(apiName, 
                          { 
                            'successRedirect' : urls.successUrl, 
                            'failureRedirect' : urls.failureUrl 
                          }
                       )(streams.req,streams.res,streams.next);
}
passportPlugin.authCallback = authCallback;

var findParamStringInUri = function(paramName, paramValue, uri) {
  var paramString = paramName + '=' + paramValue;
  var paramIndex = uri.indexOf(paramString);

  if(paramIndex == -1) {
    return '';
  }
  else {
    paramIndex--;
  }

  var paramPrefix = uri.charAt(paramIndex);
  if(paramPrefix == '&') {
    paramString = '&' + paramString;
  }
  else {
    if(paramPrefix == '?' && paramIndex+paramString.length == uri.length-1) {
      paramString = '?' + paramString; 
    }
    else {
      paramString = paramString + '&'; 
    }
  }
  return paramString;
};
passportPlugin.findParamStringInUri=findParamStringInUri;

module.exports = passportPlugin;
