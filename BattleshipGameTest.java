package battleship;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
/**
 * @author Chia-An Chen
 * CIT 590, Spring 2015, HW11
 */
public class BattleshipGameTest {

	BattleshipGame testGame;
	
	@Before
	public void setUp() throws Exception {
		testGame = new BattleshipGame();
	}

	@Test
	public void testStringToInt() {
		// valid input
		assertEquals(1, testGame.stringToInt("1,2")[0]);
		assertEquals(2, testGame.stringToInt("1,2")[1]);
		
		// invalid input
		assertEquals(100, testGame.stringToInt("1,,")[0]);
		assertEquals(100, testGame.stringToInt("a")[0]);
		assertEquals(100, testGame.stringToInt(",,,")[0]);
		assertEquals(100, testGame.stringToInt("a,2")[0]);
		assertEquals(100, testGame.stringToInt("45")[0]);
		assertEquals(100, testGame.stringToInt("1,2,3")[0]);
		assertEquals(100, testGame.stringToInt(" ")[0]);
	}


}
