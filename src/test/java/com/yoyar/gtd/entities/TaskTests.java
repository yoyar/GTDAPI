package com.yoyar.gtd.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yoyar.gtd.entities.ITask;
import com.yoyar.gtd.entities.TaskFactory;
import com.yoyar.gtd.entities.Task;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:com/yoyar/gtd/internal/system-test-config.xml")
public class TaskTests {

	@Autowired
	private TaskFactory taskFactory;
	
	@Before
	public void setUp() throws Exception {
	}
		
	@Test
	public void testMakeTaskWithTitle() {

		String title = "Test Task 1";

		ITask task = taskFactory.makeTask(title);

		assertTrue(task instanceof ITask);
		assertEquals(title, task.getTitle());

		ITask task2 = taskFactory.makeTask(title);
		assertTrue(task2 instanceof ITask);
		assertEquals(title, task.getTitle());
	}
	
	@Test
	public void testEquals() {
		ITask task1 = getTask1();
		ITask task2 = getTask2();
		assertEquals(task1, task2);
	}
	
	@Test
	public void testHashCode() {
		assertEquals(getTask1().hashCode(), getTask2().hashCode());
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testSetNullTitle() {
		ITask task1 = getTask1();
		task1.setTitle(null);
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testSetNullStringTitle() {
		ITask task1 = getTask1();
		task1.setTitle("");
	}
		
	@Test(expected=IllegalArgumentException.class) 
	public void testMakeTaskWithNullTitle() {
		taskFactory.makeTask(null);
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testMakeTaskWithNullStringTitle() {
		taskFactory.makeTask("");
	}
	
	
	
	static final Calendar aDueDate = Calendar.getInstance();
	
	static {
		aDueDate.set(2012, 0, 19, 1, 1, 1);
	}
	
	static final String title = "test equals and hash code title";
	
	/* 
	 * task1 and task2 are meant to be different objects, but they should turn
	 * out to be equal when using equals() and hashCode(), They should have the 
	 * same title and due date for testing purposes. In other words, the 
	 * properties of each should be equal.
	 */
	ITask getTask1() {
		
		ITask task1 = new Task();
		task1.setTitle(title);
		task1.setDueDate(aDueDate.getTime());
		
		return task1;
	}
	
	ITask getTask2() {
		ITask task2 = new Task();
		task2.setTitle(title);
		task2.setDueDate(aDueDate.getTime());
		return task2;
	}

}
