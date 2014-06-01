$(document).ready(function() {

  $.ajaxSetup ({
    cache: false
  });
  
  /* Events */
  $("#tabs ul li a").click(function(e) {
    e.preventDefault();
    $("#tabs ul li").removeClass("active");
    $(this).parent().addClass("active");
    page = $(this).attr("href");
    $("#page-container").load(page + "/index.html");
  });
  
});