package org.bbbs.sportsbuddies.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.bbbs.sportsbuddies.domain.Event;
import org.bbbs.sportsbuddies.service.EventService;

@RestController
@RequestMapping("/api/")
public class EventController {
	
	private EventService eventService;
	
	public EventController() {
		eventService = new EventService();
	}
	
    @RequestMapping(value = "events/{id}", 
    				method = RequestMethod.GET, 
    				headers="Accept=application/json")
    public Event getEvent(@PathVariable int id) {
        return eventService.getEvent(id);
    }

    @RequestMapping(value = "events", 
    				method = RequestMethod.GET, 
    				headers="Accept=application/json")
    public List<Event> getEvents() {
    	return eventService.getAllEvents();
    }

    @RequestMapping(value = "events", 
					method = RequestMethod.POST, 
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public void AddEvent(@RequestBody final Event event) {
    	eventService.saveEvent(event);
	}

    @RequestMapping(value = "events/{id}", 
					method = RequestMethod.PUT, 
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public void UpdateEvent(@PathVariable int id, @RequestBody final Event event) {
    	eventService.updateEvent(event);
	}
    
    @RequestMapping(value = "events/{id}", 
					method = RequestMethod.DELETE, 
					headers="Accept=application/json")
	public void DeleteEvent(@PathVariable int id) {
    	eventService.deleteEvent(id);
	}
}