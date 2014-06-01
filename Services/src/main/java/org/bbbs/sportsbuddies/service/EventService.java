package org.bbbs.sportsbuddies.service;

import java.util.List;

import org.bbbs.sportsbuddies.domain.Event;
import org.bbbs.sportsbuddies.domain.dao.EventDAO;

public class EventService extends BaseService {

	private EventDAO eventDAO;
	
	public EventService()
	{
		super();
		eventDAO = (EventDAO) context.getBean("EventDAO");
	}
	
    public List<Event> getAllEvents() {
       
        return eventDAO.getAll();
    }
    
    public Event getEvent(int id)
    {
    	EventDAO eventDAO = (EventDAO) context.getBean("EventDAO");
    	return eventDAO.getById(id);
    }
	
}
