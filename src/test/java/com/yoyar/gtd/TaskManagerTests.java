package com.yoyar.gtd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.yoyar.gtd.entities.ITask;
import com.yoyar.gtd.entities.TaskFactory;
import com.yoyar.gtd.internal.Priority;
import com.yoyar.gtd.util.GtdDateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:com/yoyar/gtd/internal/system-test-config.xml")
@Transactional
public class TaskManagerTests {

	@Autowired
	TaskManager taskManager;

	@Autowired
	private TaskFactory taskFactory;

	@Autowired
	SessionFactory sessionFactory;

	@BeforeClass
	public static void before() {
		
		ApplicationContext context = new ClassPathXmlApplicationContext(
			"com/yoyar/gtd/internal/system-test-config.xml"
		);

		LocalSessionFactoryBean bean = (LocalSessionFactoryBean) context
				.getBean("&sessionFactory");

		Configuration configuration = bean.getConfiguration();

		SchemaExport export = new SchemaExport(configuration);
		export.setOutputFile("gtd.sql");
		export.setDelimiter(";");
		export.execute(true, false, false, true);
	}

	@Before
	public void setUp() throws Exception {

		// for (int i = 0; i < 20; i++) {
		// ITask t = taskFactory.makeTask("Task " + i);
		// taskManager.saveOrUpdate(t);
		// }

		// Collection<Task> tasks = taskManager.getTasks();
		//
		// for( Task task : tasks) {
		// // System.out.println(task);
		// }
	}

	@After
	public void tearDown() throws Exception {
		taskManager.delete();
	}

	@Test
	public void testGetTask() {

		/* note: test-data.sql contains a task with id of 1 */

		long taskid = 1L;

		ITask task = taskManager.get(taskid);

		assertEquals(Long.valueOf(1), task.getId());
		assertEquals("test task 1", task.getTitle());
	}

	@Test
	public void testDeleteTask() {

		String title = "task to delete";

		ITask task = taskFactory.makeTask(title);

		taskManager.saveOrUpdate(task);

		long taskid = task.getId();

		assertNotNull(taskid);

		taskManager.delete(task);

		assertNull(taskManager.get(taskid));
	}

	@Test
	public void testCreateTaskWithAllFieldsFilledIn() {

		ITask parent = taskFactory.makeTask("parent task");
		taskManager.saveOrUpdate(parent);

		Calendar dueDate = Calendar.getInstance();
		dueDate.set(2011, 11, 19, 1, 1, 1);

		Calendar completedDate = Calendar.getInstance();
		completedDate.set(2011, 11, 28, 1, 1, 1);

		Priority p = Priority.HIGH;

		String originalTitle = "original task";

		ITask originalTask = taskFactory.makeTask(originalTitle);
		originalTask.setDueDate(dueDate);

		originalTask.setPriority(p);
		originalTask.setCompleted(completedDate);

		taskManager.saveOrUpdate(originalTask);

		Long originalTaskID = originalTask.getId();
		assertNotNull(originalTaskID);

		ITask taskFromDb = taskManager.get(originalTask.getId());

		Calendar ddCalendarFromDb = Calendar.getInstance();
		ddCalendarFromDb.setTime(taskFromDb.getDueDate());

		Calendar cdCalendarFromDb = Calendar.getInstance();
		cdCalendarFromDb.setTime(taskFromDb.getCompleted());

		assertTrue(GtdDateUtil.calendarsAreEqual(dueDate, ddCalendarFromDb));
		assertTrue(GtdDateUtil.calendarsAreEqual(completedDate,
				cdCalendarFromDb));
		assertEquals(originalTitle, taskFromDb.getTitle());
		assertEquals(p, taskFromDb.getPriority());

		assertEquals(originalTaskID, taskFromDb.getId());

	}

	@Test
	public void testGetParent() {

		String parentTaskTitle = "parent task title";
		String subTaskTitle = "sub task title";

		ITask parentTask = taskFactory.makeTask(parentTaskTitle);
		ITask subTask = taskFactory.makeTask(subTaskTitle);

		parentTask.add(subTask);

		taskManager.saveOrUpdate(parentTask);

		assertNotNull(parentTask.getId());
		assertNotNull(subTask.getId());
		assertNotNull(subTask.getParent());

		assertEquals(subTask.getParent(), parentTask);

		ITask parentFromDb = taskManager.get(parentTask.getId());

		ITask subTaskFromDb = parentFromDb.getTasks().get(0);

		assertNotNull(subTaskFromDb);
		assertEquals(subTaskTitle, subTaskFromDb.getTitle());
		assertEquals(parentTaskTitle, parentFromDb.getTitle());

	}

	@Test
	public void testAddSubTaskToParentTask() {

		String parentTaskTitle = "parent task title";
		String subTaskTitle = "sub task title";

		ITask parentTask = taskFactory.makeTask(parentTaskTitle);
		ITask subTask = taskFactory.makeTask(subTaskTitle);

		parentTask.add(subTask);
		taskManager.saveOrUpdate(parentTask);

		Long parentTaskId = parentTask.getId();
		Long subTaskId = subTask.getId();

		assertNotNull(parentTaskId);
		assertNotNull(subTaskId);

		assertEquals(parentTask, subTask.getParent());

		assertEquals(parentTask.getTasks().get(0), subTask);

	}

	@Test
	public void deleteAllIncludingTasksThatHaveSubTasks() {

		ITask parent = taskFactory.makeTask("parent");
		ITask subtask = taskFactory.makeTask("sub task");

		parent.add(subtask);

		taskManager.saveOrUpdate(parent);

		ITask parentFromDB = taskManager.get(parent.getId());
		ITask subtaskFromDB = taskManager.get(subtask.getId());

		assertNotNull(parent.getId());

		assertNotNull(subtask.getId());

		assertNotNull(parentFromDB.getId());

		assertNotNull(subtaskFromDB.getId());

		taskManager.delete();

		Session session = sessionFactory.getCurrentSession();

		long count = (Long) session.createQuery("select count(*) from Task")
				.uniqueResult();

		assertEquals(0, count);

	}

	@Test
	public void testUpdateTask() {

		long taskid = taskManager.getTasks().iterator().next().getId();

		ITask originalTask = taskManager.get(taskid);
		String origTaskTitle = originalTask.getTitle();

		String updatedTitle = "updated title for task 1";
		Calendar updatedDueDate = Calendar.getInstance();
		updatedDueDate.set(2012, 11, 19, 1, 1, 1);

		originalTask.setTitle(updatedTitle);
		originalTask.setDueDate(updatedDueDate.getTime());

		taskManager.saveOrUpdate(originalTask);

		ITask taskFromDbAfterUpdate = taskManager.get(taskid);

		assertFalse(origTaskTitle.equals(taskFromDbAfterUpdate.getTitle()));

		// hibernate updates the original task as well as the updated task obj
		assertTrue(originalTask.equals(taskFromDbAfterUpdate));

		assertEquals(updatedTitle, taskFromDbAfterUpdate.getTitle());

		assertTrue(GtdDateUtil.datesAreEqual(updatedDueDate.getTime(),
				taskFromDbAfterUpdate.getDueDate()));
	}

	@Test
	public void testAddTaskWithPriority() {

		Priority originalPriority = Priority.LOW;

		String title = "test task with priority";
		ITask task = taskFactory.makeTask(title);
		task.setPriority(originalPriority);
		taskManager.saveOrUpdate(task);

		ITask fromdb = taskManager.get(task.getId());

		assertEquals(fromdb.getPriority(), originalPriority);

	}

	@Test
	public void testAddTaskWithDueDate() {

		String title = "test task with due date";

		Calendar calOriginal = Calendar.getInstance();
		calOriginal.set(2011, 1, 1, 12, 1, 1);

		ITask task = taskFactory.makeTask(title);
		task.setDueDate(calOriginal.getTime());

		taskManager.saveOrUpdate(task);

		ITask fromDB = taskManager.get(task.getId());

		Calendar calFromDB = Calendar.getInstance();
		calFromDB.setTime(fromDB.getDueDate());

		assertTrue(GtdDateUtil.calendarsAreEqual(calFromDB, calOriginal));

	}

	@Test
	public void testDeleteAll() {

		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("select count(*) from Task");

		taskManager.delete();

		assertEquals(0L, q.uniqueResult());

		ITask t1 = taskFactory.makeTask("t1");
		ITask t2 = taskFactory.makeTask("t2");

		taskManager.saveOrUpdate(t1);
		taskManager.saveOrUpdate(t2);

		assertEquals(2L, q.uniqueResult());

		taskManager.delete();

		assertEquals(0L, q.uniqueResult());
	}

	@Test
	public void testAddTask() {

		String title = "Test Task 1a";

		ITask originalTask = taskFactory.makeTask(title);
		taskManager.saveOrUpdate(originalTask);

		assertNotNull(originalTask.getId());

		Long taskid = originalTask.getId();

		ITask taskFromDB = taskManager.get(originalTask.getId());

		assertEquals(title, taskFromDB.getTitle());
		assertEquals(taskid, taskFromDB.getId());
	}
}
