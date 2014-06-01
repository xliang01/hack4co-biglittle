$(document).ready(function() {
  var json_url = "http://ec2-54-200-250-50.us-west-2.compute.amazonaws.com:8080/sports-buddies/api/bigs";
  var big = {};
  
  $(".addForm").on("submit", function(e) {
    e.preventDefault();
    big.userId = -1;
    big.firstName = $("#firstName").val();
    big.lastName = $("#lastName").val();
    big.email = $("#email").val();
    big.address = {};
    big.address.line1 = $("#line1").val();
    big.address.line2 = $("#line2").val();
    big.address.city = $("#city").val();
    big.address.state = $("#state").val();
    big.address.zip = $("#zip").val();
    var big_data = JSON.stringify(big);
    $.ajax({
      url: json_url,
      type: 'POST',
      data: big_data,
      dataType: "json",
      crossDomain: true,
      contentType: "application/json",
      })
      .done(function(result) {
        $("#page-content").load("../bigs/index.html");
      })
      .error(function(result) {
        console.log(result);
      });
  });
});