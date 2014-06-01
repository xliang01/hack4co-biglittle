function personaSetup(afterLogin,afterLogout) {
   var signinLink = document.getElementById('signin');
   if (signinLink) {
     signinLink.onclick = function() { navigator.id.request(); };
     if(afterLogin) {
       afterLogin();
     }
   }

   var signoutLink = document.getElementById('signout');
   if (signoutLink) {
     signoutLink.onclick = function() { navigator.id.logout(); };
     if(afterLogout) {
       afterLogout();
     }
   }

   var toggleLoginLinks = function(email) {
     var hideLink = '#signin';
     var showLink = '#signout';

     if(email==null) {
       email = 'Guest';
       hideLink = '#signout';
       showLink = '#signin';
     }

     $(hideLink).hide();
     $(showLink).show();
     $('#usergreeting').html('Welcome ' + email + '!');
   };

   navigator.id.watch({
     onlogin : function(assertion) { 
       $.ajax({ 
          type: 'POST',
          url: '/session', 
          data: {assertion: assertion},
          success: function(res, status, xhr) { 
            //alert('login success : ' + JSON.stringify(res)); 
            toggleLoginLinks(res.email.substring(0,res.email.indexOf('@')));

            if(afterLogin) {
              afterLogin();
            }
          },
          error: function(xhr, status, err) {
            //alert("Login failure: " + err);
            toggleLoginLinks(null);
            navigator.id.logout();
           }
       });
     },
     onlogout : function(assertion) {
       $.ajax({
         type: 'DELETE',
         url: '/session', 
         success: function(res, status, xhr) { 
           //alert("Logout success: " + JSON.stringify(res)); 
           toggleLoginLinks(null);

           if(afterLogout) {
             afterLogout();
           }
         },
         error: function(xhr, status, err) { 
           //alert("Logout failure: " + err); 
           toggleLoginLinks(null);

           if(afterLogout) {
             afterLogout();
           }
         }
       });
     }
   });
}
