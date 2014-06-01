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
import org.bbbs.sportsbuddies.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class PairDAOImpl extends BaseDAO implements PairDAO {

	@Override
	public void save(Pair pair) {
	}

	@Override
	public Pair getById(int id) {
		String sql = "SELECT * FROM Pair WHERE PairId = ?";

		List<Pair> pairs = new ArrayList<Pair>();
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql,new Object[] { id });
		for (Map<String,Object> row : rows) {
			
			Pair p = createPairFromRow(row);
			pairs.add(p);
		}
		if (pairs.size() == 1)
		{
			return pairs.get(0);
		}
		return null;
	}

	private Pair createPairFromRow(Map<String,Object> row)
	{
		Pair p = null;
		if (row != null)
		{
		 p = new Pair();
			p.setPairId((Integer)row.get("PairId"));

			p.setBigUserId((Integer)row.get("BigUserId"));

			p.setLittleUserId((Integer)row.get("LittleUserId"));
		
			Calendar created = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			created.setTime((Date)row.get("CreatedAt"));
			p.setCreatedAt(DatatypeConverter.printDateTime(created));
		}
		return p;
	}
	
	@Override
	public void update(Pair pair) {

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
	public List<Pair> getAll() {
		jdbcTemplate = new JdbcTemplate(dataSource);

        String sql = "SELECT * FROM Pair";
        List<Pair> pairs = new ArrayList<Pair>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map<String,Object> row : rows) {
			
			Pair p = createPairFromRow(row);
			pairs.add(p);
		}
		return pairs;
	}

}