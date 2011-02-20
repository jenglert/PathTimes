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
		
		// 2011 holidays
		if(travelDay.get(Calendar.YEAR) == 2011) {
			if (travelDay.get(Calendar.MONTH) == Calendar.JANUARY && travelDay.get(Calendar.DAY_OF_MONTH) == 1) {
				return SundayHoliday;
			}
			
			if (travelDay.get(Calendar.MONTH) == Calendar.FEBRUARY && travelDay.get(Calendar.DAY_OF_MONTH) == 21) {
				return SundayHoliday;
			}
			
			if (travelDay.get(Calendar.MONTH) == Calendar.MAY && travelDay.get(Calendar.DAY_OF_MONTH) == 30) {
				return SundayHoliday;
			}

			if (travelDay.get(Calendar.MONTH) == Calendar.SEPTEMBER && travelDay.get(Calendar.DAY_OF_MONTH) == 5) {
				return SundayHoliday;
			}
			
			if (travelDay.get(Calendar.MONTH) == Calendar.NOVEMBER && travelDay.get(Calendar.DAY_OF_MONTH) == 24) {
				return SundayHoliday;
			}
			
			if (travelDay.get(Calendar.MONTH) == Calendar.DECEMBER && travelDay.get(Calendar.DAY_OF_MONTH) == 25) {
				return SundayHoliday;
			}
		}
		
		if (Calendar.SATURDAY == travelDay.get(Calendar.DAY_OF_WEEK)) {
			return Saturday;
		}
		else if (Calendar.SUNDAY == travelDay.get(Calendar.DAY_OF_WEEK)) {
			return SundayHoliday;
		}
		
		return Weekday;
	}
	
}
