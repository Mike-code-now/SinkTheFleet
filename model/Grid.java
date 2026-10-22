package com.mycompany.battleships;

/** A player's 10x10 grid with their cells and ships. */
public class Grid {

    Player player; // Owner of this grid.

    Ship[] ships = new Ship[5]; // Array of ships, empty until they are placed.

    // The cells, divided first in columns and then in rows: grid[column][row].
    Cell[][] grid = new Cell[10][10];

    // Initializes the grid with the player.
    public Grid(Player player) {
        this.player = player;

        grid = new Cell[10][10];

        // Creates each cell in the grid with the starting values.
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Cell x = new Cell(false, 0);
                grid[i][j] = x;
            }
        }
    }

    /** Returns the player this grid belongs to. */
    public Player getPlayer() {
        return this.player;
    }

    /** Returns the array of ships. */
    public Ship[] getShips() {
        return this.ships;
    }

    /** Returns all the cells of the grid. */
    public Cell[][] getCells() {
        return this.grid;
    }

    /** Places the ship and adds it to the array of ships. */
    public boolean placeShip(int length, int xCoord, int yCoord, boolean orientation,
            int shipNum) {

        // Creates a new ship.
        Ship x = new Ship(length, xCoord, yCoord, orientation);

        ships[shipNum] = x;

        // Checks if the orientation is horizontal or vertical.
        if (orientation == true) {

            // Places the ship in its coordinates.
            for (int i = 0; i < length; i++) {
                grid[xCoord + i][yCoord].setShip(shipNum);
            }
        } else {

            // Places the ship in its coordinates.
            for (int j = 0; j < length; j++) {
                grid[xCoord][yCoord + j].setShip(shipNum);
            }
        }
        return true;
    }

    /** Returns true if the ship can be placed here, false if not. */
    public boolean canPlaceShip(int length, int xCoord, int yCoord, boolean orientation) {

        // Tries placing the ship in the grid. If it is out of bounds, returns false.
        try {
            // Checks if the orientation is horizontal or vertical.
            if (orientation == true) {

                // Checks the coordinates where the ship will be placed.
                for (int i = 0; i < length; i++) {
                    if (grid[xCoord + i][yCoord].hasShip == true) {
                        return false;
                    }
                }
            } else {

                // Checks the coordinates where the ship will be placed.
                for (int j = 0; j < length; j++) {
                    if (grid[xCoord][yCoord + j].hasShip == true) {
                        return false;
                    }
                }
            }
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    /** Returns true if this cell has been shot, false if not. */
    public boolean checkShot(int xCoord, int yCoord) {
        return grid[xCoord][yCoord].isShot();
    }

    /** Places a shot: "HIT" if a ship is hit, "SUNK" if it sinks, "MISS" if it hits water. */
    public String placeShot(int xCoord, int yCoord) {

        // Marks the cell as shot.
        grid[xCoord][yCoord].markShot();

        // If the cell has a ship.
        if (grid[xCoord][yCoord].hasShip()) {

            // Gets the ship in that cell and adds 1 to its hits.
            Ship x = this.ships[grid[xCoord][yCoord].getShip()];
            x.registerHit();

            if (x.isSunk()) {
                return "SUNK";
            } else {
                return "HIT";
            }
        }
        return "MISS";
    }

    /** Returns true if all the ships are placed. */
    public boolean shipsPlaced() {

        // Checks if the array of ships is complete.
        for (Ship ship : ships) {
            if (ship == null) {
                return false;
            }
        }
        return true;
    }

    /** Returns true if all the ships are sunk. */
    public boolean shipsSunk() {

        // Checks if every ship has been sunk.
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    /** Returns the cell at (xCoord, yCoord). */
    public Cell getCell(int xCoord, int yCoord) {
        return grid[xCoord][yCoord];
    }
}
