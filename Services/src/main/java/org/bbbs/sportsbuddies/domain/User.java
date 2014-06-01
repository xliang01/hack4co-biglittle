package org.bbbs.sportsbuddies.domain;

public class User {
	
	private int userId = -1;
	private int userTypeId = -1;
	private String firstName;
	private String lastName;
	private String email;
	private Address address;
	private String createdAt;
	
	public User() {}
	
	public User(int userId,
				int userTypeId,
				String firstName,
				String lastName,
				String email,
				Address address,
				String createdAt)
	{
		this.userId = userId;
		this.userTypeId = userTypeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.createdAt = createdAt;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}

	public int getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(int userTypeId) {
		this.userTypeId = userTypeId;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
}
