package com.yoyar.gtd.internal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.yoyar.gtd.entities.ITask;
import com.yoyar.gtd.entities.TaskFactory;

@Component("taskRowMapper")
class TaskRowMapper implements RowMapper<ITask> {
	
	@Autowired
	private TaskFactory taskFactory;
	
	@Override
	public ITask mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
		/* TODO: externalize format */

		Date dueDate = null;
		Date completed = null;

		// TODO refactor these duplications
		try {
			if (rs.getString("duedate") != null)
				dueDate = format.parse(rs.getString("duedate"));
		} catch (ParseException e) {
			throw new RuntimeException("Unable to parse date.");
		}
		
		try {
			if (rs.getString("completed") != null)
				completed = format.parse(rs.getString("completed"));
		} catch (ParseException e) {
			throw new RuntimeException("Unable to parse date.");
		}

		ITask task = taskFactory.makeTask(rs.getString("title"));
		task.setId(rs.getLong("taskid"));
		//task.setParentId((Long)rs.getObject("parentid"));
		task.setDueDate(dueDate);
		task.setPriority(Priority.valueOf(rs.getString("priorityid")));
		task.setCompleted(completed);

		return task;
	}
}
