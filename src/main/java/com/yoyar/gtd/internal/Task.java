package com.yoyar.gtd.internal;

import java.util.Date;
import java.util.List;

public interface Task {

	public Long getEntityId();

	public void setEntityId(long id);
	
	public void setParentId(Long id);
	
	public Long getParentId();

	public boolean contains(Task task);

	public void addTask(Task task);

	/*
	public List<Task> getTasks();

	public void setTasks(List<Task> tasks);
	*/

	public String getTitle();

	public void setTitle(String title);

	public Priority getPriority();

	public void setPriority(Priority priority);

	public Date getDueDate();

	public void setDueDate(java.util.Date dueDate);

	public Date getCompleted();

	public void setCompleted(Date completed);

}
