package org.bbbs.sportsbuddies.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.bbbs.sportsbuddies.domain.Event;

@RestController
@RequestMapping("/api/")
public class EventController {
	
    @RequestMapping(value = "events/{id}", 
    				method = RequestMethod.GET, 
    				headers="Accept=application/json")
    public Event getEvent(@PathVariable int id) {
    	
        return new Event();
    }

    @RequestMapping(value = "events", 
    				method = RequestMethod.GET, 
    				headers="Accept=application/json")
    public List<Event> getEvents() {
        
    	List<Event> events = new ArrayList<Event>();

        events.add(new Event());
        events.add(new Event());

        return events;
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