package com.yoyar.gtd.internal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

class TaskMappingQuery extends MappingSqlQuery<Task> {

	@Autowired
	private TaskFactory taskFactory;

	public TaskMappingQuery(DataSource dataSource) {

		super(dataSource, "select * from Task where id = :id");
		super.declareParameter(new SqlParameter("id", Types.BIGINT));
		compile();
	}

	@Autowired
	RowMapper<Task> taskRowMapper;
	
	protected Task mapRow(ResultSet rs, int rowNumber) throws SQLException {
		
		return taskRowMapper.mapRow(rs, rowNumber);
		
	}
}
