package jre.pathtimes.test;

import java.util.Calendar;
import java.util.List;

import jre.pathtimes.ScheduleUtil;
import jre.pathtimes.Station;
import jre.pathtimes.TrainLine;

public class TrainLineTest extends PathTimesUnitTest {

	/**
	 * Tests retrieving the time between stations
	 */
	public void testGetTimeBetweenStations() {
		assertEquals(new Integer(0),
				TrainLine.HOB_TTRD_WEEKDAY.getTimeBetweenStations(Station.Hoboken, Station.Hoboken));
		
		assertEquals(new Integer(4), 
				TrainLine.HOB_TTRD_WEEKDAY.getTimeBetweenStations(Station.Nineth, Station.ThirtyThird));
		
		assertEquals(new Integer(14), 
				TrainLine.HOB_TTRD_WEEKDAY.getTimeBetweenStations(Station.Hoboken, Station.ThirtyThird));
		
		assertEquals(new Integer(8), 
				TrainLine.HOB_TTRD_WEEKDAY.getTimeBetweenStations(Station.Hoboken, Station.Christopher));
		
		assertEquals(new Integer(2), 
				TrainLine.HOB_TTRD_WEEKDAY.getTimeBetweenStations(Station.TwentyThird, Station.ThirtyThird));
		
		assertEquals(null, 
				TrainLine.HOB_TTRD_WEEKDAY.getTimeBetweenStations(Station.ThirtyThird, Station.Nineth));
	}
	
	/**
	 * Tests finding the next appropriate arrival times
	 */
	public void testFindNextAppropriateArrivalTimes() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.HOUR_OF_DAY, 10);
		cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		List<Calendar> trainTimes = TrainLine.HOB_TTRD_WEEKDAY.findNextAppropriateArrivalTimes(Station.Christopher, cal, 5);
		
		assertEquals(5, trainTimes.size());
		
		assertClose(ScheduleUtil.convertDateStringToCalendar("10:30 AM"), trainTimes.get(0));
		assertClose(ScheduleUtil.convertDateStringToCalendar("10:40 AM"), trainTimes.get(1));
		assertClose(ScheduleUtil.convertDateStringToCalendar("10:50 AM"), trainTimes.get(2));
		assertClose(ScheduleUtil.convertDateStringToCalendar("11:00 AM"), trainTimes.get(3));
		assertClose(ScheduleUtil.convertDateStringToCalendar("11:10 AM"), trainTimes.get(4));
	}
	
	/**
	 * Tests finding the next appropriate arrival times, second case.
	 */
	public void testFindNextAppropriateArrivalTimes_2() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		List<Calendar> trainTimes = TrainLine.JSQ_TTRD_VIA_HOB_WEEKDAY.findNextAppropriateArrivalTimes(Station.Hoboken, cal, 3);
		
		assertEquals(3, trainTimes.size());
		
		assertClose(ScheduleUtil.convertDateStringToCalendar("12:26 AM"), trainTimes.get(0));
		assertClose(ScheduleUtil.convertDateStringToCalendar("12:56 AM"), trainTimes.get(1));
		assertClose(ScheduleUtil.convertDateStringToCalendar("01:26 AM"), trainTimes.get(2));
	}
	
	public void testFindNextAppropriateArrivalTimes_3() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 11);
		cal.set(Calendar.MINUTE, 55);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		
		List<Calendar> nextArrivalTimes = TrainLine.JSQ_TTRD_VIA_HOB_WEEKDAY.findNextAppropriateArrivalTimes(
				Station.Hoboken, cal, 5);
		assertClose(ScheduleUtil.convertDateStringToCalendar("11:26 PM"),
				nextArrivalTimes.get(0));
		assertClose(ScheduleUtil.convertDateStringToCalendar("11:56 PM"),
				nextArrivalTimes.get(1));
	}
}
