$(document).ready(function() {
  $(".add").on("click", function(e) {
    e.preventDefault();
    $("#page-content").load("../pairs/new.html");
  });
});