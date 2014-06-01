$(document).ready(function() {
  var json_url = "http://ec2-54-200-250-50.us-west-2.compute.amazonaws.com:8080/sports-buddies/api/events";
  $.getJSON(json_url, function(data) {
    $.each(data, function(i, obj) {
      $("#upcoming-events-table").append("<tr><td>"+obj.title+"</td><td>"+Math.floor(Math.random() * 10 + 1)+"</td></tr>");
    });
  });
});