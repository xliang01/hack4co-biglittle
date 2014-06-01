package org.bbbs.sportsbuddies.domain.dao;

import java.util.List;

import org.bbbs.sportsbuddies.domain.User;

public interface UserDAO {
    //Get All
    public List<User> getAll(int typeId);
    //Get
    public User getById(int id, int typeId);
}