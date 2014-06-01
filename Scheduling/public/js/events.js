$(document).ready(function() {
  var json_url = "http://ec2-54-200-250-50.us-west-2.compute.amazonaws.com:8080/sports-buddies/api/events";
  var event = {};
  
  $.ajaxSetup ({
    cache: false
  });
  
  /* CREATE */
  
  $("#event-form").on("submit", function(e) {
    e.preventDefault();
    event.eventId = -1;
    event.title = $("#title").val();
    event.location = $("#location").val();
    event.description = $("#description").val();
    event.minParticipants = $("#minParticipants").val();
    event.maxParticipants = $("#maxParticipants").val();
    event.startTime = $("#startTime").val();
    event.endTime = $("#endTime").val();
    var event_data = JSON.stringify(event);
    $.ajax ({
      url: json_url,
      type: 'POST',
      data: event_data,
      dataType: "json",
      crossDomain: true,
      contentType: "application/json",
      success: function(result) {
        // stuff
      }
    });
  });
  
  /* UPDATE */
  
  $("#event-update-form").on("submit", function(e) {
    e.preventDefault();
    var updated_event = {};
    event.title = $("#title").val();
    event.location = $("#location").val();
    event.description = $("#description").val();
    event.minParticipants = $("#minParticipants").val();
    event.maxParticipants = $("#maxParticipants").val();
    event.startTime = $("#startTime").val();
    event.endTime = $("#endTime").val();
    $.ajax({
      url: ''+id,
      type: 'PUT',
      data: updated_event,
      success: function(result) {
        // Stuff
      }
    });
  });
  
  /* DELETE */
  
  $("#btn-delete").on("click", function(e) {
    id = $(this).attr("href");
    $.ajax({
      url: ''+id,
      type: 'DELETE',
      success: function(result) {
        // Stuff here
      }
    });
  });
  
  /* INDEX */
  
  $.getJSON(json_url, function(data) {
    $.each(data, function(i, item){
      $("#events-data").append("<tr><td><a class='show' href='"+item.eventId+"'>"+item.title+"</a></td><td>Coordinator Here</td><td>"+item.startTime+"</td><td>###</td><td><a href='"+item.eventId+"' class='btn btn-xs btn-success'>Edit</a> <a href='"+item.eventId+"'class='btn btn-xs btn-danger'>Delete</a></td></tr>");
    });
  });
  
  $("#add-event-btn").on("click", function(e) {
    e.preventDefault();
    $("#page-container").load("../events/new.html");
  });
  
  $("#events-data").on("click", "tr td .show", function(e) {
    e.preventDefault();
    var json_click = $(this).attr("href");
    $.getJSON(json_url, function(data) {
      var json_data; 
      console.log(data);
      $.each(data, function(i, obj) { if (obj.eventId == json_click) { json_data = obj; } });
      $("#page-container").load("../events/show.html");
      console.log(json_data);
      $(document).ajaxStop(replaceText);
      function replaceText() {
        $(".title").text(json_data.title);
        $(".location").text(json_data.location);
        $(".minPar").text(json_data.minParticipants);
        $(".maxPar").html(json_data.maxParticipants);
        $(".desc").html(json_data.description);
        $(".startTime").html(json_data.startTime);
        $(".endTime").html(json_data.endTime);
      }
    });
  });
  
  /*
  
  $("").on("click", function(e) {
    e.preventDefault();
  });
  
  $("").on("click", function(e) {
    e.preventDefault();
    alert("jdhsbghmbdsjbgjhbnsxdf,");
  });
  
  */
});