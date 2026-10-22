package com.mycompany.battleships;

/** One cell of a player's grid. */
public class Cell {

    boolean hasShip = false; // True if a ship is on this cell.
    int shipNum = 0; // Number of the ship on this cell.
    boolean isShot = false; // True if this cell has been shot.

    // Constructor.
    public Cell(boolean ship, int number) {
        this.hasShip = ship;
        this.shipNum = number;
        this.isShot = false;
    }

    /** Returns true if the cell has a ship, false otherwise. */
    public boolean hasShip() {
        return this.hasShip;
    }

    /** Sets the ship on this cell. */
    public void setShip(int num) {
        this.hasShip = true;
        this.shipNum = num;
    }

    /** Returns true if the cell is shot, false otherwise. */
    public boolean isShot() {
        return this.isShot;
    }

    /** Sets the cell to shot. */
    public void markShot() {
        this.isShot = true;
    }

    /** Returns the number of the ship on this cell. */
    public int getShip() {
        return this.shipNum;
    }
}
