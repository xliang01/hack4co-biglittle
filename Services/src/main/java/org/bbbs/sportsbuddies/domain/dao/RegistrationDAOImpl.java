package org.bbbs.sportsbuddies.domain.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bbbs.sportsbuddies.domain.Registration;
import org.bbbs.sportsbuddies.util.DateUtil;

public class RegistrationDAOImpl extends BaseDAO implements RegistrationDAO {

	@Override
	public Registration getById(int id) {
		
		final String sql = "SELECT * FROM Registration WHERE RegistrationId = ?";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql,new Object[] { id });
		
		if(rows.size() == 1) {
			return createRegistrationFromRow(rows.get(0));
		}
		
		return null;
	}

	@Override
	public List<Registration> getAll() {
	
        final String sql = "SELECT * FROM Registration";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        List<Registration> registrations  = new ArrayList<Registration>();
        
		for (Map<String,Object> row : rows) {
			Registration registration = createRegistrationFromRow(row);
			registrations.add(registration);
		}
		
		return registrations;
	}

	@Override
	public void save(Registration reg) {
		final String sql = "INSERT INTO Registration (LittleUserId, BigUserId, EventId, LittleCanGo, BigCanGo) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, new Object[] { reg.getLittleId(), reg.getBigId(), reg.getEventId(), reg.isLittleCanGo(), reg.isBigCanGo() });
	}
	
	@Override
	public void update(Registration reg) {
		final String sql = "UPDATE Registration SET LittleUserId=?, BigUserId=?, EventId=?, LittleCanGo=?, BigCanGo=? WHERE RegistrationId=?";
		jdbcTemplate.update(sql, new Object[] { reg.getLittleId(), reg.getBigId(), reg.getEventId(), reg.isLittleCanGo(), reg.isBigCanGo(), reg.getRegistrationId() });
	}

	@Override
	public void deleteById(int id) {
		final String sql = "DELETE FROM Registration WHERE RegistrationId=?";
		jdbcTemplate.update(sql, new Object[] { id });
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