package jre.pathtimes;

/**
 * An enumeration of all the stations on the PATH system.
 * 
 * @author jimenglert
 */
public enum Station {
	
	ThirtyThird(1, "33rd"), 
	TwentyThird(2, "23rd"), 
	Fourteenth(3, "14th"), 
	Nineth(4, "9th"), 
	Christopher(5, "Christopher"), 
	ExchangePlace(6, "Exchange Place"), 
	Pavonia(7, "Pavonia Newport"), 
	Hoboken(8,"Hoboken"),
	Newark(9, "Newark"), 
	WTC(10, "WTC"), 
	GroveSt(11,"Grove St."), 
	JournalSquare(12, "Journal Sq."), 
	Harrison(13,"Harrison");

	private Integer id;

	/**
	 * The human friendly name of the station.
	 */
	private String name;
	
	/**
	 * Retrieves the station by station id.
	 */
	public static Station findById(Integer stationId) {
		for (Station station: values()) {
			if (stationId.equals(station.getId())) {
				return station;
			}
		}
		
		return null;
	}
	
	/**
	 *  Constructor for a station enum object
	 */
	private Station(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	/**
	 * @return Integer the id the station
	 */
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
