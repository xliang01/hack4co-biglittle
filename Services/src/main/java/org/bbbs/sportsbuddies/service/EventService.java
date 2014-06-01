package org.bbbs.sportsbuddies.service;

import java.util.List;

import org.bbbs.sportsbuddies.domain.Event;
import org.bbbs.sportsbuddies.domain.dao.EventDAO;

public class EventService extends BaseService {

	private EventDAO eventDAO;
	
	public EventService()
	{
		eventDAO = (EventDAO) context.getBean("EventDAO");
	}
	
    public List<Event> getAllEvents() 
    {   
        return eventDAO.getAll();
    }
    
    public Event getEvent(int id)
    {
    	return eventDAO.getById(id);
    }
    
    public void saveEvent(Event event) 
    {
    	if(event != null && event.getEventId() == -1) 
    	{
    		eventDAO.save(event);
    	}
    }
    
    public void updateEvent(Event event) 
    {
    	if(event != null && getEvent(event.getEventId()) != null) 
    	{
    		eventDAO.update(event);
    	}
    }
    
    public void deleteEvent(int id) 
    {
    	if(getEvent(id) != null) 
    	{
    		eventDAO.deleteById(id);
    	}
    }
}