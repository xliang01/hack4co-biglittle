package org.bbbs.sportsbuddies.domain.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.bbbs.sportsbuddies.domain.Pair;
import org.bbbs.sportsbuddies.util.DateUtil;
import org.springframework.jdbc.core.JdbcTemplate;

public class PairDAOImpl extends BaseDAO implements PairDAO {

	@Override
	public Pair getById(int id) {
		
		String sql = "SELECT * FROM Pair WHERE PairId = ?";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { id });
		
		if(rows.size() == 1) {
			return createPairFromRow(rows.get(0));
		}
		
		return null;
	}
	
	@Override
	public List<Pair> getAll() {

        String sql = "SELECT * FROM Pair";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        List<Pair> pairs = new ArrayList<Pair>();
		
		for (Map<String,Object> row : rows) {
			Pair p = createPairFromRow(row);
			pairs.add(p);
		}
		
		return pairs;
	}

	@Override
	public void save(Pair pair) { }


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

	private Pair createPairFromRow(Map<String,Object> row)
	{
		Pair pair = null;
		if (row != null)
		{
			pair = new Pair();
			pair.setPairId((Integer)row.get("PairId"));
			pair.setBigUserId((Integer)row.get("BigUserId"));
			pair.setLittleUserId((Integer)row.get("LittleUserId"));
			pair.setCreatedAt(DateUtil.DateToString((Date)row.get("CreatedAt")));
		}
		
		return pair;
	}
}