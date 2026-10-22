package com.mycompany.battleships;

/** A ship with its length, position, orientation and number of hits. */
public class Ship {

    int length; // Number of cells the ship covers.
    int startCoordinateX; // Column of the first cell.
    int startCoordinateY; // Row of the first cell.
    boolean orientation; // True is horizontal, false is vertical.
    int numHits = 0; // Number of times the ship has been hit.

    // Constructor.
    public Ship(int length, int xCoor, int yCoor, boolean orient) {
        this.length = length;
        this.startCoordinateX = xCoor;
        this.startCoordinateY = yCoor;
        this.orientation = orient;
        this.numHits = 0;
    }

    /** Adds 1 to numHits. */
    public void registerHit() {
        this.numHits++;
    }

    /** Returns true if the ship is sunk, false otherwise. */
    public boolean isSunk() {
        return (numHits == length);
    }
}
