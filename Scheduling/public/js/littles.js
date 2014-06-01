$(document).ready(function() {
  var json_url = "https://bbbs.firebaseio.com/littles.json";
  
  $.getJSON(json_url, function(data) {
    $.each(data, function(i, item){
      if (item.address.line2 != "") {
        $("#littles-data").append("<tr><td>"+item.firstName+" "+item.lastName+"</td><td>"+item.address.line1+"<br />"+item.address.city+", "+item.address.state+" "+item.address.zip+"</td><td>ty</td><td>"+item.email+"</td><td><a href='#' class='btn btn-xs btn-success'>Edit</a> <a href='#' class='btn btn-xs btn-danger'>Delete</a></td></tr>");
      } else {
        $("#littles-data").append("<tr><td>"+item.firstName+" "+item.lastName+"</td><td>"+item.address.line1+"<br />"+item.address.line2+" "+item.address.city+", "+item.address.state+" "+item.address.zip+"</td><td>ty</td><td>"+item.email+"</td><td><a href='#' class='btn btn-xs btn-success'>Edit</a> <a href='#' class='btn btn-xs btn-danger'>Delete</a></td></tr>");
      }
    });
  });
  
  $(".add-btn").click(function(e) {
    console.log("hjsgjfhs");
    e.preventDefault();
    $("#page-container").load("../littles/add.html");
  });
});