package jre.bus;

import java.util.Arrays;
import java.util.Comparator;

public enum Station {
	PABT(0, "Port Authority", null, null), 
	FOURTEENTHANDWILLOW(10, "Fourteenth and Willow", Street.WILLOW, Street.FOURTEENTH), 
	FOURTEENTHANDWASHINGTON(20, "Fourteenth and Washington", Street.WASHINGTON, Street.FOURTEENTH), 
	ELEVENTHANDWASHINGTON(30, "Eleventh and Washington", Street.WASHINGTON, Street.ELEVENTH), 
	ELEVENTHANDWILLOW(30, "Eleventh and Willow", Street.WILLOW, Street.ELEVENTH),
	ELEVENTHANDCLINTON(30, "Eleventh and Clinton", Street.CLINTON, Street.ELEVENTH), 
	FIFTHANDWASHINGTON(40, "Fifth and Washington", Street.WASHINGTON, Street.FIFTH), 
	FIFTHANDWILLOW(40, "Fifth and Willow", Street.WILLOW, Street.FIFTH), 
	FIFTHANDCLINTON(40, "Fifth and Clinton", Street.CLINTON, Street.FIFTH), 
	FIRSTANDCLINTON(50, "First and Clinton", Street.CLINTON, Street.FIRST), 
	FIRSTANDWILLOW(50, "First and Willow", Street.WILLOW, Street.FIRST),
	FIRSTANDWASHINGTON(60, "First and Washington", Street.WASHINGTON, Street.FIRST), 
	EIGHTENTHANDGROVE(80, "Eighteenth and Grove", Street.GROVE, Street.EIGHTEENTH), 
	HAMILTONPARK(90, "Hamilton Park", null, null),
	HOBOKENTERMINAL(70, "Hobooken Terminal", null, null);
	
	/**
	 * The order that the station is encountered.  We assume the order based on buses coming from NYC
	 */
	private Integer order;
	
	private String name;
	
	private Street northSouthCrossStreet;
	
	private Street eastWestCrossStreet;
	
	public static Station[] stationsInOrder(TrainDirection direction, Station[] stations) {
		final TrainDirection finalDirection = direction;
		
		Arrays.sort(stations, new Comparator<Station>()  {

			@Override
			public int compare(Station lhs, Station rhs) {
				
				if (lhs.getOrder() > rhs.getOrder()) {
					return finalDirection.isFromNYC() ? 1 : -1;
				}
				
				return finalDirection.isFromNYC() ? -1 : 1;
			}
			
		});
		
		return stations;
	}
	
	Station(Integer order, String name, Street nsCross, Street ewCross) {
		this.order = order;
		this.name = name;
		this.northSouthCrossStreet = nsCross;
		this.eastWestCrossStreet = ewCross;
	}
	
	public Street getNsCross() {
		return this.northSouthCrossStreet;
	}
	
	public Street getEwCross() {
		return this.eastWestCrossStreet;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Integer getOrder() {
		return this.order;
	}
}
