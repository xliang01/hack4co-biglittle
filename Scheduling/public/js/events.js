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
      contentType: "application/json"
    })
    .done(function(result) {
      $("#page-content").load("../events/index.html");
    })
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
      data: updated_event
    })
    .done(function(result) {
      $("#page-content").load("../events/index.html");
    })
  });
  
  /* DELETE */
  
  $("#events-data").on("click", "#delete-event-btn", function(e) {
    e.preventDefault();
    var delete_id = $(this).attr("href");
    $.ajax({
      url: json_url+"/"+delete_id,
      type: "DELETE",
      crossDomain: true
    })
    .done(function(result) {
      location.reload();
    })
  });
  
  /* INDEX */
  
  $.getJSON(json_url, function(data) {
    $.each(data, function(i, item){
      var unformattedDate = new Date(item.startTime);
      var formattedDate = unformattedDate.toDateString();
      $("#events-data").append("<tr><td><a class='show' href='"+item.eventId+"'>"+item.title+"</a></td><td>"+formattedDate+"</td><td>###</td><td><a href='"+item.eventId+"' class='btn btn-xs btn-success'>Edit</a> <a href='"+item.eventId+"' id='delete-event-btn' class='btn btn-xs btn-danger'>Delete</a></td></tr>");
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
        var startDateFormatted = new Date(json_data.startTime).toDateString();
        var startTimeFormatted = new Date(json_data.startTime).toTimeString();
        var endDateFormatted = new Date(json_data.endTime).toDateString();
        var endTimeFormatted = new Date(json_data.endTime).toTimeString();
        $(".title").text(json_data.title);
        $(".location").text(json_data.location);
        $(".minPar").text(json_data.minParticipants);
        $(".maxPar").text(json_data.maxParticipants);
        $(".desc").text(json_data.description);
        $(".startTime").text(startDateFormatted+" "+startTimeFormatted);
        $(".endTime").text(endDateFormatted+" "+endTimeFormatted);
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