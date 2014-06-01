var otherSetupCallback, otherTeardownCallback;

function authSetup(otherSetup) {
  
  if(otherSetup) {
    otherSetupCallback = otherSetup;
  }
  else {
    otherSetup = otherSetupCallback;
  }

  var authArea = document.getElementById('auth');
  $.ajax({ 
    type: 'GET',
    url: '/auth', 
    success: function(res, status, xhr) { 
      var html='';
      if(Array.isArray(res)) {
        for(var i=0;i<res.length;i++) {
          if(res[i].isWebhook) {
            continue;
          }

          var apiId = res[i].id;
          var apiName = res[i].name;
          var method = res[i].enabled ? 'delete' : 'post';
          var toggle = res[i].enabled ? 'disable' : 'enable';
          var disabled = res[i].authRequired ? '' : 'disabled ';
          html += '<span class="auth-switch">'
          html += '<form name="'+apiId+'" method="'+method+'" action="/auth/'+apiId+'">';
          var classList= toggle+'-auth';
          html += '<input type="button" class="'+classList+'" title="'+apiName+'" value="'+apiId+'" onclick="'+toggle+'Auth(this.form)" '+disabled+'/>';
          html += '</form></span>';
        }
        otherSetup(res);
      }
      authArea.innerHTML = html; 
    },
    error: function(xhr, status, err) {
      authArea.innerHTML = ''; 
    }
  });
}

function authTeardown(otherTeardown) {

  if(otherTeardown) {
    otherTeardownCallback = otherTeardown;
  }
  else {
    otherTeardown = otherTeardownCallback;
  }

  var authArea = document.getElementById('auth');
  authArea.innerHTML = ''; 
  otherTeardown(undefined);
}

function enableAuth(form) {
  form.submit();
}

function disableAuth(form) {
  $.ajax({ 
    type: 'DELETE',
    url: form.action, 
    success: function(res, status, xhr) { 
      authSetup();  
    },
    error: function(xhr, status, err) {
    }
  });
}
