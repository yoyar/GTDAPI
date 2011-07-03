package com.yoyar.gtd.entities;


public enum Priority {
	TOP("Top"),
	HIGH("High"),
	MEDIUM("Medium"),
	LOW("Low");
	
	public String label;
	
	Priority(String label) {
		this.label = label;
	}
}
