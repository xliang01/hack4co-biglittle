package org.bbbs.sportsbuddies.domain;

public class Event {
    private String name;
    private String location;
    private String eventDate;

    public Event() {}

    public Event(String name, String location, String eventDate) {
        this.name = name;
        this.location = location;
        this.eventDate = eventDate;
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