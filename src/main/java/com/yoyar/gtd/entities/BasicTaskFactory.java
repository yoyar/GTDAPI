package com.yoyar.gtd.entities;

import org.springframework.stereotype.Component;


/**
 * Produces objects of type Task
 */
@Component("taskFactory")
public class BasicTaskFactory implements TaskFactory {

	public ITask makeTask(String title) {
		
		if( title == null || title == "") {
			throw new IllegalArgumentException(
					"When creating a Task the title cannot be null."
			);
		}
		
		ITask task = new Task();
		task.setTitle(title);
		return task;
	}
}
