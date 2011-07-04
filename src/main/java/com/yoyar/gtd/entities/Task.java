package com.yoyar.gtd.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

@javax.persistence.Entity
@Table(name = "Task")
@javax.persistence.SequenceGenerator(name = "gtd_taskid_seq", allocationSize = 1)
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "gtd_taskid_seq")
	private Long taskid = null;

	@OneToMany(
			cascade=CascadeType.ALL,
			fetch=FetchType.LAZY,
			mappedBy="parent_taskid",
			targetEntity=Task.class
	)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Task> tasks = new ArrayList<Task>();

	@SuppressWarnings("unused")
	@Column(insertable=false, updatable=false)
	private Long parent_taskid;

	@ManyToOne(fetch = FetchType.LAZY, optional = true, targetEntity = Task.class)
	@JoinColumn(name = "parent_taskid")
	private Task parent;

	@Column(name = "title", nullable=false)
	private String title;

	@Column(name = "notes", nullable=true)
	private String notes;
	
	@ManyToOne(
		fetch=FetchType.EAGER,
		optional=false,
		targetEntity=Priority.class
	)
	@JoinColumn(name="priorityid")
	private Priority priority;
	
	@Column(name = "duedate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dueDate;

	@Column(name = "completed")
	@Temporal(TemporalType.TIMESTAMP)
	private Date completed;

	public Task() {
	}

	public Long getId() {
		return taskid;
	}

	public Task getParent() {
		return parent;
	}

	public void setParent(Task parent) {
		this.parent = parent;
	}

	public void setId(long id) {
		this.taskid = id;
	}

	public void add(Task task) {
		task.setParent(this);
		this.tasks.add(task);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {

		if (null == title || "" == title) {
			throw new IllegalArgumentException("title cannot be null");
		}

		this.title = title;
	}

	public List<Task> getTasks() {
		return this.tasks;
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

	public void setDueDate(Calendar due) {
		setDueDate(due.getTime());
	}

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
		Task other = (Task) obj;
		return this.getTitle().equals(other.getTitle());
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + getTitle().hashCode();
		return hash;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNotes() {
		return notes;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Priority getPriority() {
		return priority;
	}

}
