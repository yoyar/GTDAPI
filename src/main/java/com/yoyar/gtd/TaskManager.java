package com.yoyar.gtd;

import java.util.List;

import com.yoyar.gtd.entities.ITask;

public interface TaskManager {

	/**
	 * Add a Task and return it
	 * 
	 * @param ITask
	 * @return Task
	 */
	public ITask saveOrUpdate(ITask task);
	
	/**
	 * Return a List of all top level tasks; that is, tasks with no parent id
	 * 
	 * @return List&lt;Task&gt;
	 */
	public List<ITask> getTasks();
	
	/**
	 * Return the Task with the specified taskid.
	 * 
	 * @param taskid
	 */
	public ITask get(long taskid);

	/**
	 * Delete all tasks in the database. Caution!!!
	 */
	public void delete();
	
	/**
	 * Delete the specified task, and return the id of the deleted task.
	 * @param task
	 * @return long
	 */
	public long delete(ITask task);
	
}
