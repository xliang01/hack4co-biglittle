package org.bbbs.sportsbuddies.domain.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.bbbs.sportsbuddies.domain.Event;
import org.bbbs.sportsbuddies.util.DateUtil;
import org.springframework.jdbc.core.JdbcTemplate;

public class EventDAOImpl extends BaseDAO implements EventDAO {

	@Override
	public void save(Event event) {
	}

	@Override
	public Event getById(int id) {
		
		String sql = "SELECT * FROM Event WHERE EventId = ?";
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
	public void update(Event event) {

//		String sql = "INSERT INTO Event "
//				+ "(EventId, EventName, Location, EventDate) VALUES (?, ?, ?, ?)";
//		jdbcTemplate.update(sql, new Object[] { event.getEventName(),
//		event.getName(), event.getLocation(), event.getEventDate()
//		});

	}

	@Override
	public void deleteById(int id) {
        jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "DELETE FROM Event WHERE EventId=?";
		//TODO LATER
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