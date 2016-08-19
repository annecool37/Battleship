package battleship;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
/**
 * @author Chia-An Chen
 * CIT 590, Spring 2015, HW11
 */
public class ShipTest {

	private Ship battleship;
	private Ship cruiser;
	private Ship destroyer;
	private Ship submarine;
	private Ship emptySea;
	private Ocean ocean;
	
	private int bowRow;
	private int bowColumn;
	private boolean horizontal;
	protected int length;
	protected boolean [] hit = new boolean [4];
	
	@Before
	public void setUp() throws Exception {
		battleship= new Battleship();
		cruiser = new Cruiser();
		destroyer = new Destroyer();
		submarine = new Submarine();
		emptySea = new EmptySea();
		ocean = new Ocean();		
	}

	@Test
	public void testGetHit() {
		// since I cannot just compare if two array are identical due to memory location
		// just check if the value is the same
		assertEquals(hit[0], battleship.getHit()[0]);
		assertEquals(hit[1], battleship.getHit()[1]);
		assertEquals(hit[2], battleship.getHit()[2]);
		assertEquals(hit[3], battleship.getHit()[3]);
	}
	
	@Test
	public void testGetBowRow() {
		assertEquals(this.bowRow, battleship.getBowRow());
		assertEquals(this.bowRow, cruiser.getBowRow());
		assertEquals(this.bowRow, destroyer.getBowRow());
		assertEquals(this.bowRow, submarine.getBowRow());
		assertEquals(this.bowRow, emptySea.getBowRow());
	}

	@Test
	public void testSetBowRow() {
		battleship.setBowRow(1);
		cruiser.setBowRow(2);
		destroyer.setBowRow(3);
		submarine.setBowRow(4);
		emptySea.setBowRow(5);
		assertEquals(1, battleship.getBowRow());
		assertEquals(2, cruiser.getBowRow());
		assertEquals(3, destroyer.getBowRow());
		assertEquals(4, submarine.getBowRow());
		assertEquals(5, emptySea.getBowRow());
	}

	@Test
	public void testGetBowColumn() {
		assertEquals(this.bowColumn, battleship.getBowColumn());
		assertEquals(this.bowColumn, cruiser.getBowColumn());
		assertEquals(this.bowColumn, destroyer.getBowColumn());
		assertEquals(this.bowColumn, submarine.getBowColumn());
		assertEquals(this.bowColumn, emptySea.getBowColumn());
	}

	@Test
	public void testSetBowColumn() {
		battleship.setBowColumn(1);
		cruiser.setBowColumn(2);
		destroyer.setBowColumn(3);
		submarine.setBowColumn(4);
		emptySea.setBowColumn(5);
		assertEquals(1, battleship.getBowColumn());
		assertEquals(2, cruiser.getBowColumn());
		assertEquals(3, destroyer.getBowColumn());
		assertEquals(4, submarine.getBowColumn());
		assertEquals(5, emptySea.getBowColumn());
	}

	@Test
	public void testIsHorizontal() {
		assertEquals(this.horizontal, battleship.isHorizontal());
		assertEquals(this.horizontal, cruiser.isHorizontal());
		assertEquals(this.horizontal, destroyer.isHorizontal());
		assertEquals(this.horizontal, submarine.isHorizontal());
		assertEquals(this.horizontal, emptySea.isHorizontal());
	}

	@Test
	public void testSetHorizontal() {
		battleship.setHorizontal(true);
		cruiser.setHorizontal(true);
		destroyer.setHorizontal(true);
		submarine.setHorizontal(false);
		emptySea.setHorizontal(false);
		assertTrue(battleship.isHorizontal());
		assertTrue(cruiser.isHorizontal());
		assertTrue(destroyer.isHorizontal());
		assertFalse(submarine.isHorizontal());
		assertFalse(emptySea.isHorizontal());
	}


	@Test
	public void testGetLength() {
		assertEquals(4, battleship.getLength());
		assertEquals(3, cruiser.getLength());
		assertEquals(2, destroyer.getLength());
		assertEquals(1, submarine.getLength());
		assertEquals(1, emptySea.getLength());
	}

	@Test
	public void testGetShipType() {
		assertEquals("battleship", battleship.getShipType());
		assertEquals("cruiser", cruiser.getShipType());
		assertEquals("destroyer", destroyer.getShipType());
		assertEquals("submarine", submarine.getShipType());
		assertEquals("empty", emptySea.getShipType());
	}

	
	@Test
	public void isPlaceable() {
		destroyer.placeShipAt(2, 3, true, ocean);
		// loop though the ship and should all be non-placeable
		assertFalse(submarine.isPlaceable(1,2, ocean));
		assertFalse(submarine.isPlaceable(1,3, ocean));
		assertFalse(submarine.isPlaceable(1,4, ocean));
		assertFalse(submarine.isPlaceable(1,5, ocean));
		assertFalse(submarine.isPlaceable(2,5, ocean));
		assertFalse(submarine.isPlaceable(3,5, ocean));
		assertFalse(submarine.isPlaceable(3,4, ocean));
		assertFalse(submarine.isPlaceable(3,3, ocean));
		assertFalse(submarine.isPlaceable(3,2, ocean));
		assertFalse(submarine.isPlaceable(2,2, ocean));
		
		// position that should work including boundaries and corners
		assertTrue(submarine.isPlaceable(0,0, ocean));
		assertTrue(submarine.isPlaceable(0,1, ocean));
		assertTrue(submarine.isPlaceable(1,0, ocean));
		assertTrue(submarine.isPlaceable(0,9, ocean));
		assertTrue(submarine.isPlaceable(9,0, ocean));
		assertTrue(submarine.isPlaceable(9,9, ocean));
		assertTrue(submarine.isPlaceable(4,6, ocean));
		assertTrue(submarine.isPlaceable(8,8, ocean));
		
		// index out of ocean
		assertFalse(submarine.isPlaceable(0,-1, ocean));
		assertFalse(submarine.isPlaceable(-1,0, ocean));
		assertFalse(submarine.isPlaceable(10,0, ocean));
		assertFalse(submarine.isPlaceable(0,10, ocean));
	
	}
	
	@Test
	public void testOkToPlaceShipAt() {
		// index out of ocean
		assertFalse(submarine.okToPlaceShipAt(0,-1, true, ocean));
		assertFalse(submarine.okToPlaceShipAt(-1,0, true, ocean));
		assertFalse(submarine.okToPlaceShipAt(10,0, true, ocean));
		assertFalse(submarine.okToPlaceShipAt(0,10, true, ocean));

		destroyer.placeShipAt(2, 3, true, ocean);
		// horizontal adjacent
		assertFalse(submarine.okToPlaceShipAt(1, 3, true, ocean));
		assertFalse(cruiser.okToPlaceShipAt(1, 4, true, ocean));
		assertFalse(battleship.okToPlaceShipAt(3, 0, true, ocean));
		// vertically adjacent
		assertFalse(submarine.okToPlaceShipAt(3, 3, true, ocean));
		assertFalse(destroyer.okToPlaceShipAt(0, 4, false, ocean));
		assertFalse(battleship.okToPlaceShipAt(0, 2, false, ocean));
		assertFalse(battleship.okToPlaceShipAt(0, 5, false, ocean));
		// diagonally adjacent
		assertFalse(submarine.okToPlaceShipAt(1, 2, false, ocean));
		assertFalse(submarine.okToPlaceShipAt(3, 5, false, ocean));
		assertFalse(cruiser.okToPlaceShipAt(3, 0, true, ocean));
		assertFalse(destroyer.okToPlaceShipAt(0, 5, false, ocean));

		// legal placement
		assertTrue(battleship.okToPlaceShipAt(0, 0, true, ocean));
		assertTrue(battleship.okToPlaceShipAt(0, 1, true, ocean));
		assertTrue(battleship.okToPlaceShipAt(4, 4, false, ocean));
		assertTrue(battleship.okToPlaceShipAt(4, 4, false, ocean));
		assertTrue(battleship.okToPlaceShipAt(0, 6, true, ocean));
		assertTrue(battleship.okToPlaceShipAt(6, 0, false, ocean));
		
		// ship sticking out
		assertFalse(battleship.okToPlaceShipAt(0, 7, true, ocean));
		assertFalse(battleship.okToPlaceShipAt(8, 8, true, ocean));
		assertFalse(battleship.okToPlaceShipAt(8, 8, false, ocean));	
		
	}

	@Test
	public void testPlaceShipAt() {
		assertFalse(ocean.isOccupied(6, 6));
		battleship.placeShipAt(6, 6, true, ocean);
		assertFalse(ocean.isOccupied(6, 5));
		assertTrue(ocean.isOccupied(6, 6));
		assertTrue(ocean.isOccupied(6, 7));
		assertTrue(ocean.isOccupied(6, 8));
		assertTrue(ocean.isOccupied(6, 9));
		
		assertFalse(ocean.isOccupied(2, 2));
		destroyer.placeShipAt(2, 2, false, ocean);
		assertTrue(ocean.isOccupied(2, 2));
		assertTrue(ocean.isOccupied(3, 2));


		
	}

	@Test
	public void testShootAt() {
		// emptySea
		assertFalse(emptySea.shootAt(0,0));
		
		// normal ships
		// place ship
		battleship.placeShipAt(6, 6, true, ocean);
		// failure shoot
		assertFalse(battleship.shootAt(5,9));
		// successful shoot
		assertTrue(battleship.shootAt(6,6));
		assertTrue(battleship.shootAt(6,7));
		assertTrue(battleship.shootAt(6,8));
		assertTrue(battleship.shootAt(6,9));
		// after sunk shoot
		assertFalse(battleship.shootAt(6,9));
		
		
		
	} 
	
	@Test
	public void testIsSunk() {
		// emptySea
		assertFalse(emptySea.isSunk());
		
		// normal ship
		battleship.placeShipAt(6, 6, true, ocean);
		assertFalse(battleship.isSunk()); // not sunk before any shoot
		battleship.shootAt(6,6);
		battleship.shootAt(6,7);
		assertFalse(battleship.isSunk()); // not sunk yet
		battleship.shootAt(6,8);
		battleship.shootAt(6,9);
		assertTrue(battleship.isSunk()); // sunk after four shoots
		
	
	}

	@Test
	public void testToString() {
		// emptySea
		assertEquals(".", emptySea.toString()); // not shoot yet --> "."
		emptySea.shootAt(0,0);
		assertEquals("-", emptySea.toString()); // miss --> "-"
		
		// normal ship
		cruiser.placeShipAt(3, 3, true, ocean);
		assertEquals(".", cruiser.toString());  // treat as empty if it's not shot
		cruiser.shootAt(3,3);
		cruiser.shootAt(3,4);
		assertEquals("S", cruiser.toString()); // shot but is not sunk yet
		cruiser.shootAt(3,5);
		assertEquals("x", cruiser.toString()); // print x if the ship is sunk
	}

}
