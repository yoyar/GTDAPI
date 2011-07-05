package com.yoyar.gtd;

import static org.junit.Assert.*;

import java.util.TimeZone;

import org.junit.Test;

public class TimezoneTests {

	/**
	 * All dates should appear as GMT time. All such dates should be stored
	 * in the db as GMT with no offset.
	 */
	@Test
	public void testDefaultTimeZoneIsGMT() {
		
		TimeZone t = TimeZone.getTimeZone("GMT");
		
		assertEquals(
				"The timezone should be set JVM wide as GMT. " +
				"The argument to the JVM is: -Duser.timezone=\"GMT\". " +
				"You can set this in the Installed JREs screen in Eclipse. ",
				t,
				TimeZone.getDefault()
				);
		
	}
}
