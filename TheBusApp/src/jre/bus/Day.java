package jre.bus;

import java.util.Calendar;

public enum Day {
	WEEKDAY, SATURDAY, SUNDAY;
	
	private Day() { }

	public boolean matches(Calendar calendar) {
		if (isSaturday() && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			return true;
		}  
		
		if (isSunday() && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return true;
		}
		
		if (isWeekday()
				&& (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && 
					  calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Tests whether the day is Saturday.
	 */
	public boolean isSaturday() {
		if (this.equals(Day.SATURDAY)) {
			return true;
		}
		
		return false;
	}

	/**
	 * Tests whether the day is a Sunday
	 */
	public boolean isSunday() {
		if (this.equals(Day.SUNDAY)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Tests whether the day is the weekday.
	 */
	public boolean isWeekday() {
		if (this.equals(Day.WEEKDAY)) {
			return true;
		}
		
		return false;
	}
}
