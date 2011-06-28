package com.yoyar.gtd;

import java.util.Collection;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;

import com.yoyar.gtd.internal.Priority;
import com.yoyar.gtd.internal.Task;
import com.yoyar.gtd.internal.TaskRepository;

public class TaskManagerImpl implements TaskManager {

	@Autowired
	private TaskRepository taskRepository;

	public TaskManagerImpl() {
	}

	@Override
	public Task add(Task task) {
		if( task.getPriority() == null) {
			task.setPriority(Priority.UNSET);
		}
		return taskRepository.addOrUpdate(task);

	}

	@Override
	public Task add(Task parent, Task task) {
		throw new NotImplementedException();
	}

	@Override
	public Task getTask(long taskid) {
		Task task = taskRepository.getTask(taskid);
		return task;
	}

	@Override
	public void deleteAll() {
		taskRepository.deleteAll();
	}

	@Override
	public Task update(Task task) {
		return taskRepository.addOrUpdate(task);
	}

	@Override
	public Collection<Task> getTopLevelTasks() {
		return taskRepository.getTopLevelTasks();
	}
}
