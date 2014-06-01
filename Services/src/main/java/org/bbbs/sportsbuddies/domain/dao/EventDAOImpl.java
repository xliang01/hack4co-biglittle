package org.bbbs.sportsbuddies.domain.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.sql.DataSource;
import javax.xml.bind.DatatypeConverter;

import org.bbbs.sportsbuddies.domain.Event;
import org.bbbs.sportsbuddies.domain.Pair;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class EventDAOImpl extends BaseDAO implements EventDAO {

	@Override
	public void save(Event event) {
	}

	@Override
	public Event getById(int id) {
		String sql = "SELECT * FROM Event WHERE EventId = ?";
		List<Event> events = new ArrayList<Event>();
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql,new Object[] { id });
		for (Map<String,Object> row : rows) {
			
			Event p = createEventFromRow(row);
			events.add(p);
		}
		if (events.size() == 1)
		{
			return events.get(0);
		}
		return null;
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

	@Override
	public List<Event> getAll() {
		jdbcTemplate = new JdbcTemplate(dataSource);

        String sql = "SELECT * FROM Event";
        List<Event> events  = new ArrayList<Event>();

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map<String,Object> row : rows) {
			
			Event p = createEventFromRow(row);
			events.add(p);
		}
		return events;

	}
	
	private Event createEventFromRow(Map<String,Object> row)
	{
		Event p = null;
		if (row != null)
		{
		 p = new Event();
			p.setEventId((Integer)row.get("EventId"));

			p.setTitle((String)row.get("Title"));
			
			p.setLocation((String)row.get("Location"));
			
			p.setMinParticipants((Integer)row.get("minParticipants"));
			
			p.setMaxParticipants((Integer)row.get("maxParticipants"));
			
			p.setDescription((String)row.get("Description"));
		
			p.setActive((Boolean)row.get("Active"));
			
			Calendar start = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			start.setTime((Date)row.get("StartTime"));
			p.setStartTime(DatatypeConverter.printDateTime(start));
			
			Calendar end = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			end.setTime((Date)row.get("EndTime"));
			p.setEndTime(DatatypeConverter.printDateTime(end));
			
			Calendar created = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			created.setTime((Date)row.get("CreatedAt"));
			p.setCreatedAt(DatatypeConverter.printDateTime(created));
		}
		return p;
	}

}