package org.bbbs.sportsbuddies.domain.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bbbs.sportsbuddies.domain.Pair;
import org.bbbs.sportsbuddies.util.DateUtil;

public class PairDAOImpl extends BaseDAO implements PairDAO {

	@Override
	public Pair getById(int id) {
		
		final String sql = "SELECT * FROM Pair WHERE PairId=?";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { id });
		
		if(rows.size() == 1) {
			return createPairFromRow(rows.get(0));
		}
		
		return null;
	}
	
	@Override
	public List<Pair> getAll() {

        final String sql = "SELECT * FROM Pair";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        List<Pair> pairs = new ArrayList<Pair>();
		
		for (Map<String,Object> row : rows) {
			Pair p = createPairFromRow(row);
			pairs.add(p);
		}
		
		return pairs;
	}

	@Override
	public void save(Pair pair) { 
		final String sql = "INSERT INTO Pair (LittleUserId, BigUserId) VALUES (?, ?)";
		jdbcTemplate.update(sql, new Object[] { pair.getLittleUserId(), pair.getBigUserId() });
	}


	@Override
	public void update(Pair pair) {
		final String sql = "UPDATE Pair SET LittleUserId=?, BigUserId=? WHERE PairId=?";
		jdbcTemplate.update(sql, new Object[] { pair.getLittleUserId(), pair.getBigUserId(), pair.getPairId() });
	}

	@Override
	public void deleteById(int id) {
		final String sql = "DELETE FROM Pair WHERE PairId=?";
		jdbcTemplate.update(sql, new Object[] { id });
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