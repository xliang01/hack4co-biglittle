package org.bbbs.sportsbuddies.domain.dao;

import java.util.List;

import javax.sql.DataSource;

import org.bbbs.sportsbuddies.domain.Event;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class EventDAOImpl implements EventDAO {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void save(Event event) {
	}

	@Override
	public Event getById(int id) {
		String sql = "SELECT * FROM Event WHERE EventId = ?";
		Event event = (Event) jdbcTemplate.queryForObject(sql, new Object[] { id }, new EventRowMapper());
		return event;
	}

	@Override
	public void update(Event event) {

		String sql = "INSERT INTO Event "
				+ "(EventId, EventName, Location, EventDate) VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sql, new Object[] { event.getId(),
		event.getName(), event.getLocation(), event.getEventDate()
		});

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
        List<Event> events  = jdbcTemplate.query(sql,
    			new BeanPropertyRowMapper(Event.class));
        return events;

	}

}