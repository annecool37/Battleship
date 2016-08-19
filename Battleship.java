package battleship;
/**
 * @author Chia-An Chen
 * CIT 590, Spring 2015, HW11
 */
public class Battleship extends Ship {
	
	/**
	 * battleship constructor, set length to  4
	 */
	public Battleship() {
		// to set the inherited length variable to the correct value
		this.length = 4;
	}

	/**
	 * return ship type
	 */
	@Override
	public String getShipType() {
		return "battleship";
	}

	/**
	 * get ship's length
	 */
	@Override
	public int getLength() {
		return this.length;
	}
}
