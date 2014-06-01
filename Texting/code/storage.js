var appConfig = require('./storage-config'),
    request = require('request'),
    crypto = require('crypto'),
    ObjectID = require('mongodb').ObjectID;

var storagePlugin = {};

var db = undefined;

var init = function(app, dbConn) {
  db = dbConn;
};  
storagePlugin.init = init;

var cryptKey = crypto.createHash('sha256').update(appConfig.secret).digest();

var fixSalt = function(salt) {
  if(!salt) {
    throw 'Encryption Fail';
  }

  salt = salt + '';
  while(salt.length<16) {
    salt = salt + salt;
  }

  return salt.substring(0,16);
}

var encrypt = function(input, salt) {
  salt = fixSalt(salt);
  var encipher = crypto.createCipheriv('aes-256-cbc', cryptKey, salt);
  var encrypted = encipher.update(input, 'utf8', 'base64');
  encrypted += encipher.final('base64');
  return encrypted;
};
storagePlugin.encrypt = encrypt;

var decrypt = function(input, salt) {
  salt = fixSalt(salt);
  var decipher = crypto.createDecipheriv('aes-256-cbc', cryptKey, salt);
  var decrypted = decipher.update(input, 'base64', 'utf8');
  decrypted += decipher.final('utf8');
  return decrypted;
};
storagePlugin.decrypt = decrypt;

var findUserAccount = function(params,done) {
  db.collection('useraccounts').findOne(params, function (err,user) {
    if(!err && user) {
      if(user.token) {
        user.token = decrypt(user.token, user.modifiedAt);
      }
      if(user.tokenSecret) {
        user.tokenSecret = decrypt(user.tokenSecret, user.modifiedAt);
      }
    }
    done(err,user);
  });
};
storagePlugin.findUserAccount = findUserAccount;

var saveUserAccount = function(user,done) {
  user = JSON.parse(JSON.stringify(user));
  var token = null;
  var tokenSecret = null;

  if(user.token) {
    token = user.token;
    user.token = encrypt(user.token, user.modifiedAt);
  }
  if(user.tokenSecret) {
    tokenSecret = user.tokenSecret;
    user.tokenSecret = encrypt(user.tokenSecret, user.modifiedAt);
  }

  db.collection('useraccounts').save(user, {safe:true},function(err,user) {
    if(token) {
      user.token=token;
    }
    if(tokenSecret) {
      user.tokenSecret=tokenSecret;
    }

    done(err,user);
  });
};
storagePlugin.saveUserAccount = saveUserAccount;

var removeUserAccount = function(params,done) {
  db.collection('useraccounts').remove(params, {single:true, safe:true},done); 
};
storagePlugin.removeUserAccount = removeUserAccount;

var findUserHook = function(params,done) {
  var params = JSON.parse(JSON.stringify(params));
  if(params._id) {
    params._id = new ObjectID(params._id);
  }
  db.collection('userhooks').findOne(params,function(err,hook) {
    if(!err && hook && hook.login) {
      hook.login.name = decrypt(hook.login.name, hook.createdAt);
      hook.login.pass = decrypt(hook.login.pass, hook.createdAt);
    }
    done(err,hook);
  });
};
storagePlugin.findUserHook = findUserHook;

var findUserHooks = function(params,done) {
  if(params._id) {
    params._id = new ObjectID(params._id);
  }
  db.collection('userhooks').find(params).toArray(function(err,hooks) { 
    if(!err && hooks && Array.isArray(hooks)) {
      for(var i=0;i<hooks.length;i++) {
        var hook = hooks[i];
        if(hook && hook.login) {
          hook.login.name = decrypt(hook.login.name, hook.createdAt);
          hook.login.pass = decrypt(hook.login.pass, hook.createdAt);
        }
      }
    }
    done(err,hooks);
  });
};
storagePlugin.findUserHooks = findUserHooks;

var saveUserHook = function(hook,done) {
  hook = JSON.parse(JSON.stringify(hook));
  var login = null; 

  if(hook.login) {
    login = JSON.parse(JSON.stringify(hook.login));

    hook.login.name = encrypt(hook.login.name, hook.createdAt);
    hook.login.pass = encrypt(hook.login.pass, hook.createdAt);
  }

  db.collection('userhooks').save(hook, {safe:true},function(err,hook) {
    if(login) {
      hook.login = login;
    }
    done(err,hook);
  });
};
storagePlugin.saveUserHook = saveUserHook;

var removeUserHook = function(params,done) {
  if(params._id) {
    params._id = new ObjectID(params._id);
  }
  db.collection('userhooks').remove(params, {single:true, safe:true},done); 
};
storagePlugin.removeUserHook = removeUserHook;

var ingestUserData = function(meta,data, done) {
  var metaInstance = { 
    'user' : meta.user,
    'api' : meta.api,
    'url' : meta.url,
    'createTime' : new Date().getTime()
  };

  if(Array.isArray(data)) {
    for(var i=0;i<data.length;i++) {
      data[i]._meta = metaInstance;
    }
  }
  else {
    data._meta = metaInstance;
  }
                   
  db.collection('temporary').save(data, function(err,doc){
     if(err) {
       console.log('Failed to save temp data: ' + JSON.stringify(err));
       done(undefined);
     } else {
       if(Array.isArray(doc)) {
         var docs = []; // should use _ here
         for(var i=0;i<doc.length;i++) {
           docs.push(doc[i]._id); 
         }
         done(docs);
       }
       else {
         done([doc._id]);
       }
     }
  });
};
storagePlugin.ingestUserData = ingestUserData;

var queryUserData = function(meta,query,done) {

  if(!meta || !meta.user || meta.user.trim().length==0) {
     console.log('Queries must have user');
     done(undefined);
     return;
  }

  var data = {};

  // inspect and translate the params for query
  try {
    for(var key in query) {
      var value = query[key];
      var operator = 'eq'; // equal is the default operator

      // key~op indicates which operator should be used.
      if(key.match(/^.+\~(eq|lt|lte|gt|gte|ne|in|nin|re|rei)$/i)) { // NOTE: case insensitive here
        var delimPosition = key.lastIndexOf('~');
        operator = key.substring(delimPosition+1).toLowerCase();
        key = key.substring(0,delimPosition);
      }

      // make sure the data is clean
      if(!(typeof key === "string"            // key is string
         && typeof value  === "string"      // value is string
         && key.trim().length > 0           // key must not be empty
         && key.match(/^[a-zA-Z0-9._-]+$/)   // key only alpha, numeric, dot, underscore, dash
         && value.trim().length > 0)) {      // value must not be empty

        throw ('invalid parameter: ' + key + ' = ' + value);
      }

      // error if similar condition already defined or not compatible
      if(data[key] && 
         (operator.match(/^(eq|re|rei)$/)
          || typeof(data[key]) === "string" 
          || data[key] instanceof Regexp)) {

          throw  'not handling compound conditions for eq, re, or rei operators';
      }

      // cast values. take into account that value may need to be split
      var castValues = operator.match(/^(in|nin)$/) ? value.split('~') : [value];
      for(var v=0;v<castValues.length;v++) { 
        var castValue = castValues[v];

        // handle casting
        if(castValue.match(/^[+-]?[0-9]+$/)) {
          castValue = parseInt(castValue); 
        }
        else if(castValue.match(/^[+-]?[0-9]+\.[0-9]+$/)) {
          castValue = parseFloat(castValue); 
        }
        else if(key === '_id') {
          castValue = new ObjectID(castValue);
        }

        castValues[v] = castValue;
      }
      value = castValues.length==1 ? castValues[0] : castValues; 

      if(operator === "eq") {
        data[key] = value;
      }
      else if(operator === "re" || operator === "rei") {
        try {
          var regex;
          if(operator === "rei") {
            regex = new RegExp(value,"i");
          }
          else {
            regex = new RegExp(value);
          }
          data[key] = regex;
        }
        catch(e) {
          throw ('invalid regex: ' + value);
        }
      }
      else {
        var conditionKey = '$' + operator;
        var conditionValue;
        // account for array values for IN and NOT IN
        if(operator.match(/^(in|nin)$/)) {
          if(Array.isArray(value)) {
            conditionValue = value;
          }
          else { // it needs to be an array
            conditionValue = [value];
          }
        }
        else {
          conditionValue = value;
        }
        
        //account for multiple conditions
        if(!data[key]) {
          data[key] = {};
        }
        data[key][conditionKey] = conditionValue;
      }
    }
    console.log("QUERY = " + JSON.stringify(data));
  }
  catch(ex) {
    console.log('INVALID QUERY: ' + ex);
    done(undefined);
    return;
  }

// this must be last
data['_meta.user'] = meta.user;

var options = {
  "limit": 20
};
                 
db.collection('temporary').find(data,options).toArray(function(err,docs){
   if(err) {
     console.log('Failed to find matching data: ' + JSON.stringify(err));
     done(undefined);
   } 
   else {
     done(docs);
   }
});

};
storagePlugin.queryUserData = queryUserData;

module.exports = storagePlugin;
