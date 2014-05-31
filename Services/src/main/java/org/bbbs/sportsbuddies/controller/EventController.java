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

    @RequestMapping(value = "events/{id}", method = RequestMethod.GET,
                    headers="Accept=application/json")
    public Event getEvent(@PathVariable int id) {
        Event event = new Event("Raptors Rugby", "Infinity Park", "April 4");

        return event;
    }

    @RequestMapping(value = "events", method = RequestMethod.GET, headers="Accept=application/json")
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<Event>();

        events.add(new Event("Colorado Rockies", "Coors Field", "April 9"));
        events.add(new Event("Denver Broncos", "Mile High", "October 9"));

        return events;
    }

}