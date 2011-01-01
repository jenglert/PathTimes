package jre.busapp.test;

import jre.bus.Route;
import jre.bus.Station;
import junit.framework.TestCase;

public class RouteTest extends TestCase {

	public void testConvertNumberToMillisecondsSinceBeginningOfDay() {	
		assertEquals(36600000, Route.convertNumberToMillisecondsSinceBeginningOfDay(1010));
	}
	
	public void testTravelTimesExist() {
		for (Route route : Route.values()) {
			assertEquals(route.getStations().length - 1, route.getTravelTimes().length);
		}
	}
	
	public void testGetTravelTime() {
		assertEquals(25, Route.WEEKDAY_WASHINGTON_ST_TO_NJ.getTravelTime(Station.HOBOKENTERMINAL));
	}
}
