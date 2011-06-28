package com.yoyar.gtd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yoyar.gtd.internal.Priority;
import com.yoyar.gtd.internal.Task;
import com.yoyar.gtd.internal.TaskFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:com/yoyar/gtd/internal/system-test-config.xml")
public class TaskManagerTests {

	@Autowired
	TaskManager taskManager;

	@Autowired
	private TaskFactory taskFactory;

	@Before
	public void setUp() throws Exception {
		
		for (int i = 0; i < 20; i++) {
			Task t = taskFactory.makeTask("Task " + i);
			taskManager.add(t);
		}
		
		Collection<Task> tasks = taskManager.getTopLevelTasks();
		
		for( Task task : tasks) {
			System.out.println(task);
		}
	}

	@After
	public void tearDown() throws Exception {
		taskManager.deleteAll();
	}
	
	
	
	@Test
	public void testUpdateTask() {
		
		long taskid = taskManager.getTopLevelTasks().iterator().next().getEntityId();
		
		Task originalTask = taskManager.getTask(taskid);
		
		String updatedTitle = "updated title for task 1";
		Calendar updatedDueDate = Calendar.getInstance();
		updatedDueDate.set(2012, 11, 19, 1, 1, 1);
			
		originalTask.setTitle(updatedTitle);
		originalTask.setDueDate(updatedDueDate.getTime());
		
		taskManager.update(originalTask);
		
		Task taskFromDbAfterUpdate = taskManager.getTask(taskid);
		
		assertEquals(updatedTitle, taskFromDbAfterUpdate.getTitle());
	}

	@Test
	public void testMakeTaskWithTitle() {

		String title = "Test Task 1";

		Task task = taskFactory.makeTask(title);

		assertTrue(task instanceof Task);
		assertEquals(title, task.getTitle());

		Task task2 = taskFactory.makeTask(title);
		assertTrue(task2 instanceof Task);
		assertEquals(title, task.getTitle());
	}
	
	@Test 
	public void testAddTaskWithPriority() {
		
		Priority originalPriority = Priority.LOW;
		
		String title = "test task with priority";
		Task task = taskFactory.makeTask(title);
		task.setPriority(originalPriority);
		taskManager.add(task);
		
		Task fromdb = taskManager.getTask(task.getEntityId());
		
		assertEquals(fromdb.getPriority(), originalPriority);
		
	}
	
	@Test
	public void testAddTaskWithDueDate() {

		String title = "test task with due date";

		Calendar calOriginal = Calendar.getInstance();
		calOriginal.set(2011, 1, 1, 12, 1, 1);

		Task task = taskFactory.makeTask(title);
		task.setDueDate(calOriginal.getTime());

		taskManager.add(task);

		Task fromDB = taskManager.getTask(task.getEntityId());

		Calendar calFromDB = Calendar.getInstance();
		calFromDB.setTime(fromDB.getDueDate());

		assertEquals(calOriginal.get(Calendar.YEAR),
				calFromDB.get(Calendar.YEAR));
		assertEquals(calOriginal.get(Calendar.MONTH),
				calFromDB.get(Calendar.MONTH));
		assertEquals(calOriginal.get(Calendar.DAY_OF_MONTH),
				calFromDB.get(Calendar.DAY_OF_MONTH));
		assertEquals(calOriginal.get(Calendar.HOUR_OF_DAY),
				calFromDB.get(Calendar.HOUR_OF_DAY));
		assertEquals(calOriginal.get(Calendar.MINUTE),
				calFromDB.get(Calendar.MINUTE));
		assertEquals(calOriginal.get(Calendar.SECOND),
				calFromDB.get(Calendar.SECOND));
	}

	@Test
	public void testAddTask() {

		String title = "Test Task 1";

		Task originalTask = taskFactory.makeTask(title);
		taskManager.add(originalTask);

		Task taskFromDB = taskManager.getTask(originalTask.getEntityId());

		assertEquals(taskFromDB.getTitle(), originalTask.getTitle());
		assertEquals(taskFromDB.getEntityId(), originalTask.getEntityId());
	}
}
