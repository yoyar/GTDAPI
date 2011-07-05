package com.yoyar.gtd.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yoyar.gtd.repositories.PriorityRepository;


/**
 * Produces objects of type Task
 */
@Component("taskFactory")
public class BasicTaskFactory implements TaskFactory {

	@Autowired
	PriorityRepository priorityRepository;
	
	public Task makeTask(String title) {
		
		if( title == null || title == "") {
			throw new IllegalArgumentException(
					"When creating a Task the title cannot be null."
			);
		}

		Task task = new Task(title);
		
		task.setPriority(
				priorityRepository.get(Priority.TYPE.LOW)
		);
		
		return task;
	}
}
