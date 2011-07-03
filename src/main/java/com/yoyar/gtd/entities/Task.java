package com.yoyar.gtd.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.yoyar.gtd.internal.Priority;

@javax.persistence.Entity
@Table(name = "Task")
@javax.persistence.SequenceGenerator(name = "gtd_taskid_seq", allocationSize = 1)
class Task implements ITask {

	@Id
	@Column(name = "taskid")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "gtd_taskid_seq")
	private Long taskid = null;

	@OneToMany(
			targetEntity=Task.class, 
			mappedBy="taskid", 
			cascade=CascadeType.ALL,
			fetch=FetchType.LAZY
	)
	
	private List<ITask> tasks = new ArrayList<ITask>();

	@SuppressWarnings("unused")
	@Column(insertable=false, updatable=false)
	private Long parent_taskid;

	@ManyToOne(fetch = FetchType.LAZY, optional = true, targetEntity = Task.class)
	@JoinColumn(insertable = true, name = "parent_taskid", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private ITask parent;

	@Column(name = "title", nullable=false)
	private String title;

	@Column(name = "priorityid")
	@Enumerated(EnumType.ORDINAL)
	private Priority priority;

	@Column(name = "duedate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dueDate;

	@Column(name = "completed")
	@Temporal(TemporalType.TIMESTAMP)
	private Date completed;

	public Task() {
	}

	@Override
	public Long getId() {
		return taskid;
	}

	@Override
	public ITask getParent() {
		return parent;
	}

	@Override
	public void setParent(ITask parent) {
		this.parent = parent;
	}

	@Override
	public void setId(long id) {
		this.taskid = id;
	}

	@Override
	public void add(ITask task) {
		task.setParent(this);
		this.tasks.add(task);
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {

		if (null == title || "" == title) {
			throw new IllegalArgumentException("title cannot be null");
		}

		this.title = title;
	}

	@Override
	public List<ITask> getTasks() {
		return this.tasks;
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
	public void setDueDate(Calendar due) {
		setDueDate(due.getTime());
	}

	@Override
	public void setCompleted(Calendar completed) {
		setCompleted(completed.getTime());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[Task taskid=").append(getId())
				.append(" title='").append(getTitle()).append("'")
				.append(" dueDate=").append(getDueDate()).append("]");

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

		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		ITask other = (ITask) obj;
		return this.getTitle().equals(other.getTitle());
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + getTitle().hashCode();
		return hash;
	}

}
