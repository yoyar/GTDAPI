package com.yoyar.gtd;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yoyar.gtd.internal.Priority;
import com.yoyar.gtd.internal.Task;
import com.yoyar.gtd.internal.TaskFactory;
import com.yoyar.gtd.util.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:com/yoyar/gtd/internal/system-test-config.xml")
public class TaskManagerTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
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
		
		Collection<Task> tasks = taskManager.getTasks();
		
		for( Task task : tasks) {
			//System.out.println(task);
		}
	}

	@After
	public void tearDown() throws Exception {
		taskManager.delete();
	}
	
	@Test
	public void testCreateTaskWithAllFieldsFilledIn() {
				
		Task parent = taskFactory.makeTask("parent task");
		taskManager.add(parent);
		
		Calendar dueDate = Calendar.getInstance();
		dueDate.set(2011, 11, 19, 1, 1, 1);
		
		Calendar completedDate = Calendar.getInstance();
		completedDate.set(2011, 11, 28, 1, 1, 1);
		
		Priority p = Priority.HIGH;
		
		String originalTitle = "original task";
		
		Task originalTask = taskFactory.makeTask(originalTitle);
		originalTask.setDueDate(dueDate);
		originalTask.setParentId(parent.getEntityId());
		originalTask.setPriority(p);
		originalTask.setCompleted(completedDate);
		
		taskManager.add(originalTask);
		
		Long originalTaskID = originalTask.getEntityId();
		assertNotNull(originalTaskID);
		
		Task taskFromDb = taskManager.get(originalTask.getEntityId());
		
		Calendar ddCalendarFromDb = Calendar.getInstance();
		ddCalendarFromDb.setTime(taskFromDb.getDueDate());
		
		Calendar cdCalendarFromDb = Calendar.getInstance();
		cdCalendarFromDb.setTime(taskFromDb.getCompleted());
		
		assertTrue(DateUtil.calendarsAreEqual(dueDate, ddCalendarFromDb));
		assertTrue(DateUtil.calendarsAreEqual(completedDate, cdCalendarFromDb));
		assertEquals(originalTitle, taskFromDb.getTitle());
		assertEquals(p, taskFromDb.getPriority());
		assertEquals(parent.getEntityId(), taskFromDb.getParentId());
		assertEquals(originalTaskID, taskFromDb.getEntityId());
		
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testAddSubTaskToParentWhenParentsIdIsNull() {
		
		String parentTaskTitle = "parent task title";
		String subTaskTitle = "sub task title";
		
		Task parentTask = taskFactory.makeTask(parentTaskTitle);
		
		assertNull(parentTask.getEntityId());
		
		Task subTask = taskFactory.makeTask(subTaskTitle);
		
		taskManager.add(parentTask, subTask);
		
	}
	
	
	@Test
	public void testAddSubTaskToParentTask() {
		
		String parentTaskTitle = "parent task title";
		String subTaskTitle = "sub task title";
		
		Task parentTask = taskFactory.makeTask(parentTaskTitle);
		
		taskManager.add(parentTask);
		
		assertNotNull(parentTask.getEntityId());
		
		long originalParentTaskID = parentTask.getEntityId();
		
		Task subTask = taskFactory.makeTask(subTaskTitle);
		
		taskManager.add(parentTask, subTask);
		
		assertNotNull(subTask.getEntityId());
		
		long originalSubTaskID = subTask.getEntityId();
		
		Task parentTaskFromDb = taskManager.get(originalParentTaskID);
		
		List<Task> subTasks = taskManager.getTasks(parentTaskFromDb);
		
		assertEquals(1, subTasks.size());
		
		// there is only one subTask so it must be at idx zero
		Task subTaskFromDB = subTasks.get(0);
		
		assertTrue(subTasks.contains(subTask));
		assertEquals(originalParentTaskID, (long)parentTaskFromDb.getEntityId());
		assertEquals(originalSubTaskID, (long)subTaskFromDB.getEntityId());		
		assertEquals(parentTaskTitle, parentTaskFromDb.getTitle());
		assertEquals(subTaskTitle, subTaskFromDB.getTitle());
	}
	
	@Test 
	public void deleteAllIncludingTasksThatHaveSubTasks() {
		Task parent = taskFactory.makeTask("parent");
		Task subtask = taskFactory.makeTask("sub task");
		
		taskManager.add(parent);
		taskManager.add(parent, subtask);
		
		Task parentFromDB = taskManager.get(parent.getEntityId());
		Task subtaskFromDB = taskManager.get(subtask.getEntityId());
				
		assertNotNull(parent.getEntityId());
		assertNull(parent.getParentId());
		
		assertNotNull(subtask.getEntityId());
		assertNotNull(subtask.getParentId());
		
		assertEquals(parent.getEntityId(), subtask.getParentId());
		
		assertNotNull(parentFromDB.getEntityId());
		
		assertNull(parentFromDB.getParentId());
		
		assertNotNull(subtaskFromDB.getEntityId());
		assertNotNull(subtaskFromDB.getParentId());
		
		assertEquals(parentFromDB.getEntityId(), subtaskFromDB.getParentId());
		
		taskManager.delete();
		
		assertNull(taskManager.get(parent.getEntityId()));
		assertNull(taskManager.get(subtask.getEntityId()));
		
		// try to find any task at all
		String sql = "select count(*) as numResults from Task";
		int count = jdbcTemplate.queryForInt(sql);
		assertEquals(0, count);
		
	}
	
	
	@Test
	public void testUpdateTask() {
		
		long taskid = taskManager.getTasks().iterator().next().getEntityId();
		
		Task originalTask = taskManager.get(taskid);
		
		String updatedTitle = "updated title for task 1";
		Calendar updatedDueDate = Calendar.getInstance();
		updatedDueDate.set(2012, 11, 19, 1, 1, 1);
			
		originalTask.setTitle(updatedTitle);
		originalTask.setDueDate(updatedDueDate.getTime());
		
		taskManager.update(originalTask);
		
		Task taskFromDbAfterUpdate = taskManager.get(taskid);
		
		assertEquals(updatedTitle, taskFromDbAfterUpdate.getTitle());
	}

	
	
	@Test 
	public void testAddTaskWithPriority() {
		
		Priority originalPriority = Priority.LOW;
		
		String title = "test task with priority";
		Task task = taskFactory.makeTask(title);
		task.setPriority(originalPriority);
		taskManager.add(task);
		
		Task fromdb = taskManager.get(task.getEntityId());
		
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

		Task fromDB = taskManager.get(task.getEntityId());

		Calendar calFromDB = Calendar.getInstance();
		calFromDB.setTime(fromDB.getDueDate());

		assertTrue(DateUtil.calendarsAreEqual(calFromDB, calOriginal));
		
//		assertEquals(calOriginal.get(Calendar.YEAR),
//				calFromDB.get(Calendar.YEAR));
//		assertEquals(calOriginal.get(Calendar.MONTH),
//				calFromDB.get(Calendar.MONTH));
//		assertEquals(calOriginal.get(Calendar.DAY_OF_MONTH),
//				calFromDB.get(Calendar.DAY_OF_MONTH));
//		assertEquals(calOriginal.get(Calendar.HOUR_OF_DAY),
//				calFromDB.get(Calendar.HOUR_OF_DAY));
//		assertEquals(calOriginal.get(Calendar.MINUTE),
//				calFromDB.get(Calendar.MINUTE));
//		assertEquals(calOriginal.get(Calendar.SECOND),
//				calFromDB.get(Calendar.SECOND));
	}

	@Test
	public void testAddTask() {

		String title = "Test Task 1";

		Task originalTask = taskFactory.makeTask(title);
		taskManager.add(originalTask);

		Task taskFromDB = taskManager.get(originalTask.getEntityId());

		assertEquals(taskFromDB.getTitle(), originalTask.getTitle());
		assertEquals(taskFromDB.getEntityId(), originalTask.getEntityId());
	}
}
