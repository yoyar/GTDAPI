package com.yoyar.gtd.repositories;

import java.util.List;

import com.yoyar.gtd.entities.Task;

public interface TaskRepository {
	
	public Task saveOrUpdate(Task task);
	
	public Task get(long taskid);

	/**
	 * Delete the specified task.
	 * 
	 * @param task
	 * @return long
	 */
	public long delete(Task task);
	
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
}
