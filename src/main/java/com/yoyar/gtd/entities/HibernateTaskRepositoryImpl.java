package com.yoyar.gtd.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Component("taskRepository")
class HibernateTaskRepositoryImpl implements TaskRepository {

	private SessionFactory sessionFactory;
	
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public ITask addOrUpdate(ITask task) {
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

	/**
	 * TODO delete me
	 */
	@Override
	@Deprecated
	public long delete(long taskid) {
		
//		Task t = get(taskid);
//		delete(t);
		
		Query q = getCurrentSession().createQuery(
				"delete from :type t where t.taskid = :taskid"
				).setString("type", Task.class.getName())
				.setLong("taskid", taskid);
		
		System.out.println(q.getQueryString() + " taskid: "+ taskid);
		
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

	@Override
	@Deprecated
	public List<ITask> getTasks(ITask parentTask) {
		throw new NotImplementedException();
	}
	
	

}
