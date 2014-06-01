package org.bbbs.sportsbuddies.service;

import java.util.ArrayList;
import java.util.List;

import org.bbbs.sportsbuddies.domain.Event;
import org.bbbs.sportsbuddies.domain.dao.EventDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EventService {

	ApplicationContext context;
	public EventService()
	{
       context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<Event>();
        EventDAO eventDAO = (EventDAO) context.getBean("EventDAO");
       
        return eventDAO.getAll();
    }
    
    public Event getEvent(int id)
    {
    	EventDAO eventDAO = (EventDAO) context.getBean("EventDAO");
    	return eventDAO.getById(id);
    }
	
}
