package org.bbbs.sportsbuddies.domain.dao;

import java.util.List;

import org.bbbs.sportsbuddies.domain.Event;

public interface EventDAO {
    
    public void save(Event event);
    
    public Event getById(int id);
    //Update
    public void update(Event event);
    //Delete
    public void deleteById(int id);
    //Get All
    public List<Event> getAll();
}
