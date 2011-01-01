package jre.bus;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
	
	/**
	 * Calculates the beginning of the specified day.
	 */
	public static Calendar beginningOfDay(Calendar cal) {
		Calendar newCal = Calendar.getInstance();
		newCal.setTimeZone(TimeZone.getTimeZone("EST"));
		newCal.setTime(cal.getTime());
		newCal.set(Calendar.HOUR_OF_DAY, 0);
		newCal.set(Calendar.MINUTE, 0);
		newCal.set(Calendar.SECOND, 0);
		newCal.set(Calendar.MILLISECOND, 0);
		
		return newCal;
	}
	
	public static Date addMinutes(long originalTime, int minutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(originalTime);
		
		cal.add(Calendar.MINUTE, minutes);
		
		return cal.getTime();
	}
	
	public static int differenceInMinutes(Date firstDate, Date laterDate) {
		long difference = laterDate.getTime() - firstDate.getTime();
		
		return (int) ((difference / 1000) / 60);
	}
}
