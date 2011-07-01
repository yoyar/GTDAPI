package com.yoyar.gtd.internal;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.stereotype.Component;

@Component("taskRepository")
class TaskRepositoryImpl implements TaskRepository {

	@Autowired
	RowMapper<Task> taskRowMapper;
	
	@Autowired
	private NamedParameterJdbcOperations jdbcParamTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private DataFieldMaxValueIncrementer incrementer;
	
	@Autowired
	MappingSqlQuery<Task> taskMappingQuery;
	
//	@Autowired
//	private TaskFactory taskFactory;

	public TaskRepositoryImpl() {
	}

	public Task addOrUpdate(Task task) {
		
		String dueDateString = null;
		
		String sql = "";

		// TODO externalize pattern
		if (null != task.getDueDate()) {
			dueDateString = String.format(
					"%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", 
					task.getDueDate()
			);
		}
		
		String completed = null;
		
		if( null != task.getCompleted()) {
			completed = String.format(
					"%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS",
					task.getCompleted()
			);
		}
		
		if( null == task.getEntityId()) {
			
			/* this is an insert, so we need to generate an id */
			task.setEntityId(incrementer.nextLongValue());
			
			sql = "insert into Task (taskid, parentid, priorityid, title, duedate, completed) "
				+ "values (:taskid, :parentid, :priorityid, :title, :dueDate, :completed)";
			
		} else {
			
			// update
			sql = "update Task set title = :title, parentid = :parentid," +
					" priorityid = :priorityid," +
					" duedate = :dueDate," +
					" completed = :completed" + 
					" where taskid = :taskid";
			
		} // fi
		
		if( task.getPriority() == null) {
			task.setPriority(Priority.LOW);
		}
		
		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("taskid", task.getEntityId())
				.addValue("title", task.getTitle())
				.addValue("dueDate", dueDateString)
				.addValue("priorityid", task.getPriority().toString())
				.addValue("parentid", task.getParentId())
				.addValue("completed", completed)
				;

		jdbcParamTemplate.update(sql, namedParameters);

		return task;
	}

//	@Override
//	public long addTask(Task parent, Task task) {//TODO remove me, I don't think anyone uses me.
//		throw new NotImplementedException();
//
//	}

	@Override
	public long delete(Task task) {
		long taskid = task.getEntityId();
		delete(taskid);
		return taskid;
	}

	@Override
	public Task getTask(long taskid) {
		
		List<Task> results = taskMappingQuery.execute(taskid);
		
		if( results.size() == 0 ) {
			return null;
		}
		
		return results.get(0);
	}

	@Override
	public void delete() {
		
		/* 
		 * Note: in the database schema: delete is set to cascade so all tasks 
		 * should get deleted, even though I'm only explicitly deleting the parent tasks.
		 */
		jdbcTemplate.execute("delete from Task where parentid is null");
	}

//	@Override
//	public Task updateTask(Task task) {
//		//TODO remove me, I don't think anyone uses me.
//		throw new NotImplementedException();
//	}

	
	@Override
	public List<Task> getTasks() {
		String sql = "select * from TaskView where parentid is null";
		return jdbcTemplate.query(sql, taskRowMapper);
	}

	@Override
	public List<Task> getTasks(Task parentTask) {
		
		String sql = "select * from TaskView where parentid = :parentid";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource()
			.addValue("parentid", parentTask.getEntityId())
		;
		
		return jdbcParamTemplate.query(sql, namedParameters, taskRowMapper);
	}

	@Override
	public long delete(long taskid) {
		
		jdbcTemplate.execute("delete from Task where taskid = " + taskid);
		return taskid;
	}
}
