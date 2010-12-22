package jre.bus;

public enum Station {
	PABT, 
	FOURTEENTHANDWILLOW, 
	FOURTEENTHANDWASHINGTON, 
	ELEVENTHANDWASHINGTON, 
	ELEVENTHANDWILLOW,
	ELEVENTHANDCLINTON, 
	FIFTHANDWASHINGTON, 
	FIFTHANDWILLOW, 
	FIFTHANDCLINTON, 
	FIRSTANDCLINTON, 
	FIRSTANDWILLOW,
	FIRSTANDWASHINGTON, 
	EIGTHENTHANDGROVE, 
	HAMILTONPARK,
	HOBOKENTERMINAL;
	
	/**
	 * The order that the station is encountered.  We assume the order based on buses coming from NYC
	 */
	private Integer order;
	
	Station(Integer order) {
		
	}
}
