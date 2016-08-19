package battleship;
import java.util.*;
/**
 * @author Chia-An Chen
 * CIT 590, Spring 2015, HW11
 */
public class Ocean {

	private Ship[][] shipArray;
	private Random random;
	private Ship battleship;
	private Ship cruiser1;
	private Ship cruiser2;
	private Ship destroyer1;
	private Ship destroyer2;
	private Ship destroyer3;
	private Ship submarine1;
	private Ship submarine2;
	private Ship submarine3;
	private Ship submarine4;
	private boolean [] sunkArray ;
	private int shotsNum;
	private int hitsNum;
	private Ship [] shipGarage;

	/**
	 * The constructor. 
	 * Creates an ”empty” ocean (fills the ships array with EmptySeas).
	 * Also initializes any game variables
	 */
	public Ocean() {
		// create empty oceans
		this.shipArray = new Ship[10][10];
		for (int i =0; i<10; i++) {			
			for (int j =0; j<10; j++) {
				this.shipArray[i][j] = new EmptySea();
			}
		}

		// initialize all ships
		battleship = new Battleship();
		cruiser1 = new Cruiser();
		cruiser2 = new Cruiser();
		destroyer1 = new Destroyer();
		destroyer2 = new Destroyer();
		destroyer3 = new Destroyer();
		submarine1 = new Submarine();
		submarine2 = new Submarine();
		submarine3 = new Submarine();
		submarine4 = new Submarine();

		// start the random number
		random = new Random();

		// sunkArray to record if ships are sunk
		sunkArray = new boolean[10];

		// an array to store ships
		this.shipGarage = new Ship[10];
		// park all ships in garage
		this.parkShipsInGarage();

		// initialized shots & hit nums
		shotsNum = 0;
		hitsNum = 0;

	}

	/**
	 * helper function that places the ships in an array
	 */
	public void parkShipsInGarage() {
		// park ship in garage for easy operation
		this.shipGarage[0] = battleship;
		this.shipGarage[1] = cruiser1;
		this.shipGarage[2] = cruiser2;
		this.shipGarage[3] = destroyer1;
		this.shipGarage[4] = destroyer2;
		this.shipGarage[5] = destroyer3;
		this.shipGarage[6] = submarine1;
		this.shipGarage[7] = submarine2;
		this.shipGarage[8] = submarine3;
		this.shipGarage[9] = submarine4;
	}

	/**
	 * get the garage array
	 * @return
	 */
	public Ship[] getShipGarage() {
		return this.shipGarage;
	}

	/**
	 * Place one specific ship randomly on the ocean.
	 * @param ship
	 */
	public void placeOneShipRandomly(Ship ship) {
		boolean isOkToPlace = false;
		int row = 10; 
		int column = 10 ; 
		boolean horizontal = false;

		while (isOkToPlace != true) {
			// generate random numbers
			row = random.nextInt(10);
			column = random.nextInt(10);
			horizontal = random.nextBoolean();

			// check if is okay to place
			isOkToPlace = ship.okToPlaceShipAt(row, column, horizontal, this);
		}

		// place the ship (also sat the bow row/ bow column/ horizontal)
		ship.placeShipAt(row, column, horizontal, this);	
	}

	/**
	 * Place all ten ships randomly on the (initially empty) ocean.
	 */
	public void placeAllShipsRandomly() {
		// Place larger ships before smaller ones. (ships are parked in an order of size)
		for (int i=0; i<10; i++) {
			this.placeOneShipRandomly(this.shipGarage[i]);
		}	
	}

	/**
	 * check if the input row and column is within the ocean
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean isWithinOcean (int row, int column) {
		if (row < 0 || column <0 || row > 9 || column > 9) {
			return false;
		}
		return true;
	}


	/**
	 * Returns true if the given location contains a ship, false if it does not.
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean isOccupied(int row, int column) {
		boolean isWithinOcean = this.isWithinOcean(row, column);
		if (isWithinOcean == true ) {
			if (shipArray[row][column] instanceof EmptySea ) {
				return false;
			}
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns true if the given location contains a ”real” ship, still afloat, (not an EmptySea)
	 * false if it does not.
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean shootAt(int row, int column) {	
		boolean isWithinOcean = this.isWithinOcean(row, column);
		if (isWithinOcean == true ) {
			// return true only if the ship not an empty one nor a sunk one
			if (shipArray[row][column].getShipType() != "empty" && shipArray[row][column].isSunk()== false) {
				// increase the hits/shots count
				this.hitsNum ++;	
				this.shotsNum ++;
				// shoot the ship
				shipArray[row][column].shootAt(row, column);
				return true;
			}

			// if it's an empty sea
			else if (shipArray[row][column].getShipType() == "empty") {
				// still gotta conduct shooting, but not returning true
				// increasing shut counts only
				this.shotsNum ++;
				shipArray[row][column].shootAt(row, column);
			}
			// no shooting if it's a sunk ship

		}
		return false;
	}

	/**
	 * Returns the number of shots fired (in this game)
	 * @return
	 */
	public int getShotsFired() {
		return this.shotsNum;
	}

	/**
	 * Returns the number of "true" in a boolean array
	 * @param theArray
	 * @return
	 */
	public int getTrueCount(boolean[] theArray) {
		int counter =0;
		for (boolean trueOrFalse: theArray) {
			if (trueOrFalse == true) {
				counter ++;
			}
		}
		return counter;
	}


	/**
	 * Returns the number of hits recorded (The number of times a shot hit a ship)
	 * @return
	 */
	public int getHitCount() {
		/**
		 * . 
		 * If the user shoots the same part of a ship more than once, 
		 * every hit is counted, 
		 * even though the additional ”hits” don’t do the user any good.
		 */

		return this.hitsNum;

		//		// All hits are counted, not just the first time a given square is hit.
		//		int hit =0;
		//		// this.parkShipsInGarage();
		//		for (int i=0; i<10; i++) {
		//			boolean [] hitArray = this.shipGarage[i].getHit();
		//			hit += this.getTrueCount(hitArray);
		//		}	
		//		return hit;
	}

	/**
	 * Returns the number of ships sunk (in this game).
	 * @return
	 */
	public int getShipsSunk() {
		// this.parkShipsInGarage();
		for (int i=0; i<10; i++) {
			this.sunkArray[i] = this.shipGarage[i].isSunk();
		}
		return getTrueCount(this.sunkArray);
	}

	/**
	 * Returns true if all ships have been sunk, otherwise false.
	 * @return
	 */
	public boolean isGameOver() {
		if (this.getShipsSunk() == 10) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the 10x10 array of ships.
	 * @return
	 */
	public Ship[][] getShipArray() {
		return this.shipArray;
	}

	/**
	 * set the shipArray.
	 * @return
	 */
	public void setShipArray(int row, int column, Ship ship) {
		this.shipArray[row][column] = ship;
	}


	/** 
	 * Prints the ocean. Row number is on the left edge and column number is along the top.
	 */
	public void print() {		
		// Use ’S’ to indicate a location that you have fired upon and hit a (real) ship, 
		// ’-’ to indicate a location that you have fired upon and found nothing there, 
		// ’x’ to indicate a location containing a sunken ship,
		// and ’.’ (a period) to indicate a location that you have never fired upon.
		System.out.println();
		for (int i = 0; i <= 10; i++) {
			if (i==0) {
				System.out.println("    0 1 2 3 4 5 6 7 8 9");
			}
			else { 
				System.out.print("  ");
				System.out.print(i-1 +" ");
				for (int j = 0; j < 10; j++) {
					Ship ship = this.shipArray[i-1][j];
					if (ship.getShipType()=="empty") {
						System.out.print(ship + " ");
					}
					else if (ship.isHorizontal() == true) {
						if (ship.getHit()[j - ship.getBowColumn()] == true) {
							System.out.print(ship + " ");	
						}
						else {
							System.out.print("." + " ");
						}
					}
					else if (ship.isHorizontal() == false ) {
						if (ship.getHit()[i - 1 - ship.getBowRow()] == true) {
							System.out.print(ship + " ");
						}	
						else {
							System.out.print("." + " ");
						}
					}
				}
				System.out.println();
			}
		}
		System.out.println();
	}

	// easier for visualization
	//	for (int i = 0; i <= 10; i++) {
	//		if (i==0) {
	//			System.out.print("  0 1 2 3 4 5 6 7 8 9");
	//		}
	//		else { 
	//			System.out.print(i-1+" ");
	//			for (int j = 0; j < 10; j++) {
	//				System.out.print( this.shipArray[i-1][j] + " ");
	//			}
	//		}
	//		System.out.println();
	//	}


}



