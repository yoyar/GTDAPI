package com.yoyar.gtd.internal;

import java.util.List;

public interface TaskRepository {

	public Task addOrUpdate(Task task);
	
	public Task updateTask(Task task);

	public long addTask(Task parent, Task task);

	public Task getTask(long taskid);

	public long deleteTask(Task task);

	public void deleteAll();
	
	public List<Task> getTopLevelTasks();
	
	/**
	 * For the specified parent Task return the list of sub tasks
	 * 
	 * @param Task
	 * @return List&lt;Task&gt;
	 */
	public List<Task> getTasks(Task parentTask);
}
