package com.yoyar.gtd.util;

import java.util.Calendar;
import java.util.Date;

public class GtdDateUtil {

	/**
	 * Compare dates, excluding millis. If the year, month, day, hours,
	 * minutes, seconds are the same, then dates are equal. Millis don't matter.
	 * 
	 * @param c1
	 * @param c2
	 * @return boolean
	 */
	public static boolean calendarsAreEqual(Calendar c1, Calendar c2) {
	
		return 	c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) 
			&&	c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
			&&	c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)
			&&	c1.get(Calendar.HOUR_OF_DAY) == c2.get(Calendar.HOUR_OF_DAY)
			&& 	c1.get(Calendar.MINUTE) == c2.get(Calendar.MINUTE)
			&& 	c1.get(Calendar.SECOND) == c2.get(Calendar.SECOND)
		;
	}
	
	/**
	 * Compare dates, excluding millis. If the year, month, day, hours,
	 * minutes, seconds are the same, then dates are equal. Millis don't matter.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean datesAreEqual(Date d1, Date d2) {
		
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		
		c1.setTime(d1);
		c2.setTime(d2);
		
		return calendarsAreEqual(c1, c2);
	}
}
