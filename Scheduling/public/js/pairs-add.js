$(document).ready(function() {
  
  var bigNames = [];
  var littleNames = [];
  
    $.getJSON('http://ec2-54-200-250-50.us-west-2.compute.amazonaws.com:8080/sports-buddies/api/bigs', function(data) {
      $.each(data, function(i, obj) {
        bigNames[i] = obj.firstName + " " + obj.lastname;
      });
    });
  
  $.getJSON('http://ec2-54-200-250-50.us-west-2.compute.amazonaws.com:8080/sports-buddies/api/littles', function(data) {
      $.each(data, function(i, obj) {
        littleNames[i] = obj.firstName + " " + obj.lastname;
      });
    });
  
  $( "#little" ).autocomplete({
    source: bigNames
  });
    
  $( "#big" ).autocomplete({
    source: littleNames
  });
});