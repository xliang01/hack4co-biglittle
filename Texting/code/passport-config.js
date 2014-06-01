var commonConfig = require('./common-config.js');
var request = require('request');

var appConfig = {};
appConfig._host = commonConfig.host;

appConfig.twillio = {};
appConfig.twillio.name='Twillio';
appConfig.twillio.username='<USERNAMEHERE>';
appConfig.twillio.password='<PASSWORDHERE>';
appConfig.twillio.phoneNumber='<PHONENUMBERHERE>';
appConfig.twillio.testPhoneNumber='<PHONENUMBERHERE>';
appConfig.twillio.enabled=true;
appConfig.twillio.type = 'webhook';
appConfig.twillio.buildWebhook = function(access, options, done) {
  // access: params, api(name,options,callback), query(params,callback), ingest(data,callback)
  // options: method, uri 

  console.log('execute twillio with ' + options.method + ' on uri: ' + options.uri);

  if(options.method==='GET') {
    var queryParams = access.params;
    queryParams['_meta.api'] = 'twillioback'; 
    access.query(queryParams, function(data) {
      done(null,data);
    });
    return;
  }

  var newMessage = {
    'From' : appConfig.twillio.phoneNumber,
    'To' : access.body.phone,
    'Body' : access.body.message
  };

  var requestOptions = { 
    'uri' : 'https://api.twilio.com/2010-04-01/Accounts/'+appConfig.twillio.username+'/Messages.json', 
    'method' : 'POST',
    'headers' : { 
      'Authorization' : 'Basic ' + new Buffer(appConfig.twillio.username+':'+appConfig.twillio.password).toString('base64') 
    },
    'form' : newMessage
  };
  request(requestOptions, function (error, response, body) {
    done(null, body)
  });
};

appConfig.twillioback = {};
appConfig.twillioback.name='Twillio';
appConfig.twillioback.enabled=true;
appConfig.twillioback.type = 'webhook';
appConfig.twillioback.buildWebhook = function(access, options, done) {
 console.log('RECEIVED TWILLIO CALLBACK: ' + JSON.stringify(access.body)); 

 access.ingest(access.body, function(data) {
   var ack = '<?xml version="1.0" encoding="UTF-8" ?><Response><Message>Ack</Message></Response>';
   done(null,ack);
 });
};

appConfig.sendgrid = {};
appConfig.sendgrid.name='Sendgrid';
appConfig.sendgrid.username='<USERNAMEHERE>';
appConfig.sendgrid.password='<PASSWORDHERE>';
appConfig.sendgrid.enabled=true;
appConfig.sendgrid.type = 'webhook';
appConfig.sendgrid.buildWebhook = function(access, options, done) {
  // access: params, api(name,options,callback), query(params,callback), ingest(data,callback)
  // options: method, uri 

  console.log('execute sendgrid with ' + options.method + ' on uri: ' + options.uri);

  if(options.method==='GET') {
    done(null,{});
    return;
  }

  var newMessage = {
    'api_user' : appConfig.sendgrid.username,
    'api_key' : appConfig.sendgrid.password,
    'to' : access.body.to,
    'toname' : access.body.to_name,
    'subject' : access.body.subject,
    'text' : access.body.text,
    'from' : 'noreply@dataup.co'
  };

  var requestOptions = { 
    'uri' : 'https://api.sendgrid.com/api/mail.send.json', 
    'method' : 'POST',
    'headers' : { 
    },
    'form' : newMessage
  };
  request(requestOptions, function (error, response, body) {
    done(null, body)
  });
};

module.exports = appConfig;
