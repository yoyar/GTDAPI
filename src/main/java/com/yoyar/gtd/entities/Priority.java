package com.yoyar.gtd.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;

@Entity(name="Priority")
@Immutable
public class Priority {

	@Id
	@Column(length=8)
	private String priorityid;
	
	@Column(length=8)
	private String label;
	
	@Column(columnDefinition="char(6)")
	private String hexColor;

	@Column
	private int sortorder;
	
	/**
	 * These match the IDs of of each priority stored in the database.
	 */
	static public enum TYPE {
		TOP,
		HIGH,
		MEDIUM,
		LOW;
	}
	
	public void setId(String id) {
		this.priorityid = id;
	}

	public String getId() {
		return priorityid;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setHexColor(String hexColor) {
		this.hexColor = hexColor;
	}

	public String getHexColor() {
		return hexColor;
	}

	public void setSortorder(int sortorder) {
		this.sortorder = sortorder;
	}

	public int getSortorder() {
		return sortorder;
	}
}
