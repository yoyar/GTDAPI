package com.yoyar.gtd.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yoyar.gtd.entities.Priority;
import com.yoyar.gtd.entities.PriorityEnum;
import com.yoyar.gtd.entities.Task;

@Repository("taskRepository")
class HibernateTaskRepositoryImpl implements TaskRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}
	
	@Override
	public Task saveOrUpdate(Task task) {
				
		getCurrentSession().saveOrUpdate(task);
		return task;
	}

	@Override
	public Task get(long taskid) {
		return (Task) getCurrentSession().get(Task.class, taskid);
	}

	@Override
	public long delete(Task task) {
		long taskid = task.getId();
		getCurrentSession().delete(task);
		return taskid;
	}

	@Override
	public int delete() {
		
		return getCurrentSession().createQuery(
				"delete from Task t where t.parent_taskid is null"
				)
				.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> getTasks() {
		return (List<Task>)getCurrentSession().createQuery(
			"from Task t where t.parent_taskid is null"
		).list();
	}
}
