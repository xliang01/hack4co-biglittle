package org.bbbs.sportsbuddies.domain.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bbbs.sportsbuddies.domain.Address;
import org.springframework.jdbc.core.RowMapper;

@SuppressWarnings("rawtypes")
public class AddressRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

		Address address = new Address();
		
		address.setAddressId(rs.getInt("AddressId"));
		address.setCity(rs.getString("City"));
		address.setCreatedAt(rs.getTimestamp("Created"));
		address.setLine1(rs.getString("Line1"));
		address.setLine2(rs.getString("Line2"));
		address.setState(rs.getString("State"));
		address.setUserId(rs.getInt("UserId"));
		address.setZip(rs.getString("Zip"));
		
		return address;
	}
}
