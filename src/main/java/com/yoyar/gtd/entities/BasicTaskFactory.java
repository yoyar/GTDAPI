package com.yoyar.gtd.entities;

import org.springframework.stereotype.Component;


/**
 * Produces objects of type Task
 */
@Component("taskFactory")
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
