package com.yoyar.gtd.internal;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

class TaskImpl implements Task {

	private Long id = null;

	private List<Task> tasks;

	private String title;

	private Priority priority;

	private Date dueDate;

	private Date completed;

	public TaskImpl() {
	}

	@Override
	public boolean equals(Object obj) {
		
		// TODO: refine this equals method.
		
		if( this == obj) return true;
		if((obj == null) || (obj.getClass() != this.getClass())) return false;
		
		Task other = (Task) obj;
		
		Calendar thisDueDate = Calendar.getInstance();
		Calendar otherDueDate = Calendar.getInstance();
		thisDueDate.setTime(this.getDueDate());
		otherDueDate.setTime(other.getDueDate());
		
		return this.getTitle().equals(other.getTitle())
			&& thisDueDate.get(Calendar.YEAR) == otherDueDate.get(Calendar.YEAR)
			&& thisDueDate.get(Calendar.MONTH) == otherDueDate.get(Calendar.MONTH)
			&& thisDueDate.get(Calendar.DAY_OF_MONTH) == otherDueDate.get(Calendar.DAY_OF_MONTH)
			&& thisDueDate.get(Calendar.HOUR_OF_DAY) == otherDueDate.get(Calendar.HOUR_OF_DAY)
			&& thisDueDate.get(Calendar.MINUTE) == otherDueDate.get(Calendar.MINUTE)
			&& thisDueDate.get(Calendar.SECOND) == otherDueDate.get(Calendar.SECOND)
		;
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + getTitle().hashCode();		
		hash = 31 * hash + (null == getDueDate() ? 0 : getDueDate().hashCode());		
		return hash;
	}
	
	public void addTask(Task task) {
		this.tasks.add(task);
	}

	public Long getEntityId() {
		return id;
	}

	public void setEntityId(long id) {
		this.id = id;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		
		if( null == title || "" == title) {
			throw new IllegalArgumentException("title cannot be null");
		}
		
		this.title = title;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getCompleted() {
		return completed;
	}

	public void setCompleted(Date completed) {
		this.completed = completed;
	}

	public boolean contains(Task task) {
		return this.tasks.contains(task);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("[Task id=")
		.append(getEntityId())
		.append(" title='")
		.append(getTitle()).append("'")
		.append(" dueDate=")
		.append(getDueDate())
		.append("]")
		;
		
		return sb.toString();
	}

}
