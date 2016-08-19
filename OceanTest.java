package battleship;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
/**
 * @author Chia-An Chen
 * CIT 590, Spring 2015, HW11
 */
public class OceanTest {

	private Ocean ocean;
	private Ship battleship;
	private Ship cruiser;
	private Ship destroyer;
	private Ship submarine;
	private Ship [] shipGarage ;

	@Before
	public void setUp() throws Exception {
		ocean = new Ocean();
		shipGarage = ocean.getShipGarage();
		battleship= shipGarage[0];
		cruiser = shipGarage[1];
		destroyer = shipGarage[3];
		submarine = shipGarage[6];	
	}


	@Test
	public void testOcean() {
		// though implicitly tested, still we wanna make sure it starts w/ an "empty" ship array
		assertEquals("empty", ocean.getShipArray()[0][0].getShipType());
		assertEquals("empty", ocean.getShipArray()[3][5].getShipType());
		assertEquals("empty", ocean.getShipArray()[9][9].getShipType());

	}

	@Test
	public void testGarage() {
		// testParkShipInGarage() & testgetGarage()
		// parkShipInGarage is done in the setUp
		assertEquals("battleship", ocean.getShipGarage()[0].getShipType());
		assertEquals("cruiser", ocean.getShipGarage()[1].getShipType());
		assertEquals("destroyer", ocean.getShipGarage()[3].getShipType());
		assertEquals("submarine", ocean.getShipGarage()[6].getShipType());

	}

	@Test
	public void testIsWithinOcean() {
		assertFalse(ocean.isWithinOcean(-1, -1));
		assertFalse(ocean.isWithinOcean(0, -1));
		assertFalse(ocean.isWithinOcean(-1, 0));
		assertFalse(ocean.isWithinOcean(0, 10));
		assertFalse(ocean.isWithinOcean(10, 0));
		assertFalse(ocean.isWithinOcean(10, 10));
		assertTrue(ocean.isWithinOcean(0, 0));
		assertTrue(ocean.isWithinOcean(1, 5));
		assertTrue(ocean.isWithinOcean(9, 9));

	}

	@Test
	public void testIsOccupied() {
		// if index out of bound, return true
		assertFalse(ocean.isOccupied(0, -1));
		assertFalse(ocean.isOccupied(-1, 0));
		assertFalse(ocean.isOccupied(0, 10));
		assertFalse(ocean.isOccupied(10, 0));

		// place battleship 
		battleship.placeShipAt(6, 5, true, ocean);

		// non occupied (loop around the ship)
		assertFalse(ocean.isOccupied(5, 4));
		assertFalse(ocean.isOccupied(5, 5));
		assertFalse(ocean.isOccupied(5, 6));
		assertFalse(ocean.isOccupied(5, 7));
		assertFalse(ocean.isOccupied(5, 8));
		assertFalse(ocean.isOccupied(5, 9));
		assertFalse(ocean.isOccupied(6, 4));
		assertFalse(ocean.isOccupied(6, 9));
		assertFalse(ocean.isOccupied(7, 4));
		assertFalse(ocean.isOccupied(7, 5));
		assertFalse(ocean.isOccupied(7, 6));
		assertFalse(ocean.isOccupied(7, 7));
		assertFalse(ocean.isOccupied(7, 8));
		assertFalse(ocean.isOccupied(7, 9));

		// occupied
		assertTrue(ocean.isOccupied(6, 5));
		assertTrue(ocean.isOccupied(6, 6));
		assertTrue(ocean.isOccupied(6, 7));
		assertTrue(ocean.isOccupied(6, 8));

	}

	@Test
	public void testShootAt() {
		// place battleship
		battleship.placeShipAt(6, 6, true, ocean);

		// miss
		assertFalse(ocean.shootAt(3, 3));
		assertFalse(ocean.shootAt(5, 6));

		// shoot the ship
		assertTrue(ocean.shootAt(6, 6));
		assertTrue(ocean.shootAt(6, 7));
		assertTrue(ocean.shootAt(6, 8));
		assertTrue(ocean.shootAt(6, 9));

		// will be false since the battleship is sunk
		assertTrue(battleship.isSunk());
		assertFalse(ocean.shootAt(6, 6));

	}

	@Test
	public void testGetShotsFired() {
		this.placeAllShips();
		assertEquals(0, ocean.getShotsFired());
		// invalid shoot dont count
		ocean.shootAt(-1,-1); 
		assertEquals(0, ocean.getShotsFired());
		
		ocean.shootAt(3,3);
		ocean.shootAt(3,9);
		assertEquals(2, ocean.getShotsFired());
		
		// if a user decide to shot same place for multiple times
		// we will still count the shots
		ocean.shootAt(3,4);
		ocean.shootAt(3,4);
		ocean.shootAt(3,4);
		assertEquals(5, ocean.getShotsFired());
		
		// sunk a submarine
		ocean.shootAt(9,0);
		assertEquals(6, ocean.getShotsFired());
		
		// shoot a sunk ship wont count & wont conduct shooting
		// shotFired number remains
		ocean.shootAt(9,0);
		assertEquals(6, ocean.getShotsFired());
	}

	@Test
	public void testGetTrueCount() {
		boolean [] booleanArray = new boolean[10];
		assertEquals(0 , ocean.getTrueCount(booleanArray));
		booleanArray[0] = true;
		booleanArray[3] = true;
		booleanArray[7] = true;
		assertEquals(3 , ocean.getTrueCount(booleanArray));
	}

	@Test
	public void testGetHitCount() {
		cruiser.placeShipAt(0, 0, false, ocean);
		battleship.placeShipAt(5, 0, true, ocean);
		// no hit count before any shooting
		assertEquals(0, ocean.getHitCount());

		// shoot the cruiser 
		ocean.shootAt(1 ,0);
		assertEquals(1, ocean.getHitCount());
		// additional hit would increase hit count
		ocean.shootAt(1, 0); 
		assertEquals(2, ocean.getHitCount());

		// sunk cruiser
		ocean.shootAt(0, 0);
		ocean.shootAt(2, 0);
		assertEquals(4, ocean.getHitCount());
		assertTrue(cruiser.isSunk());
		
		// shoot a sunk ship, hit count remains
		ocean.shootAt(0, 0);
		assertEquals(4, ocean.getHitCount());

		// shoot on empty sea, hit count remains
		ocean.shootAt(0, 3); 
		assertEquals(4, ocean.getHitCount());

		// shoot battleship
		ocean.shootAt(5,1);
		ocean.shootAt(5,3);
		assertEquals(6, ocean.getHitCount());
	}

	@Test
	public void testGetShipsSunk() {
		submarine.placeShipAt(0, 0, true, ocean);
		destroyer.placeShipAt(2, 0, true, ocean);
		cruiser.placeShipAt(1, 3, false, ocean);

		// no ship is sunk
		assertEquals(0, ocean.getShipsSunk());

		// sunk the submarine! 
		ocean.shootAt(0 ,0);
		assertEquals(1, ocean.getShipsSunk());

		// shoot some other ship but not shooting to sunk
		ocean.shootAt(2,0);
		ocean.shootAt(1,3);
		assertEquals(1, ocean.getShipsSunk());

		// sunk all the ships
		ocean.shootAt(2,1);
		ocean.shootAt(2,3);
		ocean.shootAt(3,3);
		assertEquals(3, ocean.getShipsSunk());

	}

	/**
	 * helper function to place all ships on the ocean for testing isGameOver
	 */
	private void placeAllShips() {
		// place ships as indicated in hw pdf p.2
		shipGarage[0].placeShipAt(6, 9, false, ocean);
		shipGarage[1].placeShipAt(1, 1, true, ocean);
		shipGarage[2].placeShipAt(3, 2, true, ocean);
		shipGarage[3].placeShipAt(5, 1, true, ocean);
		shipGarage[4].placeShipAt(1, 6, false, ocean);
		shipGarage[5].placeShipAt(2, 9, false, ocean);
		shipGarage[6].placeShipAt(3, 0, true, ocean);
		shipGarage[7].placeShipAt(9, 0, true, ocean);
		shipGarage[8].placeShipAt(5, 4, true, ocean);
		shipGarage[9].placeShipAt(5, 7, true, ocean);

	}

	@Test
	public void testIsGameOver() {
		this.placeAllShips();
		assertEquals(0,ocean.getShipsSunk());
		assertFalse(ocean.isGameOver());	
		// sunk partial ships (those in upper ocean)
		for (int i=0; i < 5; i++) { 
			for (int j=0; j<10; j++) {
				ocean.shootAt(i,j);
			}
		}
		assertEquals(5,ocean.getShipsSunk());
		assertFalse(ocean.isGameOver());

		// sunk the rest ships
		for (int i=5; i < 10; i++) { 
			for (int j=0; j<10; j++) {
				ocean.shootAt(i,j);
			}
		}
		assertEquals(10,ocean.getShipsSunk());
		assertTrue(ocean.isGameOver());
	}

	@Test
	public void testGetShipArray() {
		assertEquals("empty", ocean.getShipArray()[0][0].getShipType());
		assertEquals("empty", ocean.getShipArray()[3][5].getShipType());
		assertEquals("empty", ocean.getShipArray()[9][9].getShipType());
		battleship.placeShipAt(6, 9, false, ocean);
		assertEquals("battleship", ocean.getShipArray()[7][9].getShipType());
		assertEquals("battleship", ocean.getShipArray()[9][9].getShipType());
	}

	@Test
	public void testSetShipArray() {
		assertEquals("empty", ocean.getShipArray()[6][6].getShipType());
		assertEquals("empty", ocean.getShipArray()[1][1].getShipType());
		ocean.setShipArray(6, 6, battleship);
		ocean.setShipArray(1, 1, submarine);
		assertEquals("battleship", ocean.getShipArray()[6][6].getShipType());
		assertEquals("submarine", ocean.getShipArray()[1][1].getShipType());
	}

	// cannot really unit test the print method, but it's "visually" verified!
	@Test
	public void testPrint() {
		ocean.print(); // empty ships --> all dots
		this.placeAllShips();
		ocean.print(); // no shooting --> all dots
		ocean.shootAt(0,0); // miss --> "-"
		ocean.shootAt(1,1); // shoot at the ship, but not sunk --> "S"
		ocean.shootAt(1,3); // shoot at the ship, but not sunk --> "S"
		ocean.shootAt(3,0); // sunk a ship --> "x"
		ocean.print();
		// sunk the almost sunk ship 
		ocean.shootAt(1,2);  // ship sunk --> whole ship marks w/ "x"
		ocean.print();

	}

}
