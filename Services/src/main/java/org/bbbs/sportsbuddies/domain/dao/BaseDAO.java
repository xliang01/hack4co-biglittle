package org.bbbs.sportsbuddies.domain.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public abstract class BaseDAO {

	protected DataSource dataSource;
	protected JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
}