package org.bbbs.sportsbuddies.domain;

public class Event {
	private int id;
    private String name;
    private String location;
    private String eventDate;

    public Event() {}

    public Event(int id, String name, String location, String eventDate) {
        this.id = id;
    	this.name = name;
        this.location = location;
        this.eventDate = eventDate;
    }
    
    public int getId()
    {
    	return id;
    }

    public void setId(int id)
    {
    	this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.name = eventDate;
    }
}