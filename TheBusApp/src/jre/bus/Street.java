package jre.bus;

public enum Street {

	FIRST(StreetDirection.EastWest, "First St"), 
	FIFTH(StreetDirection.EastWest, "Fifth St"), 
	ELEVENTH(StreetDirection.EastWest, "Eleventh St"), 
	FOURTEENTH(StreetDirection.EastWest, "Fourteenth St."), 
	EIGHTEENTH(StreetDirection.EastWest, "Eighteenth St."), 
	WASHINGTON(StreetDirection.NorthSouth, "Washington St."), 
	WILLOW(StreetDirection.NorthSouth, "Willow St."), 
	CLINTON(StreetDirection.NorthSouth, "Clinton St."),
	GROVE(StreetDirection.NorthSouth, "Grove St.");
	
	private StreetDirection direction;
	
	private String name;
	
	private Street(StreetDirection direction, String name) {
		this.direction = direction;
		this.name = name;
	}
	
	public StreetDirection getDirection() {
		return this.direction;
	}
	
	public String getName() {
		return this.name;
	}
}
