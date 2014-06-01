package org.bbbs.sportsbuddies.domain.dao;

import java.util.List;

import org.bbbs.sportsbuddies.domain.Event;

public interface EventDAO {
    // Get
    public Event getById(int id);
    // Get All
    public List<Event> getAll();
	// Save
    public void save(Event event);
    // Update
    public void update(Event event);
    // Delete
    public void deleteById(int id);
}