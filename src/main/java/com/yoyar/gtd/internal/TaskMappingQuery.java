package com.yoyar.gtd.internal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Component;

@Component("taskMappingQuery")
class TaskMappingQuery extends MappingSqlQuery<Task> {
	
	@Autowired
	RowMapper<Task> taskRowMapper;
	
	@Autowired
	public TaskMappingQuery(DataSource dataSource) {

		super(
			dataSource, 
			"select * from TaskView where taskid = :taskid"
		);
		super.declareParameter(new SqlParameter("taskid", Types.BIGINT));
		compile();
	}
	
	protected Task mapRow(ResultSet rs, int rowNumber) throws SQLException {
		return taskRowMapper.mapRow(rs, rowNumber);
	}
}
