package com.yoyar.gtd.entities;

import java.util.List;

import org.hibernate.SessionFactory;


public interface TaskRepository {

	public void setSessionFactory(SessionFactory sessionFactory);
	
	public ITask addOrUpdate(ITask task);
	
	public ITask get(long taskid);

	/**
	 * Delete the specified task.
	 * 
	 * @param task
	 * @return long
	 */
	public long delete(ITask task);
	
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
	public List<ITask> getTasks();
	
	/**
	 * For the specified parent Task return the list of sub tasks
	 * 
	 * @param ITask
	 * @return List&lt;Task&gt;
	 */
	public List<ITask> getTasks(ITask parentTask);
}
