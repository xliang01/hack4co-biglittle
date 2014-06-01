package org.bbbs.sportsbuddies.domain.dao;

import java.util.List;

import org.bbbs.sportsbuddies.domain.Address;
import org.bbbs.sportsbuddies.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

public class UserDAOImpl extends BaseDAO implements UserDAO {

	@Override
	public User getById(int id, int typeId) {
		
		User user = getUser(id, typeId);
		
		if(user != null) {
			Address address = getAddress(user.getUserId());
			if(address != null) {
				user.setAddress(address);
			}
		}
		
		return user;
	}
	
	@Override
	public List<User> getAll(int typeId) {
		
        String sql = "SELECT * FROM User WHERE UserTypeId = ?";
        List<User> users = jdbcTemplate.query(sql, new Object[] { typeId }, new BeanPropertyRowMapper(User.class));
        
        for(User user : users) {
        	Address address = getAddress(user.getUserId());
        	if(address != null) {
        		user.setAddress(address);
        	}
        }
        
        return users;
	}
	
	private Address getAddress(int userId) {
		
		String sql = "SELECT * FROM Address WHERE UserId = ?";
        List<Address> addresses = jdbcTemplate.query(sql, new Object[] { userId }, new BeanPropertyRowMapper(Address.class));
        
        if(addresses.size() == 1) {
        	return addresses.get(0);
        }
        
        return null;
	}
	
	private User getUser(int id, int typeId) {
		
		String sql = "SELECT * FROM User WHERE UserId = ? AND UserTypeId = ?";
        List<User> users = jdbcTemplate.query(sql, new Object[] { id, typeId }, new BeanPropertyRowMapper(User.class));
		
		if(users.size() == 1) {
			return users.get(0);
		}
		
		return null;
	}
}