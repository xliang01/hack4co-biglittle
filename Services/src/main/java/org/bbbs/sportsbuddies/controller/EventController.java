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

	EventService eventService = new EventService();
	
    @RequestMapping(value = "events/{id}", method = RequestMethod.GET,
                    headers="Accept=application/json")
    public Event getEvent(@PathVariable int id) {
        
        return eventService.getEvent(id);
    }

    @RequestMapping(value = "events", method = RequestMethod.GET, headers="Accept=application/json")
    public List<Event> getAllEvents() {
       
        return eventService.getAllEvents();
    }

}