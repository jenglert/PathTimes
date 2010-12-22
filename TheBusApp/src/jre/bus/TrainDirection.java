package jre.bus;

public enum TrainDirection {
	TO_NYC(1), FROM_NYC(2);
	
	private Integer id;
	
	/**
	 * Attempts to find a TrainDirection based on an id.  If none is found, returns null.
	 */
	public static TrainDirection findById(Integer id) {
		for (TrainDirection direction : TrainDirection.values()) {
			if (direction.getId().equals(id))
				return direction;
		}
		
		return null;
	}
	
	private TrainDirection(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}
}
