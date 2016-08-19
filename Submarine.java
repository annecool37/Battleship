package battleship;

/**
 * @author Chia-An Chen
 * CIT 590, Spring 2015, HW11
 */
public class Submarine extends Ship {

	/**
	 * submarine constructor, set length to 1
	 */
	public Submarine() {
		// to set the inherited length variable to the correct value
		this.length = 1;
	}

	/**
	 * return ship type
	 */
	@Override
	public String getShipType() {
		return "submarine";
	}
	
	/**
	 * get ship's length
	 */
	@Override
	public int getLength() {
		return this.length;
	}
}
