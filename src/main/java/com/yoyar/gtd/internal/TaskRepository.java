package com.yoyar.gtd.internal;

import java.util.List;

public interface TaskRepository {

	public Task addOrUpdate(Task task);
	
	public Task getTask(long taskid);

	/**
	 * Delete the specified task.
	 * 
	 * @param task
	 * @return long
	 */
	public long delete(Task task);
	
	public long delete(long taskid);

	/**
	 * Delete ALL tasks
	 */
	public void delete();
	
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
