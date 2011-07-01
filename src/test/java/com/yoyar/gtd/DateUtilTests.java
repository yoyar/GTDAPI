package com.yoyar.gtd;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class DateUtilTests {

	@Test
	public void testDatesAreEqual() {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		
		c1.set(2011, 11, 19, 1, 2, 1);
		c2.set(2011, 11, 19, 1, 2, 1);
		
		Date d1 = c1.getTime();
		Date d2 = c2.getTime();
		
		assertTrue(DateUtil.datesAreEqual(d1, d2));
		
		c2.set(Calendar.MILLISECOND, 111);
		d2 = c2.getTime();
		
		assertThat(d1, not(equalTo(d2)));
		
		assertTrue(DateUtil.datesAreEqual(d1, d2));
		
		c2.set(2012, 11, 19, 1, 2, 1);
		d2 = c2.getTime();
		
		assertFalse(DateUtil.datesAreEqual(d1, d2));
	}
	
	@Test
	public void testCalendarsAreEqual() {
		
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		
		c1.set(2011, 11, 19, 1, 2, 1);
		c2.set(2011, 11, 19, 1, 2, 1);
		
		assertTrue(DateUtil.calendarsAreEqual(c1, c2));
		
		c2.set(Calendar.MILLISECOND, 100);
		
		assertThat(
				c1.get(Calendar.MILLISECOND), 
				not(equalTo(c2.get(Calendar.MILLISECOND)))
		);
		
		assertTrue(DateUtil.calendarsAreEqual(c1, c2));
		
		c2.set(2011, 10, 19, 1, 2, 2);
		
		assertFalse(DateUtil.calendarsAreEqual(c1, c2));
		
	}
}
