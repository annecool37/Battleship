package battleship;
/**
 * @author Chia-An Chen
 * CIT 590, Spring 2015, HW11
 */
public class Cruiser extends Ship {
	
	/**
	 * cruiser constructor, set length to 3
	 */
	public Cruiser() {
		// to set the inherited length variable to the correct value
		this.length = 3;
	}

	/**
	 * return ship type
	 */
	@Override
	public String getShipType() {
		return "cruiser";
	}
	
	/**
	 * get ship's length
	 */
	@Override
	public int getLength() {
		return this.length;
	}
}
