package battleship;
/**
 * @author Chia-An Chen
 * CIT 590, Spring 2015, HW11
 */
public abstract class Ship {

	private int bowRow;
	private int bowColumn;
	private boolean horizontal;
	protected int length;
	protected boolean [] hit = new boolean [4];
	private boolean isShot = false;
	
	/**
	 * returns the hit array
	 * @return
	 */
	public boolean[] getHit() {
		return this.hit;
	}

	/**
	 * Returns bowRow
	 * @return
	 */
	public int getBowRow() {
		return this.bowRow;
	}

	/**
	 * Sets the value of bowRow
	 * @param row
	 */
	public void setBowRow(int row) {
		this.bowRow = row;
	}

	/**
	 * Returns bowColumn
	 * @return
	 */
	public int getBowColumn() {
		return this.bowColumn;
	}

	/**
	 * Sets the value of bowColumn
	 * @param column
	 */
	public void setBowColumn(int column) {
		this.bowColumn = column;
	}

	/**
	 * Returns horizontal
	 * @return
	 */
	public boolean isHorizontal() {
		return this.horizontal;
	}
	
	/**
	 * Sets the value of the instance variable horizontal.
	 * @param horizontal
	 */
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}
	
	/**
	 * Returns the length of this particular ship
	 * @return
	 */
	public int getLength() {
		return this.length;
	}

	
	/**
	 * an abstract method that has no body.
	 * @return
	 */
	public abstract String getShipType();
	
	/**
	 * check if there's no adjacent ship in the position
	 * @param row
	 * @param column
	 * @param ocean
	 * @return
	 */
	public boolean isPlaceable(int row, int column, Ocean ocean)  {
		// index out of ship array
		if (row < 0 || column <0 || row > 9 || column > 9) {
			return false;
		}
				
		int lastRow = row - 1;
		int nextRow = row + 1;
		int lastCol = column - 1;
		int nextCol = column + 1;

		// loop through all adjacent cells (including itself)
		for ( int rowNum = lastRow; rowNum <= nextRow ; rowNum++) {
			for (int colNum = lastCol; colNum <= nextCol ; colNum++) {
					if (ocean.isOccupied(rowNum, colNum) == true) {
						return false;
				}
			}
		}
		return true;	
	}
	
	
	/**
	 * Returns true if it is okay to put a ship of this length 
	 * with its bow in this location, with the given orientation, 
	 * and returns false otherwise.
	 * @param row
	 * @param column
	 * @param horizontal
	 * @param ocean
	 * @return
	 */
	public boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		boolean isPlaceable = true;
		
		// horizontal ship
		if (horizontal == true) {
			// loop each part of the ship through column
			for (int i =  column; i < column + this.length; i++) {
				isPlaceable = this.isPlaceable(row, i, ocean);
				if (isPlaceable == false) {
					return false;
				}
			}
		}
		
		// vertical ship
		else {
			// loop each part of the ship through row
			for (int i = row ; i < row + this.length; i++) {
				isPlaceable = this.isPlaceable(i, column, ocean);
				if (isPlaceable == false) {
					return false;
				}
			}
		}
		return true;

	}
	
	/**
	 * ÓPutsÓ the ship in the ocean.
	 * @param row
	 * @param column
	 * @param horizontal
	 * @param ocean
	 */
	public void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		this.setBowColumn(column);
		this.setBowRow(row);
		this.setHorizontal(horizontal);
		
		// horizontal ship
		if (horizontal == true) {
			for (int i = column ; i < column+this.length; i++) {
				// putting a reference to the ship in locations in the ships array
				ocean.setShipArray(row, i, this);
				}
			
			}
		
		// vertical ship
		else {
			for (int i = row ; i < row+this.length; i++) {
				// putting a reference to the ship in locations in the ships array
				ocean.setShipArray(i, column, this);
				}
			}
		}
	
	
	/**
	 * If a part of the ship occupies the given row and column, 
	 * and the ship hasnÕt been sunk, mark that part of the ship as ÓhitÓ
	 * and return true, otherwise return false.
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean shootAt(int row, int column) {		
		boolean isOccupied = false;
		int whichPartOfShip = 0;
		
		// check if a part of the ship occupies the given row and column
		if (this.horizontal == true && this.bowRow == row) {
			for (int i = this.bowColumn; i < this.bowColumn + this.length; i++) {
				if (column == i) {
					// record which part of ship
					whichPartOfShip = i - this.bowColumn;
					// the given row/column is occupied
					isOccupied = true;
					break;
				}
			}
		}
		else if (this.horizontal == false && this.bowColumn == column) {
			for (int i = this.bowRow; i < this.bowRow + this.length; i++) {
				if (row == i) {
					whichPartOfShip = i - this.bowRow;
					isOccupied = true;
					break;
				}
			}
		}
		
		// if the given row/ column is occupied and the ship is not sunk yet
		if (isOccupied == true && this.isSunk() == false ) {
			// mark that part of the ship as ÓhitÓ
			this.hit[whichPartOfShip] = true;
			isShot = true; 
			return true;
		}
		return false;
	}
	
	
	/**
	 * Return true if every part of the ship has been hit, false otherwise.
	 * @return
	 */
	public boolean isSunk() {
		for (int i =0; i < this.length; i++) {
			if (this.hit[i] == false) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns a single-character String to use in the OceanÕs print method
	 * return ÓxÓ if the ship has been sunk, ÓSÓ if it has not been sunk. 
	 */
	@Override
	public String toString() {		
		if (this.isShot == true) {
			if (this.isSunk() == true) {
				// sunk ship
				return "x"; 
			}
			else {
				// ship being partially shot
				return "S"; 
			}
		}
		return ".";
	}

}
	
	
	

