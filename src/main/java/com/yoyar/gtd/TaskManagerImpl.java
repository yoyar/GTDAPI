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
		return taskRepository.addOrUpdate(task);
	}

	@Override
	@Deprecated
	public ITask add(ITask parent, ITask task) {
		
		if( parent.getId() == null) {
			throw new IllegalArgumentException("The parent task's id must not be null");
		}
		
		//task.setParentId(parent.getId());
		
		return taskRepository.addOrUpdate(task);
		
	}

	@Override
	public ITask get(long taskid) {
		ITask task = taskRepository.get(taskid);
		return task;
	}

	@Override
	public void delete() {
		taskRepository.delete();
	}

	@Override
	public ITask update(ITask task) {
		return taskRepository.addOrUpdate(task);
	}

	@Override
	public List<ITask> getTasks() {
		return taskRepository.getTasks();
	}

	@Override
	public List<ITask> getTasks(ITask parentTask) {
		return taskRepository.getTasks(parentTask);
	}

	@Override
	public long delete(ITask task) {
		return taskRepository.delete(task);
	}

}
