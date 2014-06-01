$(document).ready(function() {
  var json_url = "http://ec2-54-200-250-50.us-west-2.compute.amazonaws.com:8080/sports-buddies/api/bigs";
  
  $.getJSON(json_url, function(data) {
    $.each(data, function(i, item){
      $("#bigs-data").append("<tr><td>"+item.firstName+" "+item.lastName+"</td><td>"+item.address.line1+"<br />"+item.address.city+", "+item.address.state+" "+item.address.zip+"</td><td>ty</td><td>"+item.email+"</td><td><a href='' class='btn btn-success btn-xs'>Edit</a> <a href='' class='btn btn-danger btn-xs'>Delete</a></td></tr>");
    });
  });
  
  $("#bigs-data").on("click", "tr td a", function(e) {
    e.preventDefault();
    var json_click = $(this).attr("href");
    $.getJSON(json_url, function(data) {
      var json_data = _.where(data, {id: json_click});
      $("#page-container").load("../bigs/show.html");
      $(document).ajaxStop(replaceText);
      function replaceText() {
        $(".title").text("Title: ");
      };
    });
  });
  
$(".add-btn").click(function(e) {
    e.preventDefault();
    $("#page-container").load("../bigs/add.html");
  });
});