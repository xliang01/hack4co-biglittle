package org.bbbs.sportsbuddies.domain;

import java.util.Date;

public class Event {
    
	private int eventId;
	
	private String title;
	private String location;
	private int minParticipants;
	private int maxParticipants;
	private boolean active;
	private String description;
	private String startTime;
	private String endTime;
	private String createdAt;
	
	public Event() {}
	
	public Event(int eventId,
				 String title,
				 String location,
				 int minParticipants,
				 int maxParticipants,
				 boolean active,
				 String description,
				 String startTime,
				 String endTime,
				 String createdAt) {
		
		this.eventId = eventId;
		this.title = title;
		this.location = location;
		this.minParticipants = minParticipants;
		this.maxParticipants = maxParticipants;
		this.active = active;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.createdAt = createdAt;
	}
	
	public int getEventId() {
		return eventId;
	}
	
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public int getMinParticipants() {
		return minParticipants;
	}
	
	public void setMinParticipants(int minParticipants) {
		this.minParticipants = minParticipants;
	}
	
	public int getMaxParticipants() {
		return maxParticipants;
	}
	
	public void setMaxParticipants(int maxParticipants) {
		this.maxParticipants = maxParticipants;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getEndTime() {
		return endTime;
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
}