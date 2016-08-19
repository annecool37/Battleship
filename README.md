# Battleship
This repository contains java codes that build a simple version of the classic game: Battleship.

### Classes
- BattleshipGame: Main Code to initiate the battleship game, read user input, and return the game result
- Ocean: A class that randomly places the ship objects onto the ocean
- Ship: A class to create a ship object and define the properties of a ship such as whether or not the ship is shot or sunk
- EmptySea: A class that extends from the Ship class to check if this grid point on the ocean is being shot ot has a ship
- Battleship, Cruiser, Destroyer, Submarine: Classes that extend from the Ship class and create ships in four different lengths

### Unit Tests
- BattleshipGameTest: Test code for testing the BattleshipGame
- OceanTest: Test code for testing the Ocean
- ShipTest: Test code for testing the Ship
