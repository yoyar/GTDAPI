package com.yoyar.gtd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.yoyar.gtd.entities.Priority;
import com.yoyar.gtd.repositories.PriorityRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:com/yoyar/gtd/internal/system-test-config.xml")
@Transactional
public class PriorityRepositoryTests {

	@Autowired
	PriorityRepository priorityRepository;
	
	@Test
	public void testInjection() {
		assertNotNull(priorityRepository);
		assertTrue(priorityRepository instanceof PriorityRepository);
	}
	
	@Test
	public void testGet() {
		
		Priority p = priorityRepository.get(Priority.TYPE.LOW);
		assertNotNull(p);
		assertTrue(p instanceof Priority);
		assertEquals(Priority.TYPE.valueOf(p.getId()), Priority.TYPE.LOW);
	}
}












