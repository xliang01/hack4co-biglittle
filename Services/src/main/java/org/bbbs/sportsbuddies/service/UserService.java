package org.bbbs.sportsbuddies.service;

import java.util.List;

import org.bbbs.sportsbuddies.domain.User;
import org.bbbs.sportsbuddies.domain.dao.UserDAO;

public class UserService extends BaseService {
	
	private static int BIG_TYPE = 1;
	private static int LITTLE_TYPE = 2;
	
	private UserDAO userDAO;
	
	public UserService() 
	{
		userDAO = (UserDAO) context.getBean("UserDAO"); 
	}
	
	public List<User> getBigs() 
	{
        return userDAO.getAll(BIG_TYPE);
    }
    
    public User getBig(int id)
    {
    	return userDAO.getById(id, BIG_TYPE);
    }
    
    public List<User> getLittles() 
    {
        return userDAO.getAll(LITTLE_TYPE);
    }
    
    public User getLittle(int id)
    {
    	return userDAO.getById(id, LITTLE_TYPE);
    }
}