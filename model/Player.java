package com.mycompany.battleships;

/** A player with a name and their own grid. */
public class Player {

    String name; // Name of the player.
    Grid grid; // The player's own grid.

    // Constructor.
    public Player(String name) {
        this.name = name;
        this.grid = new Grid(this);
    }

    /** Returns the name of the player. */
    public String getName() {
        return this.name;
    }

    /** Returns the grid of the player. */
    public Grid getGrid() {
        return this.grid;
    }
}
