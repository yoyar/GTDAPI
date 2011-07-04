package com.yoyar.gtd.repositories;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Repository;

import com.yoyar.gtd.entities.Priority;

@Repository("priorityRepository")
public class HibernatePriorityRepository implements PriorityRepository {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public Priority get(Priority.TYPE priorityType) {
		
		Priority p = (Priority)sessionFactory.getCurrentSession().get(
			Priority.class, priorityType.toString()
		);
		
		if( p == null ) {
			throw new DataRetrievalFailureException(
					"Unable to find Priority record for enum: " 
					+ priorityType.toString()
			);
		}
		
		return p;
	}
}
