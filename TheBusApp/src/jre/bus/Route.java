package jre.bus;

public enum Route {
	
	/*****************************
	 * NJ - NYC *
	 *****************************/

	// Washington Street
	WEEKDAY_WASHINGTON_STREET_TO_NYC ("WEEKDAY_WASHINGTON_STREET_TO_NYC", 
			new Integer[] { 00, 30, 100,
			130, 510, 540, 600, 608, 616, 622, 632, 640, 650, 655, 700, 706,
			712, 717, 723, 729, 732, 736, 738, 741, 745, 748, 750, 752, 754,
			757, 759, 801, 803, 805, 808, 810, 813, 816, 819, 822, 825, 829,
			835, 840, 844, 849, 854, 859, 905, 911, 917, 923, 930, 936, 942,
			948, 954, 1000, 1015, 1030, 1050, 1110, 1130, 1150, 1210, 1230,
			1250, 1310, 1330, 1350, 1410, 1430, 1445, 1500, 1510, 1520, 1530,
			1540, 1550, 1600, 1606, 1612, 1618, 1624, 1630, 1636, 1642, 1648,
			1654, 1700, 1708, 1716, 1718, 1724, 1730, 1736, 1742, 1748, 1754,
			1800, 1808, 1816, 1824, 1830, 1836, 1842, 1848, 1854, 1900, 1910,
			1920, 1930, 1940, 1942, 1950, 2000, 2010, 2020, 2030, 2040, 2050,
			2100, 2110, 2120, 2130, 2140, 2150, 2200, 2210, 2220, 2230, 2245,
			2300, 2315, 2330 },
			new Station[] { Station.HOBOKENTERMINAL, Station.FIRSTANDWASHINGTON, 
			Station.FIFTHANDWASHINGTON, Station.ELEVENTHANDWASHINGTON, 
			Station.FOURTEENTHANDWASHINGTON, Station.FOURTEENTHANDWILLOW, 
			Station.PABT},
			TrainDirection.TO_NYC, Day.WEEKDAY),

	// Clinton Street to NYC
	WEEKDAY_CLINTON_STREET_TO_NYC("WEEKDAY_CLINTON_STREET_TO_NYC", new Integer[] { 635, 640, 655,
			705, 709, 717, 720, 726, 731, 737, 739, 743, 745, 747, 749, 751,
			753, 757, 759, 801, 803, 807, 809, 813, 815, 822, 825, 829, 836,
			848, 855, 912, 920 },
			new Station[] {
			Station.HOBOKENTERMINAL, Station.FIRSTANDCLINTON, Station.FIFTHANDCLINTON, 
			Station.ELEVENTHANDCLINTON, Station.FOURTEENTHANDWILLOW, Station.PABT
		}, TrainDirection.TO_NYC, Day.WEEKDAY),

	// Clinon Street from JC to NYC
	WEEKDAY_CLINTON_STREET_FROM_JC_TO_NYC("WEEKDAY_CLINTON_STREET_FROM_JC_TO_NYC", new Integer[] { 643, 653,
			706, 712, 725, 731, 745, 755, 801, 808, 823, 832, 854 },
			new Station[] {
			Station.HAMILTONPARK, Station.EIGTHENTHANDGROVE, Station.FIRSTANDCLINTON, Station.FIFTHANDCLINTON,
			Station.ELEVENTHANDCLINTON, Station.FOURTEENTHANDWILLOW, Station.PABT
		}, TrainDirection.TO_NYC, Day.WEEKDAY),

	// Weekday Clinton street local
	WEEKDAY_CLINTON_STREET_LOCAL("WEEKDAY_CLINTON_STREET_LOCAL", new Integer[] { 1600, 1620, 1640,
			1700, 1715, 1725, 1735, 1745, 1800, 1820, 1840, 1900, 1920, 1940,
			2000, 2020 },
			new Station[] {
			Station.HOBOKENTERMINAL, Station.FIRSTANDCLINTON, Station.FIFTHANDCLINTON, Station.ELEVENTHANDCLINTON
		}, TrainDirection.TO_NYC, Day.WEEKDAY),

	// Saturday Washington street to NYC
	SATURDAY_WASHINGTON_STREET_TO_NYC("SATURDAY_WASHINGTON_STREET_TO_NYC",
			new Integer[] { 00, 25, 55,
			130, 600, 630, 650, 710, 730, 750, 810, 830, 850, 910, 930, 950,
			1010, 1030, 1050, 1110, 1130, 1150, 1210, 1230, 1250, 1310, 1330,
			1350, 1410, 1430, 1450, 1510, 1530, 1550, 1610, 1630, 1650, 1710,
			1730, 1750, 1810, 1830, 1850, 1910, 1930, 1950, 2010, 2030, 2050,
			2110, 2130, 2150, 2210, 2230, 2250, 2310, 2330 },
			new Station[] {
			Station.HOBOKENTERMINAL, Station.FIRSTANDWASHINGTON, 
			Station.FIFTHANDWASHINGTON, Station.ELEVENTHANDWASHINGTON, 
			Station.FOURTEENTHANDWASHINGTON, Station.FOURTEENTHANDWILLOW, 
			Station.PABT
		}, TrainDirection.FROM_NYC, Day.SATURDAY),

	// Sunday Washington Street to NYC
	SUNDAY_WASHINGTON_STREET_TO_NYC("SUNDAY_WASHINGTON_STREET_TO_NYC",
			new Integer[] { 30, 130, 700,
			800, 900, 930, 1000, 1030, 1050, 1110, 1130, 1150, 1210, 1230,
			1250, 1310, 1330, 1350, 1410, 1430, 1450, 1510, 1530, 1550, 1610,
			1630, 1650, 1710, 1730, 1800, 1830, 1910, 1930, 2000, 2030, 2100,
			2130, 2200, 2230, 2330 },
			new Station[] {Station.HOBOKENTERMINAL, Station.FIRSTANDWASHINGTON, 
			Station.FIFTHANDWASHINGTON, Station.ELEVENTHANDWASHINGTON, 
			Station.FOURTEENTHANDWASHINGTON, Station.FOURTEENTHANDWILLOW, 
			Station.PABT}, 
			TrainDirection.TO_NYC, Day.SUNDAY),

	/*****************************
	 * NYC - NJ *
	 *****************************/

	WEEKDAY_WILLOW_AVE_TO_NJ("WEEKDAY_WILLOW_AVE_TO_NJ", new Integer[] { 1732, 1744, 1755,
			1810, 1815, 1826, 1850, 1856, 1908, 1914, 1922, 1930 },
			new Station[] { Station.PABT, Station.FOURTEENTHANDWILLOW, Station.ELEVENTHANDWILLOW,
			Station.FIFTHANDWILLOW, Station.FIRSTANDWILLOW, Station.HOBOKENTERMINAL}, TrainDirection.FROM_NYC, Day.WEEKDAY),

	WEEKDAY_WILLOW_AV_TO_JC("WEEKDAY_WILLOW_AV_TO_JC", new Integer[] { 1700, 1718, 1726,
			1738, 1750, 1805, 1820, 1832, 1838, 1844, 1902, 1940, 2040, 2142 }, 
			new Station[] {
			Station.PABT, Station.FOURTEENTHANDWILLOW, Station.ELEVENTHANDWILLOW,
			Station.FIFTHANDWILLOW, Station.FIRSTANDWILLOW, Station.HAMILTONPARK
		}, TrainDirection.FROM_NYC, Day.WEEKDAY),

	WEEKDAY_WILLOW_AVE_LOCAL("WEEKDAY_WILLOW_AVE_LOCAL", new Integer[] { 618, 630, 640, 650,
			700, 710, 720, 730, 740, 750, 800, 810, 820, 830, 840, 850, 900 }, new Station[] {
			Station.FOURTEENTHANDWILLOW, Station.ELEVENTHANDWILLOW,
			Station.FIFTHANDWILLOW, Station.FIRSTANDWILLOW, Station.HOBOKENTERMINAL
		}, TrainDirection.FROM_NYC, Day.WEEKDAY),

	WEEKDAY_WASHINGTON_ST_TO_NJ("WEEKDAY_WASHINGTON_ST_TO_NJ",  new Integer[] { 00, 30, 55, 130,
			200, 535, 605, 630, 640, 650, 700, 710, 715, 720, 730, 734, 736,
			741, 747, 756, 758, 800, 803, 805, 806, 809, 812, 816, 818, 820,
			822, 824, 826, 828, 832, 836, 838, 840, 842, 846, 850, 854, 858,
			906, 910, 915, 920, 930, 940, 950, 1000, 1015, 1030, 1045, 1100,
			1120, 1140, 1200, 1220, 1240, 1300, 1320, 1340, 1400, 1415, 1430,
			1445, 1500, 1515, 1530, 1545, 1600, 1612, 1624, 1636, 1644, 1652,
			1700, 1708, 1716, 1720, 1724, 1728, 1732, 1736, 1740, 1743, 1746,
			1749, 1752, 1756, 1800, 1804, 1808, 1812, 1816, 1820, 1824, 1828,
			1832, 1836, 1840, 1845, 1850, 1855, 1900, 1905, 1910, 1916, 1920,
			1924, 1928, 1932, 1936, 1940, 1944, 1948, 1952, 1956, 2005, 2010,
			2015, 2020, 2025, 2032, 2048, 2054, 2102, 2110, 2118, 2126, 2134,
			2150, 2000, 2010, 2030, 2040, 2050, 2300, 2330, 2345 },
			new Station[] {
			Station.PABT, Station.FOURTEENTHANDWILLOW, Station.FOURTEENTHANDWASHINGTON, Station.ELEVENTHANDWASHINGTON,
			Station.FIFTHANDWASHINGTON, Station.FIRSTANDWASHINGTON, Station.HOBOKENTERMINAL
		}, TrainDirection.FROM_NYC, Day.WEEKDAY),

	SATURDAY_WASHINGTON_ST_TO_NJ("SATURDAY_WASHINGTON_ST_TO_NJ", new Integer[] { 25, 55, 130, 200,
			630, 700, 720, 740, 800, 820, 840, 900, 920, 940, 1000, 1020, 1040,
			1100, 1120, 1135, 1155, 1215, 1235, 1255, 1315, 1335, 1355, 1415,
			1435, 1455, 1515, 1535, 1555, 1615, 1635, 1655, 1715, 1735, 1755,
			1815, 1835, 1855, 1915, 1935, 1955, 2015, 2035, 2055, 2115, 2135,
			2155, 2215, 2235, 2255, 2315, 2335, 2355 },
			new Station[] {
			Station.PABT, Station.FOURTEENTHANDWILLOW, Station.FOURTEENTHANDWASHINGTON, Station.ELEVENTHANDWASHINGTON,
			Station.FIFTHANDWASHINGTON, Station.FIRSTANDWASHINGTON, Station.HOBOKENTERMINAL
		}, TrainDirection.FROM_NYC, Day.SATURDAY),

	SUNDAY_WASHINGTON_ST_TO_NJ("SUNDAY_WASHINGTON_ST_TO_NJ",
			new Integer[] { 00, 55, 200, 700,
			830, 930, 1000, 1030, 1100, 1120, 1140, 1200, 1220, 1240, 1300,
			1320, 1340, 1400, 1420, 1440, 1500, 1520, 1540, 1600, 1620, 1640,
			1700, 1720, 1740, 1800, 1830, 1900, 1920, 1940, 2000, 2030, 2100,
			2200, 2230, 2300 },
			new Station[] {
			Station.PABT, Station.FOURTEENTHANDWILLOW, Station.FOURTEENTHANDWASHINGTON, Station.ELEVENTHANDWASHINGTON,
			Station.FIFTHANDWASHINGTON, Station.FIRSTANDWASHINGTON, Station.HOBOKENTERMINAL
		}, TrainDirection.FROM_NYC, Day.SUNDAY);
	
	
	private String name;
	
	private Integer[] startTimes;
	
	private Station[] stations;
	
	private TrainDirection direction;
	
	private Day day;
	
	private Route(String name, Integer[] startTimes, Station[] stations, TrainDirection direction, Day day) {
		this.name = name;
		this.startTimes = startTimes;
		this.stations = stations;
		this.direction = direction;
		this.day = day;
	}
	
	/**
	 * Converts start times to an array of milliseconds since the beginning of the day.
	 */
	public long[] getStartTimesAsMillisecondsSinceBeginningOfDay() {
		long[] times = new long[this.startTimes.length];
		
		for (int i = 0 ; i < startTimes.length; i++) {
			times[i] = convertNumberToMillisecondsSinceBeginningOfDay(startTimes[i]);
		}
		
		return times;
	}
	
	public static long convertNumberToMillisecondsSinceBeginningOfDay(Integer time) {
		int minutes = time % 100;
		int hours = time / 100;
		
		return (minutes + hours * 60) * 60 * 1000;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer[] getStartTimes() {
		return startTimes;
	}

	public void setStartTimes(Integer[] startTimes) {
		this.startTimes = startTimes;
	}

	public Station[] getStations() {
		return stations;
	}

	public void setStations(Station[] stations) {
		this.stations = stations;
	}

	public TrainDirection getDirection() {
		return direction;
	}

	public void setDirection(TrainDirection direction) {
		this.direction = direction;
	}

	public Day getDay() {
		return this.day;
	}
}
