package com.yoyar.gtd.entities;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.yoyar.gtd.internal.Priority;

public interface Task {

	public Long getId();

	public void setId(long id);
	
	/**
	 * Add a child task to this task.
	 */
	public void add(Task task);
	
	/**
	 * Get the child tasks for this task.
	 */
	public List<Task> getTasks();
	
	/**
	 * Get the parent task for this task.
	 * @return Task
	 */
	public Task getParent();
	
	public void setParent(Task parent);

	public String getTitle();

	public void setTitle(String title);

	public Priority getPriority();

	public void setPriority(Priority priority);

	public Date getDueDate();

	public void setDueDate(java.util.Date dueDate);
	public void setDueDate(Calendar due);

	public Date getCompleted();

	public void setCompleted(Date completed);
	public void setCompleted(Calendar completed);

}
