package org.bbbs.sportsbuddies.domain.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bbbs.sportsbuddies.domain.Event;
import org.springframework.jdbc.core.RowMapper;

@SuppressWarnings("rawtypes")
public class EventRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

		Event event = new Event();

		event.setEventId(rs.getInt("EventId"));

		event.setTitle(rs.getString("Title"));

		event.setLocation(rs.getString("Location"));

		event.setMinParticipants(rs.getInt("MinParticipants"));
		
		event.setMaxParticipants(rs.getInt("MaxParticipants"));
		
		event.setActive(rs.getBoolean("Active"));
		
		event.setDescription(rs.getString("Description"));
		
		event.setStartTime(rs.getTimestamp("StartTime"));
		
		event.setEndTime(rs.getTimestamp("EndTime"));
	
		event.setCreatedAt(rs.getTimestamp("Created"));
		
		return event;

	}

}
