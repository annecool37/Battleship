package battleship;
/**
 * @author Chia-An Chen
 * CIT 590, Spring 2015, HW11
 */
public class EmptySea extends Ship {

	private boolean isShot = false;
	
	/**
	 * constructor sets the inherited length variable to 1.
	 */
	public EmptySea() {
		this.length = 1;
	}

	
	/**
	 * returns false to indicate that nothing was hit.
	 */
	@Override
	public boolean shootAt(int row, int column) {
		this.isShot = true;
		return false;
	}
	
	/**
	 * returns false to indicate that you didn’t sink anything.
	 */
	@Override 
	public boolean isSunk() {
		return false;
	}
	
	/**
	 * Returns a single-character String 
	 * to use in the Ocean’s print method
	 */
	@Override
	public String toString() {
		if (this.isShot == true ) {
			return "-";
		}
		return ".";
	}
	
	/**
	 * returns the string ”empty”
	 */ 
	@Override
	public String getShipType() {
		return "empty";
	}
	

}
