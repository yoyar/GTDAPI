package com.yoyar.gtd;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yoyar.gtd.internal.Task;
import com.yoyar.gtd.internal.TaskRepository;

@Component("taskManager")
public class TaskManagerImpl implements TaskManager {

	@Autowired
	private TaskRepository taskRepository;

	public TaskManagerImpl() {
	}

	@Override
	public Task add(Task task) {
		
		return taskRepository.addOrUpdate(task);

	}

	@Override
	public Task add(Task parent, Task task) {
		
		if( parent.getEntityId() == null) {
			throw new IllegalArgumentException("The parent task's id must not be null");
		}
		
		task.setParentId(parent.getEntityId());
		
		return taskRepository.addOrUpdate(task);
		
	}

	@Override
	public Task get(long taskid) {
		Task task = taskRepository.getTask(taskid);
		return task;
	}

	@Override
	public void deleteAll() {
		// TODO: with sub tasks this likely needs to delete recursively, starting first with leaf nodes.
		taskRepository.delete();
	}

	@Override
	public Task update(Task task) {
		return taskRepository.addOrUpdate(task);
	}

	@Override
	public List<Task> getTopLevelTasks() {
		return taskRepository.getTasks();
	}

	@Override
	public List<Task> getTasks(Task parentTask) {
		return taskRepository.getTasks(parentTask);
	}
}
