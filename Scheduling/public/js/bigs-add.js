$(document).ready(function() {
  
  var big = {};
  
  $(".addForm").on("submit", function(e) {
    e.preventDefault();
    big.firstName = $("#firstName").val();
    big.lastName = $("#lastName").val();
    big.email = $("#email").val();
    big.address.line1 = $("#line1").val();
    big.address.line2 = $("#line2").val();
    big.address.city = $("#city").val();
    big.address.state = $("#state").val();
    big.address.zip = $("#zip").val();
    console.log(big);
  });
});