package jre.busapp.test;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import jre.bus.DateUtil;

import android.test.AndroidTestCase;

public class DateUtilTest extends AndroidTestCase {

	public void testAddMinutes() {
		// SUNDAY, December 26rd, 6:21PM 2010
		Calendar startTime = Calendar.getInstance();
		startTime.setTimeZone(TimeZone.getTimeZone("EST"));
		startTime.setTimeInMillis(1293405712000L);
		
		Date newTime = DateUtil.addMinutes(startTime.getTimeInMillis(), 5);
		
		Calendar newCal = Calendar.getInstance();
		newCal.setTimeZone(TimeZone.getTimeZone("EST"));
		newCal.setTimeInMillis(newTime.getTime());
		
		assertEquals(18, newCal.get(Calendar.HOUR_OF_DAY));
		assertEquals(26, newCal.get(Calendar.MINUTE));
	}
	
	public void testDifferenceInMinutes() {
		// SUNDAY, December 26rd, 6:21PM 2010
		Calendar startTime = Calendar.getInstance();
		startTime.setTimeZone(TimeZone.getTimeZone("EST"));
		startTime.setTimeInMillis(1293405712000L);
		
		// SUNDAY, December 26rd, 6:21PM 2010
		Calendar endTime = Calendar.getInstance();
		endTime.setTimeZone(TimeZone.getTimeZone("EST"));
		endTime.setTimeInMillis(1293405778000L);
		
		assertEquals(1, DateUtil.differenceInMinutes(startTime.getTime(), endTime.getTime()));
	}
	
	public void testBeginningOfDay() {
		Calendar cal = Calendar.getInstance();
		
		Calendar beginningOfDay = DateUtil.beginningOfDay(cal);
		
		assertEquals(0, beginningOfDay.get(Calendar.HOUR));
		assertEquals(0, beginningOfDay.get(Calendar.MINUTE));
		assertEquals(0, beginningOfDay.get(Calendar.MILLISECOND));
		assertEquals(0, beginningOfDay.get(Calendar.SECOND));
	}
}
