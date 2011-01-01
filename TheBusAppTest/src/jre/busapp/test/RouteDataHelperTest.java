package jre.busapp.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import jre.bus.Route;
import jre.bus.RouteDataHelper;
import jre.bus.RouteDataLoader;
import jre.bus.Snake;
import jre.bus.Station;
import jre.bus.TrainDirection;
import android.test.AndroidTestCase;
import android.util.Log;

public class RouteDataHelperTest extends AndroidTestCase {
	
	public void testNextFiveBuses_THURSDAY() {
		// Thursday, December 23rd, 8AM 2010
		Calendar startTime = Calendar.getInstance();
		startTime.setTimeInMillis(1293109200000L);
		startTime.setTimeZone(TimeZone.getTimeZone("EST"));
		
		// Sanity
		assertEquals(8, startTime.get(Calendar.HOUR_OF_DAY));
		assertEquals(2010, startTime.get(Calendar.YEAR));
		assertEquals(Calendar.THURSDAY, startTime.get(Calendar.DAY_OF_WEEK));
		
		RouteDataLoader loader = new RouteDataLoader(getContext());
		loader.loadUpcomingDays(startTime, 48);
		
		RouteDataHelper routeDataHelper = new RouteDataHelper(getContext());
		
		assertTrue(routeDataHelper.selectAll().size() > 10);
		for (Map<String, Object> row : routeDataHelper.selectAll()) {
			Log.e("msg",
					"Route: "
							+ row.get("route")
							+ " start_time: "
							+ new SimpleDateFormat(
									"yyyy.MM.dd G 'at' HH:mm:ss z")
									.format(new Date((Long) row
											.get("start_time"))));
		}
		
		List<Snake<Long, String>> nextFiveBuses = new ArrayList<Snake<Long, String>>(
				routeDataHelper.nextFiveBuses(startTime.getTime(),
						Station.HOBOKENTERMINAL, TrainDirection.TO_NYC));
		
		Log.e("found", nextFiveBuses.get(0) + " - "  + nextFiveBuses.get(1) + " - " + nextFiveBuses.get(2));
	
		assertEquals(5, nextFiveBuses.size());
		Calendar one = Calendar.getInstance();
		one.setTimeInMillis(nextFiveBuses.get(0).getFirst());
		one.setTimeZone(TimeZone.getTimeZone("EST"));
		assertEquals(8, one.get(Calendar.HOUR_OF_DAY));
		assertEquals(1, one.get(Calendar.MINUTE));
		
		Calendar two = Calendar.getInstance();
		two.setTimeInMillis(nextFiveBuses.get(1).getFirst());
		two.setTimeZone(TimeZone.getTimeZone("EST"));
		assertEquals(8, two.get(Calendar.HOUR_OF_DAY));
		assertEquals(3, two.get(Calendar.MINUTE));
		
		Calendar three = Calendar.getInstance();
		three.setTimeInMillis(nextFiveBuses.get(2).getFirst());
		three.setTimeZone(TimeZone.getTimeZone("EST"));
		assertEquals(8, three.get(Calendar.HOUR_OF_DAY));
		assertEquals(5, three.get(Calendar.MINUTE));
	}
	
	public void testNextFiveBuses_SUNDAY() {
		// SUNDAY, December 26rd, 6:21PM 2010
		Calendar startTime = Calendar.getInstance();
		startTime.setTimeZone(TimeZone.getTimeZone("EST"));
		startTime.setTimeInMillis(1293405712000L);
		
		// Sanity
		assertEquals(18, startTime.get(Calendar.HOUR_OF_DAY));
		assertEquals(21, startTime.get(Calendar.MINUTE));
		assertEquals(2010, startTime.get(Calendar.YEAR));
		
		assertEquals(Calendar.SUNDAY, startTime.get(Calendar.DAY_OF_WEEK));
		
		RouteDataLoader loader = new RouteDataLoader(getContext());
		loader.loadUpcomingDays(startTime, 48);
		
		RouteDataHelper routeDataHelper = new RouteDataHelper(getContext());
		
		assertTrue(routeDataHelper.selectAll().size() > 10);
		for (Map<String, Object> row : routeDataHelper.selectAll()) {
			Log.e("msg",
					"Route: "
							+ row.get("route")
							+ " start_time: "
							+ new SimpleDateFormat(
									"yyyy.MM.dd G 'at' HH:mm:ss z")
									.format(new Date((Long) row
											.get("start_time"))) + " time: " + row.get("start_time"));
		}
		
		List<Snake<Long, String>> nextFiveBuses = new ArrayList<Snake<Long, String>>(
				routeDataHelper.nextFiveBuses(startTime.getTime(),
						Station.HOBOKENTERMINAL, TrainDirection.TO_NYC));
	
		assertEquals(5, nextFiveBuses.size());
		Calendar one = Calendar.getInstance();
		one.setTimeInMillis(nextFiveBuses.get(0).getFirst());
		one.setTimeZone(TimeZone.getTimeZone("EST"));
		assertEquals(18, one.get(Calendar.HOUR_OF_DAY));
		assertEquals(30, one.get(Calendar.MINUTE));
		
		Calendar two = Calendar.getInstance();
		two.setTimeInMillis(nextFiveBuses.get(1).getFirst());
		two.setTimeZone(TimeZone.getTimeZone("EST"));
		assertEquals(19, two.get(Calendar.HOUR_OF_DAY));
		assertEquals(10, two.get(Calendar.MINUTE));
		
		Calendar three = Calendar.getInstance();
		three.setTimeInMillis(nextFiveBuses.get(2).getFirst());
		three.setTimeZone(TimeZone.getTimeZone("EST"));
		assertEquals(19, three.get(Calendar.HOUR_OF_DAY));
		assertEquals(30, three.get(Calendar.MINUTE));
	}

	public void testHasAvailableTime() {
		// SUNDAY, December 26rd, 6:21PM 2010
		Calendar startTime = Calendar.getInstance();
		startTime.setTimeZone(TimeZone.getTimeZone("EST"));
		startTime.setTimeInMillis(1293405712000L);
		
		// Sanity
		assertEquals(18, startTime.get(Calendar.HOUR_OF_DAY));
		assertEquals(21, startTime.get(Calendar.MINUTE));
		assertEquals(2010, startTime.get(Calendar.YEAR));
		
		assertEquals(Calendar.SUNDAY, startTime.get(Calendar.DAY_OF_WEEK));
		
		RouteDataLoader loader = new RouteDataLoader(getContext());
		loader.loadUpcomingDays(startTime, 48);
		
		RouteDataHelper routeDataHelper = new RouteDataHelper(getContext());
		
		assertTrue(routeDataHelper.selectAll().size() > 10);
		for (Map<String, Object> row : routeDataHelper.selectAll()) {
			Log.e("msg",
					"Route: "
							+ row.get("route")
							+ " start_time: "
							+ new SimpleDateFormat(
									"yyyy.MM.dd G 'at' HH:mm:ss z")
									.format(new Date((Long) row
											.get("start_time"))) + " time: " + row.get("start_time"));
		}
		
		assertTrue(routeDataHelper.hasAvailableTime(startTime.getTime(), Route.SUNDAY_WASHINGTON_STREET_TO_NYC));
		assertFalse(routeDataHelper.hasAvailableTime(startTime.getTime(), Route.SATURDAY_WASHINGTON_STREET_TO_NYC));
	}
	
	public void testGetMaxStartTime() {
		// SUNDAY, December 26rd, 6:21PM 2010
		Calendar startTime = Calendar.getInstance();
		startTime.setTimeZone(TimeZone.getTimeZone("EST"));
		startTime.setTimeInMillis(1293405712000L);
		
		// Sanity
		assertEquals(18, startTime.get(Calendar.HOUR_OF_DAY));
		assertEquals(21, startTime.get(Calendar.MINUTE));
		assertEquals(2010, startTime.get(Calendar.YEAR));
		
		assertEquals(Calendar.SUNDAY, startTime.get(Calendar.DAY_OF_WEEK));
		
		RouteDataLoader loader = new RouteDataLoader(getContext());
		loader.loadUpcomingDays(startTime, 48);
		
		assertEquals(1293578400000L, new RouteDataHelper(getContext()).getMaxStartTime());
	}
	
}
