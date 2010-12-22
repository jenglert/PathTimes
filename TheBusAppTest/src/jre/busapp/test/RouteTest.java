package jre.busapp.test;

import jre.bus.Route;
import junit.framework.TestCase;

public class RouteTest extends TestCase {

	public void testConvertNumberToMillisecondsSinceBeginningOfDay() {	
		assertEquals(36600000, Route.convertNumberToMillisecondsSinceBeginningOfDay(1010));
	}
}
