package battleship;
import java.util.Scanner;
/**
 * @author Chia-An Chen
 * CIT 590, Spring 2015, HW11
 */
public class BattleshipGame {

	private Ocean ocean;
	private boolean run;
	private boolean gameOver;

	/**
	 * The constructor of the Battleship Game
	 * 
	 */
	public BattleshipGame() {
		// create an ocean object
		this.ocean = new Ocean();
		// place the ships randomly
		this.ocean.placeAllShipsRandomly();

		this.run = true;
		this.gameOver = false;
	}

	/**
	 * This creates a BattleshipGame object and calls its start method.
	 * @param args
	 */
	public static void main(String[] args) {
		BattleshipGame game = new BattleshipGame();
		game.start();
	}


	/**
	 * This method will read commands from the player,  
	 * call the corresponding method, and print the game results. 
	 */
	public void start() {
		while (this.run == true) {
			this.println("Hi! Welcome to the Battleship Game!\n\n"
					+ "RULES:\n"
					+ "- The computer will place 10 ships horizontally or vertically \n"
					+ "  without immediate adjacent to each other on the ocean\n"
					+ "    one battleship, a length of four\n"
					+ "    two cruisers, each is a length of three\n"
					+ "    three destroyers, each is a length of two\n"
					+ "    four submarines, each is a length of one\n"
					+ "- The goal is to sink ALL ships on the ocean\n\n"
					+ "INDICATIONS:\n"
					+ "'-': miss shot, 'S': part of a ship, 'x': a sunk ship\n\n"
					+ "COMMANDS:\n"
					+ " To shoot, enter row,column (row is on the left edge, column is on the top)\n"
					+ " To quit the game, enter quit\n\n"
					+ "Now Let the Game START! :)\n");

			// print the ocean
			this.ocean.print();

			while (this.gameOver == false) { 	
				// ask for input
				this.println("Please enter a command:");
				String command = this.readLine();

				if (command.toLowerCase().equals("quit")) {
					// exit the while loop
					this.gameOver = true;
					// quit the game
					this.quit();
				}

				else {
					// process input from string to integer
					int[] position = stringToInt(command);

					if (position[0] == 100) {
						this.println("not a valid row,column input\n");
					}

					else {
						//  shoot on the ocean 
						ocean.shootAt(position[0],  position[1]);

						// display the ocean; 
						ocean.print();

						// check if the game is over
						this.gameOver = ocean.isGameOver();

						if (this.gameOver == true) {
							// calculate hit rate
							String hitRate = this.getHitRate();

							// print final scores; 
							this.println("Congrats! You've sunk all the ships!\n"
									+ "The total shots you've fired are " + ocean.getShotsFired() + ".\n"
									+ "The hit rate is " + hitRate + ".");

							// and ask the user if he/she wants to play again. 
							this.println("Do you want to play again? (enter y for yes)");
							String input = this.readLine();
							if (input.equals("y")) {
								// reset the variables
								this.gameOver = false;

								// game restarts
								this.println("Game restarting...");

								// recreate an ocean object
								this.ocean = new Ocean();

								// place the ships randomly
								this.ocean.placeAllShipsRandomly();

								// print the ocean
								this.ocean.print();			
							}
							else {
								// quit the game
								this.quit();
							}

						}
						else {	
							// if game is not over yet, display number of ships sunk
							this.println("number of ships sunk: " + ocean.getShipsSunk());
						}
					}
				}
			} 
		}
	}



	/**
	 * get the hit rate of the game = total hit(20) / total shot 
	 * @return
	 */
	private String getHitRate() {
		double shotsNum = ocean.getShotsFired();
		// total ships got 20 shot to sunk, times 100 to make the percentage
		double hitRate = 2000/shotsNum;
		return (int)hitRate + "%";
	}

	/**
	 * prints the message 
	 * @param message
	 */
	public void println(String message) {
		System.out.println(message);
	}


	/**
	 * read the full line but not just the next word
	 * @return
	 */
	public String readLine() {
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine();
	}

	/**
	 * turn string passed in into integer array
	 * @param inputString
	 * @return
	 */
	public int[] stringToInt(String inputString) {
		int[] numArray =  new int[2]; 
		String[] string = {};
		if (inputString.length() == 3 ) {				
			for (int i = 0; i < 2; i++) {
				try {
					// process input
					string = inputString.split(",");
					int num = Integer.parseInt(string[i]);
					numArray[i] = num;	
				}
				catch (NumberFormatException e) {
					// handle non number input
					numArray[0] =100; // tag
				}
				catch (ArrayIndexOutOfBoundsException e) {
					// prevent break down if input contains more than one ","
					numArray[0] =100; // tag
				}
			}
		}
		else {
			// if input length !=3, then it must be an invalid input
			numArray[0] =100; // tag
		}
		return numArray;
	}

	/**
	 * quit the game.
	 */
	public void quit() {
		this.println("Goodbye.");
		this.run = false;

	}

}

