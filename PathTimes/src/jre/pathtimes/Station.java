package jre.pathtimes;

/**
 * An enumeration of all the stations on the PATH system.
 * 
 * @author jimenglert
 */
public enum Station {
	
	ThirtyThird(1, "33rd", 40.74904512643806, -73.98837089538574, "33rd and Broadway"), 
	TwentyThird(2, "23rd", 40.74273757130469, -73.99283409118652, "23rd and 6th ave"), 
	Fourteenth(3, "14th", 40.73685214795608, -73.99699687957764, "14th and 6th ave"), 
	Nineth(4, "9th", 40.7341856521751, -73.9989709854126, "9th and 6th ave"), 
	Christopher(5, "Christopher", 40.732949922769336, -74.00712490081787, "Christopher and Greenwich"), 
	ExchangePlace(6, "Exchange Place", 40.716070163321575, -74.0330457687378, "Christopher Columbus Dr and the Hudson River"), 
	Pavonia(7, "Pavonia Newport", 40.72677093147629, -74.03476238250732, "Washington Blvd and Town Square Pl"), 
	Hoboken(8,"Hoboken", 40.73603920325004, -74.02931213378906, "Hudson and River"),
	Newark(9, "Newark", 40.73460839643972, -74.16380882263184, "Market and Raymond Plaza"), 
	WTC(10, "WTC", 40.71154865315634, -74.01064395904541, "Vessey and Church"), 
	GroveSt(11,"Grove St.", 40.71942043681212, -74.04253005981445, "Chistopher Columbus and Grove St"), 
	JournalSquare(12, "Journal Sq.", 40.73216945026674, -74.06312942504883, "Pavonia Ave and John F. Kennedy Blvd"), 
	Harrison(13,"Harrison", 40.73851052435288, -74.15586948394775, "Frank E. Rodgers and Somerset");

	private Integer id;

	/**
	 * The human friendly name of the station.
	 */
	private String name;
	
	/**
	 * The latitude of the path station
	 */
	private Double latitude;
	
	/**
	 * The longitude of the path station.
	 */
	private Double longitude;
	
	/**
	 * The address of the train station.
	 */
	private String address;
	
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
	private Station(Integer id, String name, Double latitude, Double longitude, String address) {
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
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

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}
	
	public String getAddress() {
		return address;
	}
}
