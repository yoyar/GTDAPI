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

class TaskRepositoryImpl implements TaskRepository {

	@Autowired
	private NamedParameterJdbcOperations jdbcParamTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private DataFieldMaxValueIncrementer incrementer;
	
	@Autowired
	private TaskFactory taskFactory;

	public TaskRepositoryImpl() {
	}

	public Task addOrUpdate(Task task) {

		Long taskid = null;
		
		String dueDateString = null;
		
		String sql = "";

		// TODO externalize pattern
		if (null != task.getDueDate()) {
			dueDateString = String.format(
					"%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", task.getDueDate());
		}
		
		if( null == task.getEntityId()) {
			/* this is an insert, so we need to generate an id */
			taskid = incrementer.nextLongValue();
			task.setEntityId(taskid);
			
			sql = "insert into Task (id, title, dueDate, priority) "
				+ "values (:id, :title, :dueDate, :priority)";
		} else {
			// update
			sql = "update Task " +
					" set title = :title," +
					" dueDate = :dueDate," +
					" priority = :priority" +
					" where id = :id";
		} // fi
		
		
		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("id", task.getEntityId())
				.addValue("title", task.getTitle())
				.addValue("dueDate", dueDateString)
				.addValue("priority", task.getPriority().toString())
				;

		jdbcParamTemplate.update(sql, namedParameters);

		return task;
	}

	@Override
	public long addTask(Task parent, Task task) {
		throw new NotImplementedException();

	}

	@Override
	public long deleteTask(Task task) {
		throw new NotImplementedException();

	}

	@Autowired
	MappingSqlQuery<Task> taskMappingQuery;

	@Override
	public Task getTask(long taskid) {
		
		List<Task> results = taskMappingQuery.execute(taskid);
		
		if( results.size() == 0 ) {
			return null;
		}
		
		return results.get(0);
	}

	@Override
	public void deleteAll() {
		jdbcTemplate.execute("delete from Task");
	}

	@Override
	public Task updateTask(Task task) {
		throw new NotImplementedException();
	}

	@Autowired
	RowMapper<Task> taskRowMapper;
	
	@Override
	public List<Task> getTopLevelTasks() {
		
		String sql = "select * from Task where parentid is null";
		
		return jdbcTemplate.query(sql, taskRowMapper);
	}

}
