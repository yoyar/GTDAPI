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
		

		Task task = new Task();
		task.setTitle(title);
		
		
		task.setPriority(PriorityEnum.LOW.getPriorityDAO());
		
//		Priority p = (Priority) getCurrentSession().get(
//			Priority.class, PriorityEnum.LOW.toString()
//		);
//		task.setPriority(p);
		
		
		return task;
	}
}
