$(document).ready(function() {
  var json_url = "http://ec2-54-200-250-50.us-west-2.compute.amazonaws.com:8080/sports-buddies/api/pairs/";
  
  $("#add-pairs-btn").on("click", function(e) {
    e.preventDefault();
    $("#page-container").load("../pairs/new.html");
  });
  
  $.getJSON(json_url, function(data) {
    $.each(data, function(i, obj) {
      
    });
  });
});