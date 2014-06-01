package org.bbbs.sportsbuddies.domain.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bbbs.sportsbuddies.domain.Event;
import org.springframework.jdbc.core.RowMapper;

@SuppressWarnings("rawtypes")
public class EventRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

		Event event = new Event();

		event.setId(rs.getInt("EventId"));

		event.setName(rs.getString("EventName"));

		event.setLocation(rs.getString("Location"));
		
		event.setEventDate(rs.getString("EventDate"));

		return event;

	}

}
