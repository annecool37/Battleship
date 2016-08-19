package battleship;
/**
 * @author Chia-An Chen
 * CIT 590, Spring 2015, HW11
 */
public class Destroyer extends Ship {
	
	/**
	 * destroyer constructor, set length to 2
	 */
	public Destroyer() {
		// to set the inherited length variable to the correct value
		this.length = 2;
	}

	/**
	 * return ship type
	 */
	@Override
	public String getShipType() {
		return "destroyer";
	}

	/**
	 * get ship's length
	 */
	@Override
	public int getLength() {
		return this.length;
	}
	
	
}
