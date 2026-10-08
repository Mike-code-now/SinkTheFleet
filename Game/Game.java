public class Game {
    Player player1;
    Player player2;
    Player currentPlayer;
    GamePhase phase;
    
    //Initilaizer
    public Game(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
        this.currentPlayer = this.player1;
    }
    
    //returns false if not all ships have been placed. if they have changes gamephase and then returns true
    public boolean finishPlacement() {
        return false;
    }
        // false if currentPlayer's grid.shipsPlaced() is false
        // player 1 done → switchTurn, player 2 places next
        // player 2 done → switchTurn back to player 1, phase = PLAYING
    
    
    // Playing phase
    public String fire(int x, int y) {
        return "Hi";
    }
        // on the opponent's grid:
        // checkShot is true → return "ALREADY_SHOT" and don't switch turn
        // otherwise placeShot and keep its result
        // opponent's shipsSunk() → phase = FINISHED, don't switch turn
        // otherwise switchTurn
        // return the result ("MISS", "HIT", "SUNK" or "ALREADY_SHOT")
    
        
        
    //GETTERS & HELPER FUNCTIONS
        
        
    public Player getCurrentPlayer() {
        return this.currentPlayer;

    }
    
    // whichever player isn't currentPlayer
    public Player getOpponent() {
        if (this.currentPlayer == this.player1) {
            return this.player2;
        } else {
            return this.player1;
        }
    }     
    
    public GamePhase getPhase() {
        return this.phase;
    }
    
    //returns null if game not over. a player if they won
    public Player isOver() {
        
        //if all player1 ships are sunk
        if (player1.getGrid().shipsSunk()) {
            return player2;
        
        } else if (player2.getGrid().shipsSunk()) {
            return player1;
        }
        
        return null;
    }
    
    private void switchTurn() {
        
        if (this.currentPlayer == this.player1) {
            this.currentPlayer = this.player2;
        
        } else {
            this.currentPlayer = this.player1;
        }
    }
}
