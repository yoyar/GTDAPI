package com.yoyar.gtd.internal;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

class TaskImpl implements Task {

	private Long id = null;

	private Long parentid = null;
	
	private List<Task> tasks;

	private String title;

	private Priority priority;

	private Date dueDate;

	private Date completed;

	public TaskImpl() {
	}

	@Override
	public void addTask(Task task) {
		this.tasks.add(task);
	}

	@Override
	public Long getEntityId() {
		return id;
	}

	@Override
	public void setEntityId(long id) {
		this.id = id;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		
		if( null == title || "" == title) {
			throw new IllegalArgumentException("title cannot be null");
		}
		
		this.title = title;
	}

	@Override
	public Priority getPriority() {
		return priority;
	}

	@Override
	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	@Override
	public Date getDueDate() {
		return dueDate;
	}

	@Override
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	@Override
	public Date getCompleted() {
		return completed;
	}

	@Override
	public void setCompleted(Date completed) {
		this.completed = completed;
	}

	@Override
	public boolean contains(Task task) {
		return this.tasks.contains(task);
	}
	
	@Override
	public Long getParentId() {
		return parentid;
	}

	@Override
	public void setParentId(Long parentid) {
		this.parentid = parentid;
	}
	
	@Override
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
	
	/**
	 * A Task equals another Task when the titles are equal.
	 * 
	 * @param obj
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
				
		if( this == obj) return true;
		if((obj == null) || (obj.getClass() != this.getClass())) return false;
		Task other = (Task) obj;
		return this.getTitle().equals(other.getTitle());
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + getTitle().hashCode();		
		return hash;
	}

	@Override
	public void setDueDate(Calendar due) {
		setDueDate(due.getTime());
	}

	@Override
	public void setCompleted(Calendar completed) {
		setCompleted(completed.getTime());
	}
}
