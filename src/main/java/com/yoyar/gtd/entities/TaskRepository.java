package com.yoyar.gtd.entities;

import java.util.List;

public interface TaskRepository {
	
	public ITask saveOrUpdate(ITask task);
	
	public ITask get(long taskid);

	/**
	 * Delete the specified task.
	 * 
	 * @param task
	 * @return long
	 */
	public long delete(ITask task);
	
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
	public List<ITask> getTasks();	
}
