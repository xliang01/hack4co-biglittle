package org.bbbs.sportsbuddies.domain.dao;

import java.util.List;

import org.bbbs.sportsbuddies.domain.Registration;

public interface RegistrationDAO {
    // Get
    public Registration getById(int id);
    // Get All
    public List<Registration> getAll();
	// Save
    public void save(Registration registration);
    // Update
    public void update(Registration registration);
    // Delete
    public void deleteById(int id);
}