package org.bbbs.sportsbuddies.domain.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bbbs.sportsbuddies.domain.Event;
import org.bbbs.sportsbuddies.util.DateUtil;

public class EventDAOImpl extends BaseDAO implements EventDAO {

	@Override
	public Event getById(int id) {
		
		String sql = "SELECT * FROM Event WHERE EventId=?";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql,new Object[] { id });
		
		if(rows.size() == 1) {
			return createEventFromRow(rows.get(0));
		}
		
		return null;
	}

	@Override
	public List<Event> getAll() {
	
        String sql = "SELECT * FROM Event";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        List<Event> events  = new ArrayList<Event>();
        
		for (Map<String,Object> row : rows) {
			Event event = createEventFromRow(row);
			events.add(event);
		}
		
		return events;
	}

	@Override
	public void save(Event event) {
		
		Date startTime = null;
		Date endTime = null;
		
		if(event.getStartTime() != null) {
			startTime = DateUtil.StringToDate(event.getStartTime());
		}
		
		if(event.getEndTime() != null) {
			endTime = DateUtil.StringToDate(event.getStartTime());
		}
		
		final String sql = "INSERT INTO Event (Title, Location, MinParticipants, MaxParticipants, Active, Description, StartTime, EndTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, new Object[] { event.getTitle(), event.getLocation(), event.getMinParticipants(), event.getMaxParticipants(), event.isActive(), event.getDescription(), startTime, endTime });
	}

	@Override
	public void update(Event event) {
		
		Date startTime = null;
		Date endTime = null;
		
		if(event.getStartTime() != null) {
			startTime = DateUtil.StringToDate(event.getStartTime());
		}
		
		if(event.getEndTime() != null) {
			endTime = DateUtil.StringToDate(event.getStartTime());
		}
		
		final String sql = "UPDATE Event SET Title=?, Location=?, MinParticipants=?, MaxParticipants=?, Active=?, Description=?, StartTime=?, EndTime=? WHERE EventId=?";
		jdbcTemplate.update(sql, new Object[] { event.getTitle(), event.getLocation(), event.getMinParticipants(), event.getMaxParticipants(), event.isActive(), event.getDescription(), startTime, endTime, event.getEventId() });
	}

	@Override
	public void deleteById(int id) {
		final String sql = "DELETE FROM Event WHERE EventId=?";
		jdbcTemplate.update(sql, new Object[] { id });
	}
	
	private Event createEventFromRow(Map<String,Object> row)
	{
		Event event = null;
		if (row != null)
		{
			event = new Event();
			
			event.setEventId((Integer)row.get("EventId"));
			event.setTitle((String)row.get("Title"));
			event.setLocation((String)row.get("Location"));
			event.setMinParticipants((Integer)row.get("minParticipants"));
			event.setMaxParticipants((Integer)row.get("maxParticipants"));
			event.setDescription((String)row.get("Description"));
			event.setActive((Boolean)row.get("Active"));
			event.setStartTime(DateUtil.DateToString((Date)row.get("StartTime")));
			event.setEndTime(DateUtil.DateToString((Date)row.get("EndTime")));
			event.setCreatedAt(DateUtil.DateToString((Date)row.get("CreatedAt")));
		}
		return event;
	}

}