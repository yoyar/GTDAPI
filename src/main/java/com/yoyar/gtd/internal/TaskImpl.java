package com.yoyar.gtd.internal;

import java.util.Date;
import java.util.List;

class TaskImpl implements Task {

	private Long id = null;

	private Long parentid = null;
	
	@Override
	public Long getParentId() {
		return parentid;
	}

	@Override
	public void setParentId(Long parentid) {
		this.parentid = parentid;
	}

	private List<Task> tasks;

	private String title;

	private Priority priority;

	private Date dueDate;

	private Date completed;

	public TaskImpl() {
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

//	public List<Task> getTasks() {
//		return tasks;
//	}
//
//	public void setTasks(List<Task> tasks) {
//		this.tasks = tasks;
//	}

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
}
