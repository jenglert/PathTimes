package jre.bus;

public enum Street {

	FIRST(StreetDirection.EastWest, "First St", 50), 
	FIFTH(StreetDirection.EastWest, "Fifth St", 40), 
	ELEVENTH(StreetDirection.EastWest, "Eleventh St", 30), 
	FOURTEENTH(StreetDirection.EastWest, "Fourteenth St.", 10), 
	EIGHTEENTH(StreetDirection.EastWest, "Eighteenth St.", 80), 
	WASHINGTON(StreetDirection.NorthSouth, "Washington St.", null), 
	WILLOW(StreetDirection.NorthSouth, "Willow St.", null), 
	CLINTON(StreetDirection.NorthSouth, "Clinton St.", null),
	GROVE(StreetDirection.NorthSouth, "Grove St.", null);
	
	private StreetDirection direction;
	
	private String name;
	
	private Integer order;
	
	private Street(StreetDirection direction, String name, Integer order) {
		this.direction = direction;
		this.name = name;
		this.order = order;
	}
	
	public StreetDirection getDirection() {
		return this.direction;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Integer getOrder() {
		if (this.order == null) {
			throw new RuntimeException("Attempting to retrieve the order of a North South street.");
		}
		
		return this.order;
	}
}
