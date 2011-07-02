package com.yoyar.gtd.entities;

import java.util.List;

import org.hibernate.SessionFactory;


public interface TaskRepository {

	public void setSessionFactory(SessionFactory sessionFactory);
	
	public Task addOrUpdate(Task task);
	
	public Task get(long taskid);

	/**
	 * Delete the specified task.
	 * 
	 * @param task
	 * @return long
	 */
	public long delete(Task task);
	
	/**
	 * Delete the task with the specified task id.
	 * 
	 * @param taskid
	 * @return taskid
	 */
	public long delete(long taskid);

	/**
	 * Delete ALL tasks
	 * @return number of items deleted
	 */
	public int delete();
	
	/**
	 * Returns the top level tasks, that is, all tasks with no parents.
	 * 
	 * @return List&lt;Task&gt;
	 */
	public List<Task> getTasks();
	
	/**
	 * For the specified parent Task return the list of sub tasks
	 * 
	 * @param Task
	 * @return List&lt;Task&gt;
	 */
	public List<Task> getTasks(Task parentTask);
}
