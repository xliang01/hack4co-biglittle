$(document).ready(function() {

  $.ajaxSetup ({
    cache: false
  });
  
  /* Events */
  $("#tabs ul li a").click(function(e) {
    e.preventDefault();
    page = $(this).attr("href");
    $("#page-container").load(page + "/index.html");
  });
  
});