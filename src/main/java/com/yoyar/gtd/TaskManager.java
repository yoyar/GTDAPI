package com.yoyar.gtd;

import java.util.List;

import com.yoyar.gtd.entities.Task;

public interface TaskManager {

	/**
	 * Add a Task and return it
	 * 
	 * @param Task
	 * @return Task
	 */
	public Task saveOrUpdate(Task task);

	/**
	 * Update the specified task
	 * 
	 * @param task
	 * @return Task
	 */
	@Deprecated
	public Task update(Task task);
	
	/**
	 * Return a List of all top level tasks; that is, tasks with no parent id
	 * 
	 * @return List&lt;Task&gt;
	 */
	public List<Task> getTasks();
	
	/**
	 * For the specified parent task, return the sub tasks.
	 * 
	 * @param parentTask
	 * @return List&lt;Task&gt;
	 */
	public List<Task> getTasks(Task parentTask);
	
	/**
	 * Add a newly created Task to the ParentTask and return just added Task
	 * 
	 * @param Task
	 *            parent
	 * @param Task
	 *            task
	 * @return long taskid
	 */
	@Deprecated
	public Task add(Task parent, Task task);

	/**
	 * Return the Task with the specified taskid.
	 * 
	 * @param taskid
	 */
	public Task get(long taskid);

	/**
	 * Delete all tasks in the database. Caution!!!
	 */
	public void delete();
	
	/**
	 * Delete the specified task, and return the id of the deleted task.
	 * @param task
	 * @return long
	 */
	public long delete(Task task);
	
}
