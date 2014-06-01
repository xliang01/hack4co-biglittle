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
    
    public void saveBig(User user) 
    {
    	if(user != null && user.getUserId() == -1) 
    	{
	    	user.setUserTypeId(BIG_TYPE);
	    	userDAO.save(user);
    	}
    	else
    	{
    		updateBig(user);
    	}
    }
    
    public void updateBig(User user) 
    {
       	if(user != null && user.getUserTypeId() == BIG_TYPE && getBig(user.getUserId()) != null) 
       	{
	    	userDAO.update(user);
    	}
    }
    
    public void deleteBig(int id) 
    {
    	if(getBig(id) != null) 
    	{
    		userDAO.deleteById(id);
    	}
    }
    
    public void saveLittle(User user) 
    {
    	if(user != null && user.getUserId() == -1) 
    	{
	    	user.setUserTypeId(LITTLE_TYPE);
	    	userDAO.save(user);
    	}
    	else 
    	{
    		updateLittle(user);
    	}
    }
    
    public void updateLittle(User user) 
    {
    	if(user != null && user.getUserTypeId() == LITTLE_TYPE && getLittle(user.getUserId()) != null) 
    	{
	    	userDAO.update(user);
    	}
    }
    
    public void deleteLittle(int id) 
    {
    	if(getLittle(id) != null) 
    	{
    		userDAO.deleteById(id);
    	}
    }
}