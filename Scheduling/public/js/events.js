$(document).ready(function() {
  var json_url = "https://bbbs.firebaseio.com/events.json";
  var event = {};
  
  $("#event-form").on("submit", function(e) {
    e.preventDefault();
    event.title = $("#title").val();
    event.location = $("#location").val();
    event.description = $("#description").val();
    event.minParticipants = $("#minParticipants").val();
    event.maxParticipants = $("#maxParticipants").val();
    event.startTime = $("#startTime").val();
    event.endTime = $("#endTime").val();
    $.post("https://bbbs.firebaseio.com/events.json", event)
  });
  
  $.getJSON(json_url, function(data) {
    $.each(data, function(i, item){
      $("#events-data").append("<tr><td><a href='123'>"+item.title+"</a></td><td>Coordinator Here</td><td>"+item.startTime+"</td><td>###</td><td><a href='#' class='btn btn-xs btn-success'>Edit</a> <a href='#' class='btn btn-xs btn-danger'>Delete</a></td></tr>");
    });
  });
  
  $("#add-event").on("click", function(e) {
    e.preventDefault();
    $("#page-container").load("../events/new.html");
  });
  
  $("#events-data").on("click", "tr td a", function(e) {
    e.preventDefault();
    var json_click = $(this).attr("href");
    $.getJSON(json_url, function(data) {
      var json_data = _.where(data, {id: json_click});
      $("#page-container").load("../events/show.html");
      $(document).ajaxStop(replaceText);
      function replaceText() {
        $(".title").text("Title: ");
        $(".location").html("Location: ");
        $(".minPar").html("Minimum Participants: ");
        $(".maxPar").html("Maximum Partcipants: ");
        $(".desc").html("Descripton: ");
        $(".myActive").html("Active: ");
        $(".startTime").html("Start Time: ");
        $(".endTime").html("End Time: ");
        $(".creatDate").html("Date: ");
        $(".lastEdit").html("Last Edit: ");
      }
    });
  });
});