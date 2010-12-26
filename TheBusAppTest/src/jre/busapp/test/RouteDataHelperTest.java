package jre.busapp.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import jre.bus.RouteDataHelper;
import jre.bus.RouteDataLoader;
import jre.bus.Station;
import jre.bus.TrainDirection;
import android.test.AndroidTestCase;
import android.util.Log;

public class RouteDataHelperTest extends AndroidTestCase {
	
	public void testNextFiveBuses() {
		// Thursday, December 23rd, 8AM 2010
		Calendar startTime = Calendar.getInstance();
		startTime.setTimeInMillis(1293109200000L);
		startTime.setTimeZone(TimeZone.getTimeZone("EST"));
		
		// Sanity
		assertEquals(8, startTime.get(Calendar.HOUR_OF_DAY));
		assertEquals(2010, startTime.get(Calendar.YEAR));
		assertEquals(Calendar.THURSDAY, startTime.get(Calendar.DAY_OF_WEEK));
		
		RouteDataLoader loader = new RouteDataLoader(getContext());
		loader.loadUpcomingDays(startTime.getTime(), 1);
		
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
		
		List<Long> nextFiveBuses = new ArrayList<Long>(
				routeDataHelper.nextFiveBuses(startTime.getTime(),
						Station.HOBOKENTERMINAL, TrainDirection.TO_NYC));
	
		assertEquals(5, nextFiveBuses.size());
		Calendar one = Calendar.getInstance();
		one.setTimeInMillis(nextFiveBuses.get(0));
		one.setTimeZone(TimeZone.getTimeZone("EST"));
		assertEquals(8, one.get(Calendar.HOUR_OF_DAY));
		assertEquals(1, one.get(Calendar.MINUTE));
		
		Calendar two = Calendar.getInstance();
		two.setTimeInMillis(nextFiveBuses.get(1));
		two.setTimeZone(TimeZone.getTimeZone("EST"));
		assertEquals(8, two.get(Calendar.HOUR_OF_DAY));
		assertEquals(3, two.get(Calendar.MINUTE));
		
		Calendar three = Calendar.getInstance();
		three.setTimeInMillis(nextFiveBuses.get(2));
		three.setTimeZone(TimeZone.getTimeZone("EST"));
		assertEquals(8, three.get(Calendar.HOUR_OF_DAY));
		assertEquals(5, three.get(Calendar.MINUTE));
	}

}
