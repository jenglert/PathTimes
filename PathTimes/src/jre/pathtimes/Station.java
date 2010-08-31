package jre.pathtimes;

/**
 * An enumeration of all the stations on the PATH system.
 * 
 * @author jimenglert
 */
public enum Station {
	
	ThirtyThird(1, "33rd", 40.74911, -73.98824, "33rd and Broadway"), 
	TwentyThird(2, "23rd", 40.74289, -73.99287, "23rd and 6th ave"), 
	Fourteenth(3, "14th", 40.73739, -73.99686, "14th and 6th ave"), 
	Nineth(4, "9th", 40.73421, -73.99894, "9th and 6th ave"), 
	Christopher(5, "Christopher", 40.73360, -74.00682, "Christopher and Greenwich"), 
	ExchangePlace(6, "Exchange Place", 40.7162, -74.03298, "Christopher Columbus Dr and the Hudson River"), 
	Pavonia(7, "Pavonia Newport", 40.72668, -74.03476, "Washington Blvd and Town Square Pl"), 
	Hoboken(8,"Hoboken", 40.7349, -74.0278, "Hudson and River"),
	Newark(9, "Newark", 40.73472, -74.16417, "Market and Raymond Plaza"), 
	WTC(10, "WTC", 40.71179, -74.01053, "Vessey and Church"), 
	GroveSt(11,"Grove St.", 40.71967, -74.04312, "Chistopher Columbus and Grove St"), 
	JournalSquare(12, "Journal Sq.", 40.73214, -74.06311, "Pavonia Ave and John F. Kennedy Blvd"), 
	Harrison(13,"Harrison", 40.71179, -74.01053, "Frank E. Rodgers and Somerset");

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
