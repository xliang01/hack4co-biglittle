$(document).ready(function() {
  var json_url_pair = "http://ec2-54-200-250-50.us-west-2.compute.amazonaws.com:8080/sports-buddies/api/pairs";
  var json_url_bigs = "http://ec2-54-200-250-50.us-west-2.compute.amazonaws.com:8080/sports-buddies/api/bigs";
  var json_url_littles = "http://ec2-54-200-250-50.us-west-2.compute.amazonaws.com:8080/sports-buddies/api/littles";
  var bigNames = [];
  var littleNames = [];
  var pair = {};
  
  $("#add-pair-form").on("submit", function(e) {
    e.preventDefault();
    pair.pairId = -1;
    pair.littleUserId = $("#little").val();
    pair.bigUserId = $("#big").val();
    var pair_data = JSON.stringify(pair);
    $.ajax({
      url: json_url_pair,
      type: "POST",
      data: pair_data,
      dataType: "json",
      crossDomain: true,
      contentType: "application/json",
      success: function(result) {
        // stuff
      }
    });
  });
  
  $.getJSON(json_url_bigs, function(data) {
    $.each(data, function(i, obj) {
      bigNames[i] = obj.firstName + " " + obj.lastname;
     });
   });
  
  $.getJSON(json_url_littles, function(data) {
    $.each(data, function(i, obj) {
      littleNames[i] = obj.firstName + " " + obj.lastName;
    });
  });
  
  $( "#little" ).autocomplete({
    source: bigNames
  });
    
  $( "#big" ).autocomplete({
    source: littleNames
  });
});