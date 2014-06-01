package org.bbbs.sportsbuddies.domain.dao;

import java.util.List;

import org.bbbs.sportsbuddies.domain.User;

public interface UserDAO {
    // Get
    public User getById(int id, int typeId);
    // Get All
    public List<User> getAll(int typeId);
    // Save
    public void save(User user);
    // Update
    public void update(User user);
    // Delete
    public void deleteById(int id);
}