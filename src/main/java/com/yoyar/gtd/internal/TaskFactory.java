package com.yoyar.gtd.internal;

public interface TaskFactory {

	/**
	 * Create a Task. title should never be null or the null string.
	 * 
	 * @param title
	 * @return Task
	 */
	public Task makeTask(String title);
}