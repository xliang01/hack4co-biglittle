package org.bbbs.sportsbuddies.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.bbbs.sportsbuddies.domain.Event;
import org.bbbs.sportsbuddies.service.EventService;

@RestController
@RequestMapping("/api/")
public class EventController {
	
	private EventService eventService = new EventService();
	
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
					headers="Accept=application/json")
	public void AddEvent() {
	
	}

    @RequestMapping(value = "events/{id}", 
					method = RequestMethod.PUT, 
					headers="Accept=application/json")
	public void UpdateEvent(@PathVariable int id) {
	
	}
    
    @RequestMapping(value = "events/{id}", 
					method = RequestMethod.DELETE, 
					headers="Accept=application/json")
	public void DeleteEvent(@PathVariable int id) {
	
	}
}