package com.yoyar.gtd;

import java.util.Collection;

import com.yoyar.gtd.internal.Task;

public interface TaskManager {

	/**
	 * Add a Task and return the just added Task
	 * 
	 * @param task
	 * @return long taskid
	 */
	public Task add(Task task);

	public Task update(Task task);
	
	public Collection<Task> getTopLevelTasks();
	
	/**
	 * Add a Task to the ParentTask and return just added Task
	 * 
	 * @param Task
	 *            parent
	 * @param Task
	 *            task
	 * @return long taskid
	 */
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
	public void deleteAll();
}
