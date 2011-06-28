package com.yoyar.gtd.internal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

class TaskRowMapper implements RowMapper<Task> {
	
	@Autowired
	private TaskFactory taskFactory;
	
	@Override
	public Task mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
		/* TODO: externalize format */

		Date dueDate = null;

		try {
			if (rs.getString("dueDate") != null)
				dueDate = format.parse(rs.getString("dueDate"));
		} catch (ParseException e) {
			throw new RuntimeException("Unable to parse date.");
		}

		Task task = taskFactory.makeTask(rs.getString("title"));
		task.setEntityId(rs.getLong("id"));
		task.setDueDate(dueDate);
		task.setPriority(Priority.valueOf(rs.getString("priority")));

		return task;
	}
}
