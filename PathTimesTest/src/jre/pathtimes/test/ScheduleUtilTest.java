package jre.pathtimes.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.util.Log;

import jre.pathtimes.ScheduleUtil;
import jre.pathtimes.Station;
import jre.pathtimes.TrainLine;

public class ScheduleUtilTest extends PathTimesUnitTest {
	
	/**
	 * Tests all the schedules for appropriateness
	 */
	public void testAllSchedules() {
		for (int i = 0; i < Station.values().length; i++) {
			for (int j = 0; j < Station.values().length; j++) {
				for (int dayOfWeek = 0; dayOfWeek < 7; dayOfWeek++) {
				
					if (dayOfWeek == Calendar.WEDNESDAY || dayOfWeek == Calendar.TUESDAY || dayOfWeek == Calendar.THURSDAY)
						continue;
				
					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.HOUR_OF_DAY, 0);
					calendar.set(Calendar.MINUTE, 0);
					calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
					
					for (int minuteIter = 0; minuteIter < 48; minuteIter++) {
						calendar.add(Calendar.MINUTE,	minuteIter * 30);
						
						try {
							System.out.println("Processing "
									+ Station.values()[i].getName()
									+ " to "
									+ Station.values()[j].getName()
									+ " at "
									+ new SimpleDateFormat("E hh mm a")
											.format(calendar.getTime()));
							
							List<Calendar> results = ScheduleUtil.getNextArrivalTimes(Station.values()[i], Station.values()[j], calendar, 6);
							
							String makesSense = resultsMakeSense(results);
							
							if (makesSense == null) {
								continue;
							}
							
							throw new RuntimeException(
									"Failure while processing "
											+ Station.values()[i].getName()
											+ " to "
											+ Station.values()[j].getName()
											+ " at " + new SimpleDateFormat("E hh mm a").format(calendar.getTime())
											+ " becuase:  " + makesSense);
							
							
						}
						catch (Exception e) {
							throw new RuntimeException(
									"Failure while processing "
											+ Station.values()[i].getName()
											+ " to "
											+ Station.values()[j].getName()
											+ " at "
											+ new SimpleDateFormat("E hh mm a").format(calendar
													.getTime()), e);
						}	
					}			
				}
			}
		}
	}
	
	private String resultsMakeSense(List<Calendar> results) {
		
		// This is probably OK because there is no train between these two stations.
		if (results == null) {
			return null;
		}
		
		Calendar previousResult = null;
		for (Calendar result : results) {
			if (previousResult == null) {
				continue;
			}
			
			if (result.getTimeInMillis() <= previousResult.getTimeInMillis()) {
				return "There was a path time that was equal to or less than the one before it.";
			}
			
			if (result.getTimeInMillis() - previousResult.getTimeInMillis() <  3 * 60 * 1000) { // 3 minutes
				return "There was a path time where the previous time was within 3 minutes of the one before it.";
			}
			
			if (result.getTimeInMillis() - previousResult.getTimeInMillis() >  31 * 60 * 1000) { // 3 minutes
				return "There was a path time where the previous time was greater than 31 minutes after the one before it.";
			}
		}
		
		
		return null;
	}
	
	/**
	 * Tests converting a date string into a calendar instance.
	 */
	public void testConvertDateStringToCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, 4);
		cal.set(Calendar.MINUTE, 4);
		cal.set(Calendar.AM_PM, Calendar.PM);
		assertClose(cal, ScheduleUtil.convertDateStringToCalendar("04:04 PM"));
		assertClose(cal, ScheduleUtil.convertDateStringToCalendar("4:04 PM"));	
		
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.HOUR, 4);
		cal2.set(Calendar.MINUTE, 4);
		cal2.set(Calendar.AM_PM, Calendar.AM);
		
		assertClose(cal2, ScheduleUtil.convertDateStringToCalendar("04:04 AM"));
		
		Calendar cal3 = Calendar.getInstance();
		cal3.set(Calendar.HOUR_OF_DAY, 0);
		cal3.set(Calendar.MINUTE, 4);
		
		assertClose(cal3, ScheduleUtil.convertDateStringToCalendar("12:04 AM"));
		assertEquals(Calendar.getInstance().get(Calendar.DAY_OF_WEEK), ScheduleUtil.convertDateStringToCalendar("12:04 AM").get(Calendar.DAY_OF_WEEK));
		
		Calendar cal4 = Calendar.getInstance();
		cal4.set(Calendar.HOUR_OF_DAY, 0);
		cal4.set(Calendar.MINUTE, 13);
		assertClose(cal4, ScheduleUtil.convertDateStringToCalendar("12:13 AM"));
		
		Calendar cal5 = Calendar.getInstance();
		cal5.set(Calendar.HOUR_OF_DAY, 12);
		cal5.set(Calendar.MINUTE, 26);
		assertClose(cal5, ScheduleUtil.convertDateStringToCalendar("12:26 PM"));
		
	}
	
	/**
	 * Tests the compare methods that compares two calendar instances.
	 */
	public void testCompare() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, 4);
		cal.set(Calendar.MINUTE, 4);
		cal.set(Calendar.AM_PM, Calendar.PM);
		
		assertEquals(0, ScheduleUtil.compare(ScheduleUtil.convertDateStringToCalendar("04:04 PM"), cal));
		assertTrue(ScheduleUtil.compare(ScheduleUtil.convertDateStringToCalendar("04:05 AM"), cal) < 0);
		assertTrue(ScheduleUtil.compare(ScheduleUtil.convertDateStringToCalendar("04:03 PM"), cal) < 0);
		assertTrue(ScheduleUtil.compare(ScheduleUtil.convertDateStringToCalendar("04:05 PM"), cal) > 0);
		assertTrue(ScheduleUtil.compare(ScheduleUtil.convertDateStringToCalendar("05:04 PM"), cal) > 0);
	}

	/**
	 * Tests finding appropriate train lines.
	 */
	public void testFindAppropriateTrainLine() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, 4);
		cal.set(Calendar.MINUTE, 4);
		cal.set(Calendar.AM_PM, Calendar.PM);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		List<TrainLine> correctResult = new ArrayList<TrainLine>();
		correctResult.add(TrainLine.HOB_TTRD_WEEKDAY);
		correctResult.add(TrainLine.JSQ_TTRD_VIA_HOB_WEEKDAY);
		
		assertEquals(correctResult, ScheduleUtil.findAppropriateTrainLines(
				Station.Hoboken, Station.ThirtyThird, cal));
	}
	
	/**
	 * Tests getting the next arrival time.
	 */
	public void testGetNextArrivalTimes() {
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, 4);
		cal.set(Calendar.MINUTE, 4);
		cal.set(Calendar.AM_PM, Calendar.PM);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		List<Calendar> nextArrivalTimes = ScheduleUtil.getNextArrivalTimes(
				Station.Hoboken, Station.ThirtyThird, cal, 5);
		
		assertNotNull(nextArrivalTimes);
		
		assertClose(ScheduleUtil.convertDateStringToCalendar("04:12 PM"), nextArrivalTimes.get(0));
	}
	
	/**
	 * Tests getting the next arrival times, second case.
	 */
	public void testGetNextArrivalTimes_2() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, 10);
		cal.set(Calendar.MINUTE, 43);
		cal.set(Calendar.AM_PM, Calendar.PM);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
	  
		
		List<Calendar> nextArrivalTimes = ScheduleUtil.getNextArrivalTimes(
				Station.Hoboken, Station.ThirtyThird, cal, 5);
		
		assertNotNull(nextArrivalTimes);
		assertEquals(5, nextArrivalTimes.size());
		
		assertClose(ScheduleUtil.convertDateStringToCalendar("10:52 PM"),
				nextArrivalTimes.get(0));
		assertClose(ScheduleUtil.convertDateStringToCalendar("11:07 PM"),
				nextArrivalTimes.get(1));
		assertClose(ScheduleUtil.convertDateStringToCalendar("11:28 PM"),
				nextArrivalTimes.get(2));
		assertClose(ScheduleUtil.convertDateStringToCalendar("11:43 PM"),
				nextArrivalTimes.get(3));
		assertClose(ScheduleUtil.convertDateStringToCalendar("11:58 PM"),
				nextArrivalTimes.get(4));
	}
	
	/**
	 * Tests getting arrival times, 3rd case.
	 */
	public void testGetNextArrivalTimes_3() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, 10);
		cal.set(Calendar.MINUTE, 43);
		cal.set(Calendar.AM_PM, Calendar.PM);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);

		List<Calendar> nextArrivalTimes = ScheduleUtil.getNextArrivalTimes(
				Station.ThirtyThird, Station.Hoboken, cal, 5);

		assertNotNull(nextArrivalTimes);
		assertEquals(5, nextArrivalTimes.size());
		
		assertClose(ScheduleUtil.convertDateStringToCalendar("10:57 PM"), nextArrivalTimes.get(0));
		assertClose(ScheduleUtil.convertDateStringToCalendar("11:12 PM"), nextArrivalTimes.get(1));
		assertClose(ScheduleUtil.convertDateStringToCalendar("11:27 PM"), nextArrivalTimes.get(2));
		assertClose(ScheduleUtil.convertDateStringToCalendar("11:45 PM"), nextArrivalTimes.get(3));
		assertClose(ScheduleUtil.convertDateStringToCalendar("12:00 AM"), nextArrivalTimes.get(4));
	}
	
	public void testGetNextArrivalTime_4() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 43);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

		List<Calendar> nextArrivalTimes = ScheduleUtil.getNextArrivalTimes(
				Station.Newark, Station.WTC, cal, 5);

		assertNotNull(nextArrivalTimes);
		assertEquals(5, nextArrivalTimes.size());
		
		assertClose(ScheduleUtil.convertDateStringToCalendar("12:00 AM"),
				nextArrivalTimes.get(0));
		assertClose(ScheduleUtil.convertDateStringToCalendar("12:30 AM"),
				nextArrivalTimes.get(1));
		assertClose(ScheduleUtil.convertDateStringToCalendar("01:05 AM"),
				nextArrivalTimes.get(2));
		assertClose(ScheduleUtil.convertDateStringToCalendar("01:40 AM"),
				nextArrivalTimes.get(3));
		assertClose(ScheduleUtil.convertDateStringToCalendar("02:15 AM"),
				nextArrivalTimes.get(4));
	}
	
	public void testGetNextArrivalTime_NON_CONNECTED() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 43);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		
		List<Calendar> results = ScheduleUtil.getNextArrivalTimes(Station.Hoboken, Station.Newark, cal, 5);
		
		assertEquals(0, results.size());
	}
	
	public void testGetNextArrivalTime_6() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 11);
		cal.set(Calendar.MINUTE, 55);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

		List<Calendar> nextArrivalTimes = ScheduleUtil.getNextArrivalTimes(
				Station.Hoboken, Station.ThirtyThird, cal, 5);
		
		List<TrainLine> trainLine = ScheduleUtil.findAppropriateTrainLines(
				Station.Hoboken, Station.ThirtyThird, cal);
		
		assertEquals(2, trainLine.size());

		assertNotNull(nextArrivalTimes);
		assertEquals(5, nextArrivalTimes.size());
		
		assertClose(ScheduleUtil.convertDateStringToCalendar("12:02 PM"),
				nextArrivalTimes.get(0));
		assertClose(ScheduleUtil.convertDateStringToCalendar("12:12 PM"),
				nextArrivalTimes.get(1));
		assertClose(ScheduleUtil.convertDateStringToCalendar("12:22 PM"),
				nextArrivalTimes.get(2));
		assertClose(ScheduleUtil.convertDateStringToCalendar("12:32 PM"),
				nextArrivalTimes.get(3));
		assertClose(ScheduleUtil.convertDateStringToCalendar("12:42 PM"),
				nextArrivalTimes.get(4));
	}
	
	public void testGetNextArrivalTime_7() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

		List<Calendar> nextArrivalTimes = ScheduleUtil.getNextArrivalTimes(
				Station.Nineth, Station.TwentyThird, cal, 5);

		assertNotNull(nextArrivalTimes);
		assertEquals(5, nextArrivalTimes.size());
		
		assertClose(ScheduleUtil.convertDateStringToCalendar("11:38 PM"),
				nextArrivalTimes.get(0));
		assertClose(ScheduleUtil.convertDateStringToCalendar("11:53 PM"),
				nextArrivalTimes.get(1));
		assertClose(ScheduleUtil.convertDateStringToCalendar("12:08 AM"),
				nextArrivalTimes.get(2));
		assertClose(ScheduleUtil.convertDateStringToCalendar("12:38 AM"),
				nextArrivalTimes.get(3));
		assertClose(ScheduleUtil.convertDateStringToCalendar("01:08 AM"),
				nextArrivalTimes.get(4));
	}
	
	public void testGetNextArrivalTime_8() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 2);
		cal.set(Calendar.MINUTE, 55);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);

		List<Calendar> nextArrivalTimes = ScheduleUtil.getNextArrivalTimes(
				Station.Christopher, Station.Hoboken, cal, 5);

		assertNotNull(nextArrivalTimes);
		assertEquals(5, nextArrivalTimes.size());
		
		assertClose(ScheduleUtil.convertDateStringToCalendar("03:11 AM"),
				nextArrivalTimes.get(0));
		assertClose(ScheduleUtil.convertDateStringToCalendar("03:46 AM"),
				nextArrivalTimes.get(1));
		assertClose(ScheduleUtil.convertDateStringToCalendar("04:21 AM"),
				nextArrivalTimes.get(2));
		assertClose(ScheduleUtil.convertDateStringToCalendar("04:56 AM"),
				nextArrivalTimes.get(3));
		assertClose(ScheduleUtil.convertDateStringToCalendar("05:31 AM"),
				nextArrivalTimes.get(4));
	}
	
	public void testGetNextArrivalTime_9() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);

		List<Calendar> nextArrivalTimes = ScheduleUtil.getNextArrivalTimes(
				Station.ThirtyThird, Station.Hoboken, cal, 5);

		assertNotNull(nextArrivalTimes);
		assertEquals(5, nextArrivalTimes.size());
		
		assertClose(ScheduleUtil.convertDateStringToCalendar("11:45 PM"),
				nextArrivalTimes.get(0));
		assertClose(ScheduleUtil.convertDateStringToCalendar("12:00 AM"),
				nextArrivalTimes.get(1));
		assertClose(ScheduleUtil.convertDateStringToCalendar("12:15 AM"),
				nextArrivalTimes.get(2));
		assertClose(ScheduleUtil.convertDateStringToCalendar("12:30 AM"),
				nextArrivalTimes.get(3));
		assertClose(ScheduleUtil.convertDateStringToCalendar("12:45 AM"),
				nextArrivalTimes.get(4));
	}
	
	public void testGetNextArrivalTime_10() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);

		List<Calendar> nextArrivalTimes = ScheduleUtil.getNextArrivalTimes(
				Station.ThirtyThird, Station.ExchangePlace, cal, 5);

		List<TrainLine> findAppropriateTrainLines = ScheduleUtil.findAppropriateTrainLines(Station.ThirtyThird, Station.ExchangePlace, cal);
		Log.e("jim", new Integer(findAppropriateTrainLines.size()).toString());
		
		assertEquals(0, nextArrivalTimes.size());
	}
	
	public void testGetNextArrivalTime_11() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 22);
		cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

		List<Calendar> nextArrivalTimes = ScheduleUtil.getNextArrivalTimes(
				Station.ExchangePlace, Station.Pavonia, cal, 5);

		assertNotNull(nextArrivalTimes);
		assertEquals(4, nextArrivalTimes.size());
		
		assertClose(ScheduleUtil.convertDateStringToCalendar("10:30 PM"),
				nextArrivalTimes.get(0));
		assertClose(ScheduleUtil.convertDateStringToCalendar("10:45 PM"),
				nextArrivalTimes.get(1));
		assertClose(ScheduleUtil.convertDateStringToCalendar("11:00 PM"),
				nextArrivalTimes.get(2));
		assertClose(ScheduleUtil.convertDateStringToCalendar("11:15 PM"),
				nextArrivalTimes.get(3));
	}
}
