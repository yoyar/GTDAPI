package com.yoyar.gtd;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yoyar.gtd.entities.ITask;
import com.yoyar.gtd.entities.TaskRepository;

@Component("taskManager")
public class TaskManagerImpl implements TaskManager {

	@Autowired
	private TaskRepository taskRepository;

	public TaskManagerImpl() {
	}

	@Override
	public ITask saveOrUpdate(ITask task) {
		return taskRepository.saveOrUpdate(task);
	}

	@Override
	public ITask get(long taskid) {
		return taskRepository.get(taskid);
	}

	@Override
	public void delete() {
		taskRepository.delete();
	}

	@Override
	public List<ITask> getTasks() {
		return taskRepository.getTasks();
	}

	@Override
	public long delete(ITask task) {
		return taskRepository.delete(task);
	}
}
