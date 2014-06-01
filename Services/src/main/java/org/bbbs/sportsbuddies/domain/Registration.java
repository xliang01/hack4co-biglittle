package org.bbbs.sportsbuddies.domain;

public class Registration {
	
	private int registrationId = -1;
	private int littleId = -1;
	private int bigId = -1;
	private int eventId = -1;
	private boolean bigCanGo;
	private boolean littleCanGo;
	private String createdAt;
	
	public Registration() {}
	
	public Registration(int registrationId,
					    int littleId,
					    int bigId,
					    int eventId,
					    String createdAt)
	{
		this.registrationId = registrationId;
		this.littleId = littleId;
		this.bigId = bigId;
		this.eventId = eventId;
		this.createdAt = createdAt;
	}
	
	public int getRegistrationId() {
		return registrationId;
	}
	public void setRegistrationId(int registrationId) {
		this.registrationId = registrationId;
	}
	
	public int getLittleId() {
		return littleId;
	}
	
	public void setLittleId(int littleId) {
		this.littleId = littleId;
	}
	
	public int getBigId() {
		return bigId;
	}
	
	public void setBigId(int bigId) {
		this.bigId = bigId;
	}
	
	public int getEventId() {
		return eventId;
	}
	
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	
	public boolean isBigCanGo() {
		return bigCanGo;
	}

	public void setBigCanGo(boolean bigCanGo) {
		this.bigCanGo = bigCanGo;
	}

	public boolean isLittleCanGo() {
		return littleCanGo;
	}

	public void setLittleCanGo(boolean littleCanGo) {
		this.littleCanGo = littleCanGo;
	}

	public String getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
}
