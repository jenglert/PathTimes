package jre.busapp.test;

import jre.bus.Route;
import jre.bus.Station;
import jre.bus.TrainDirection;
import junit.framework.TestCase;

public class StationTest extends TestCase {

	public void testStationsInOrderToNycClinton() {
		
		Station[] stationsSorted = Station.stationsInOrder(
				TrainDirection.TO_NYC,
				Route.WEEKDAY_CLINTON_STREET_TO_NYC.getStations());

		assertEquals(6, stationsSorted.length);
		
		int i = 0;
		for (Station station: new Station[] { Station.HOBOKENTERMINAL,
				Station.FIRSTANDCLINTON, Station.FIFTHANDCLINTON,
				Station.ELEVENTHANDCLINTON, Station.FOURTEENTHANDWILLOW,
				Station.PABT }) {
			assertEquals(station, stationsSorted[i]);
			i++;
		}
	}
	
	public void testStationsInOrderFromNYCWillow() {
		Station[] stationsSorted = Station.stationsInOrder(
				TrainDirection.FROM_NYC,
				Route.WEEKDAY_WILLOW_AVE_TO_NJ.getStations());

		assertEquals(6, stationsSorted.length);
		
		int i = 0;
		for (Station station : new Station[] { Station.PABT,
				Station.FOURTEENTHANDWILLOW, Station.ELEVENTHANDWILLOW,
				Station.FIFTHANDWILLOW, Station.FIRSTANDWILLOW,
				Station.HOBOKENTERMINAL }) {
			assertEquals(station, stationsSorted[i]);
			i++;
		}
	}
	
}
