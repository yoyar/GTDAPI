package com.yoyar.gtd.entities;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("taskRepository")
class HibernateTaskRepositoryImpl implements TaskRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public ITask saveOrUpdate(ITask task) {
		getCurrentSession().saveOrUpdate(task);
		return task;
	}

	@Override
	public ITask get(long taskid) {
		return (ITask) getCurrentSession().get(Task.class, taskid);
	}

	@Override
	public long delete(ITask task) {
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
	public List<ITask> getTasks() {
		return (List<ITask>)getCurrentSession().createQuery(
			"from Task t where t.parent_taskid is null"
		).list();
	}
}
