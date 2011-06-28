package com.yoyar.gtd.internal;

/**
 * 
 * @author matt
 *
 */
public class BasicTaskFactory implements TaskFactory {

	public Task makeTask(String title) {
		
		if( title == null || title == "") {
			throw new IllegalArgumentException(
					"When creating a Task the title cannot be null."
			);
		}
		
		Task task = new TaskImpl();
		task.setTitle(title);
		return task;
	}
}
