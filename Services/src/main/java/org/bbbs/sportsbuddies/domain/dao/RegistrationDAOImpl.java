package org.bbbs.sportsbuddies.domain.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bbbs.sportsbuddies.domain.Registration;
import org.bbbs.sportsbuddies.util.DateUtil;
import org.springframework.jdbc.core.JdbcTemplate;

public class RegistrationDAOImpl extends BaseDAO implements RegistrationDAO {


	@Override
	public Registration getById(int id) {
		
		String sql = "SELECT * FROM Registration WHERE RegistrationId = ?";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql,new Object[] { id });
		
		if(rows.size() == 1) {
			return createRegistrationFromRow(rows.get(0));
		}
		
		return null;
	}

	@Override
	public List<Registration> getAll() {
	
        String sql = "SELECT * FROM Registration";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        List<Registration> registrations  = new ArrayList<Registration>();
        
		for (Map<String,Object> row : rows) {
			Registration registration = createRegistrationFromRow(row);
			registrations.add(registration);
		}
		
		return registrations;
	}

	@Override
	public void save(Registration registration) {
	}
	
	@Override
	public void update(Registration registration) {

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
	
	private Registration createRegistrationFromRow(Map<String,Object> row)
	{
		Registration registration = null;
		if (row != null)
		{
			registration = new Registration();
			
			registration.setRegistrationId((Integer)row.get("RegistrationId"));
			registration.setBigId((Integer)row.get("BigUserId"));
			registration.setLittleId((Integer)row.get("LittleUserId"));
			registration.setEventId((Integer)row.get("EventId"));
			registration.setLittleCanGo((Boolean)row.get("LittleCanGo"));
			registration.setBigCanGo((Boolean)row.get("BigCanGo"));
			registration.setCreatedAt(DateUtil.DateToString((Date)row.get("CreatedAt")));
		}
		
		return registration;
	}

}