package org.bbbs.sportsbuddies.domain.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bbbs.sportsbuddies.domain.Address;
import org.bbbs.sportsbuddies.domain.User;
import org.bbbs.sportsbuddies.util.DateUtil;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

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
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { typeId });
        List<User> users = new ArrayList<User>();
        
        for (Map<String,Object> row : rows) {
        	
        	User user = createUserFromRow(row);
        	if(user != null) {
        		
	        	Address address = getAddress(user.getUserId());
	        	if(address != null) {
	        		user.setAddress(address);
	        	}
	        	
	        	users.add(user);
        	}
        }
        
        return users;
	}
	
	public void save(final User user) {
		
		Address address = user.getAddress();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		final String sql = "INSERT INTO User (FirstName, LastName, Email, UserTypeId) VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(
		    new PreparedStatementCreator() {		
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"FirstName", "LastName", "Email", "UserTypeId"});
		            ps.setString(1, user.getFirstName());
		            ps.setString(2, user.getLastName());
		            ps.setString(3, user.getEmail());
		            ps.setInt(4, user.getUserTypeId());
		            return ps;
		        }
		    },
		    keyHolder);
		
		if(address != null) {
			final String addressSql = "INSERT INTO Address (Line1, Line2, City, State, Zip, UserId) VALUES (?, ?, ?, ?, ?, ?)";
			jdbcTemplate.update(addressSql, new Object[] { address.getLine1(), address.getLine2(), address.getCity(), address.getState(), address.getZip(), keyHolder.getKey() });
		}
	}
	
	public void update(User user) {
		
		Address address = user.getAddress();
		
		final String sql = "UPDATE User SET FirstName=?, LastName=?, Email=?, UserTypeId=? WHERE UserId = ?";
		jdbcTemplate.update(sql, new Object[] { user.getFirstName(), user.getLastName(), user.getEmail(), user.getUserTypeId(), user.getUserId() });
		
		if(address != null) {
			final String addressSql = "UPDATE Address SET Line1=?, Line2=?, City=?, State=?, Zip=? WHERE AddressId = ?";
			jdbcTemplate.update(addressSql, new Object[] { address.getLine1(), address.getLine2(), address.getCity(), address.getState(), address.getZip(), address.getAddressId() });
		}
	}
	
	public void deleteById(int id) {
		
		final String addressSql = "DELETE FROM Address WHERE UserId = ?";
		jdbcTemplate.update(addressSql, new Object[] { id });
		
		final String sql = "DELETE FROM User WHERE UserId = ?";
		jdbcTemplate.update(sql, new Object[] { id });
	}

	private User getUser(int id, int typeId) {
		
		String sql = "SELECT * FROM User WHERE UserId = ? AND UserTypeId = ?";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { id, typeId } );
		
		if(rows.size() == 1) {
			return createUserFromRow(rows.get(0));
		}
		
		return null;
	}
	
	private Address getAddress(int userId) {
		
		String sql = "SELECT * FROM Address WHERE UserId = ?";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { userId });
        
        if(rows.size() == 1) {
        	return createAddressFromRow(rows.get(0));
        }
        
        return null;
	}
	
	private User createUserFromRow(Map<String,Object> row)
	{
		User user = null;
		if (row != null)
		{
			user = new User();
			user.setUserId((Integer)row.get("UserId"));
			user.setEmail((String)row.get("Email"));
			user.setFirstName((String)row.get("FirstName"));
			user.setLastName((String)row.get("LastName"));
			user.setUserTypeId((Integer)row.get("UserTypeId"));
			user.setCreatedAt(DateUtil.DateToString((Date)row.get("CreatedAt")));
		}
		
		return user;
	}
	
	private Address createAddressFromRow(Map<String,Object> row)
	{
		Address address = null;
		if (row != null)
		{
			address = new Address();
			address.setAddressId((Integer)row.get("AddressId"));
			address.setLine1((String)row.get("Line1"));
			address.setLine2((String)row.get("Line2"));
			address.setState((String)row.get("State"));
			address.setCity((String)row.get("City"));
			address.setState((String)row.get("State"));
			address.setZip((String)row.get("Zip"));
			address.setUserId((Integer)row.get("UserId"));
			address.setCreatedAt(DateUtil.DateToString((Date)row.get("CreatedAt")));
		}
		
		return address;
	}
}