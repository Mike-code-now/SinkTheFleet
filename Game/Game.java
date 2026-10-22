package com.mycompany.battleships;

/** The game state: the players, whose turn it is and the game phase. */
public class Game {

    Player player1; // First player.
    Player player2; // Second player.
    Player currentPlayer; // Player whose turn it is.
    GamePhase phase; // Current phase of the game.
    boolean hasFired = false; // True once the current player has missed this turn.

    // Constructor.
    public Game(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
        this.currentPlayer = player1;
        this.phase = GamePhase.SETUP;
    }

    // Setup phase

    /** Returns false if not all ships are placed, otherwise switches turn and returns true. */
    public boolean finishPlacement() {

        // If the player has placed all their ships, switches turn.
        if (currentPlayer.getGrid().shipsPlaced()) {

            // If both players have placed their ships, changes the phase to PLAYING.
            if (currentPlayer == player2) {
                phase = GamePhase.PLAYING;
            }

            switchTurn();
            return true;
        }
        return false;
    }

    // Playing phase

    /** Fires at (x, y). Returns "MISS", "HIT", "SUNK", "ALREADY SHOT" or "ALREADY FIRED". */
    public String fire(int x, int y) {
        Grid grid = getOpponent().getGrid();

        if (grid.checkShot(x, y) == true) {
            return "ALREADY SHOT";
        }

        // If the player already fired.
        if (this.hasFired) {
            return "ALREADY FIRED";
        }

        String shot = grid.placeShot(x, y);

        // Changes the phase to FINISHED if all the enemy's ships have been sunk.
        if (grid.shipsSunk()) {
            phase = GamePhase.FINISHED;
        }

        // Only a miss ends the turn; after a hit the player may fire again.
        if (shot.equals("MISS")) {
            this.hasFired = true;
        }

        return shot;
    }

    // Getters and helper methods

    /** Returns the player whose turn it is. */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /** Returns whichever player isn't the current player. */
    public Player getOpponent() {
        if (this.currentPlayer == this.player1) {
            return this.player2;
        } else {
            return this.player1;
        }
    }

    /** Returns the current game phase. */
    public GamePhase getPhase() {
        return this.phase;
    }

    /** Returns "" if the game isn't over, or the winner's name if it is. */
    public String isOver() {

        // If all of player 1's ships are sunk, player 2 wins.
        if (player1.getGrid().shipsSunk()) {
            return player2.getName();

        } else if (player2.getGrid().shipsSunk()) {
            return player1.getName();
        }

        return "";
    }

    /** Switches turn and resets hasFired. */
    public void switchTurn() {

        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }

        this.hasFired = false;
    }

    /** Checks if the ship can be placed in the player's grid. */
    public boolean canPlaceShipInGrid(int length, int x, int y, boolean orientation,
            Player player) {
        return player.getGrid().canPlaceShip(length, x, y, orientation);
    }

    /** Places the ship in the player's grid. */
    public boolean placeShipInGrid(int length, int x, int y, boolean orientation, int shipNum,
            Player player) {
        return player.getGrid().placeShip(length, x, y, orientation, shipNum);
    }
}
