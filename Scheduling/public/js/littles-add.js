$(document).ready(function() {
  var json_url = "http://ec2-54-200-250-50.us-west-2.compute.amazonaws.com:8080/sports-buddies/api/littles";
  var little = {};
  
  $(".addForm").on("submit", function(e) {
    e.preventDefault();
    little.firstName = $("#firstName").val();
    little.lastName = $("#lastName").val();
    little.email = $("#email").val();
    little.address = {};
    little.address.line1 = $("#line1").val();
    little.address.line2 = $("#line2").val();
    little.address.city = $("#city").val();
    little.address.state = $("#state").val();
    little.address.zip = $("#zip").val();
    var little_data = JSON.stringify(little);
    $.ajax({
      url: json_url,
      type: 'POST',
      data: little_data,
      dataType: "json",
      crossDomain: true,
      contentType: "application/json",
      success: function(result) {
        // stuff
      }
    });
  });
});