package com.yoyar.gtd;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yoyar.gtd.entities.Task;
import com.yoyar.gtd.entities.TaskRepository;

@Component("taskManager")
public class TaskManagerImpl implements TaskManager {

	@Autowired
	private TaskRepository taskRepository;

	public TaskManagerImpl() {
	}

	@Override
	public Task saveOrUpdate(Task task) {
		return taskRepository.addOrUpdate(task);
	}

	@Override
	@Deprecated
	public Task add(Task parent, Task task) {
		
		if( parent.getId() == null) {
			throw new IllegalArgumentException("The parent task's id must not be null");
		}
		
		//task.setParentId(parent.getId());
		
		return taskRepository.addOrUpdate(task);
		
	}

	@Override
	public Task get(long taskid) {
		Task task = taskRepository.get(taskid);
		return task;
	}

	@Override
	public void delete() {
		taskRepository.delete();
	}

	@Override
	public Task update(Task task) {
		return taskRepository.addOrUpdate(task);
	}

	@Override
	public List<Task> getTasks() {
		return taskRepository.getTasks();
	}

	@Override
	public List<Task> getTasks(Task parentTask) {
		return taskRepository.getTasks(parentTask);
	}

	@Override
	public long delete(Task task) {
		return taskRepository.delete(task);
	}

}
