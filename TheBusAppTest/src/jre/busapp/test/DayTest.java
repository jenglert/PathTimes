package jre.busapp.test;

import java.util.Calendar;

import jre.bus.Day;
import junit.framework.TestCase;

public class DayTest extends TestCase {

	public void testIsSaturday() {
		assertTrue(Day.SATURDAY.isSaturday());
		assertFalse(Day.SUNDAY.isSaturday());
		assertFalse(Day.WEEKDAY.isSaturday());
	}
	
	public void testIsSunday() {
		assertFalse(Day.SATURDAY.isSunday());
		assertTrue(Day.SUNDAY.isSunday());
		assertFalse(Day.WEEKDAY.isSunday());
	}
	
	public void testIsWeekday() {
		assertFalse(Day.SATURDAY.isWeekday());
		assertFalse(Day.SUNDAY.isWeekday());
		assertTrue(Day.WEEKDAY.isWeekday());
	}
	
	public void testMatches() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		assertTrue(Day.WEEKDAY.matches(cal));
		assertFalse(Day.SATURDAY.matches(cal));
		assertFalse(Day.SUNDAY.matches(cal));
		
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		assertFalse(Day.WEEKDAY.matches(cal));
		assertTrue(Day.SATURDAY.matches(cal));
		assertFalse(Day.SUNDAY.matches(cal));
		
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		assertFalse(Day.WEEKDAY.matches(cal));
		assertFalse(Day.SATURDAY.matches(cal));
		assertTrue(Day.SUNDAY.matches(cal));
	}
	
}
