package com.yoyar.gtd.entities;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public enum PriorityEnum {
	TOP,
	HIGH,
	MEDIUM,
	LOW;
	
	static final SessionFactory sf;
	// TODO needs to be refactored.
	static {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"com/yoyar/gtd/internal/system-test-config.xml"
			);

		sf = (SessionFactory)context.getBean("sessionFactory");		
	}
	
	private PriorityEnum() {
	}
	
	public Priority getPriorityDAO() {
		return (Priority)sf.getCurrentSession().get(Priority.class, this.toString());
	}
	
}
