package jre.pathtimes;

import java.util.Calendar;

public enum ScheduleDay {
	Weekday, Saturday, SundayHoliday;	
	
	
	/**
	 * Retrieves the appropriate ScheduleDate based on the travel day.
	 * 
	 * @param travelDay the day of travel.
	 * @return ScheduleDate the schedule date
	 */
	public static ScheduleDay getByDate(Calendar travelDay) {
		
		if (Calendar.SATURDAY == travelDay.get(Calendar.DAY_OF_WEEK)) {
			return Saturday;
		}
		else if (Calendar.SUNDAY == travelDay.get(Calendar.DAY_OF_WEEK)) {
			return SundayHoliday;
		}
		
		return Weekday;
	}
	
}
