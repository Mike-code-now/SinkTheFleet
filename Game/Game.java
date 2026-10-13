public class Game {
    Player player1;
    Player player2;
    Player currentPlayer;
    GamePhase phase;
    boolean hasFired = false;
    
    //Initilaizer
    public Game(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
        this.currentPlayer = player1;
        this.phase = GamePhase.SETUP;
    }

    //Setup phase
    //returns false if not all ships have been placed. if they have changes gamephase and then returns true
    public boolean finishPlacement() {

        //if player has placed its ships switches turn and if both players have placed changes phase to PLAYING
        if (currentPlayer.getGrid().shipsPlaced()) {
            if (currentPlayer == player2) {
                phase =  GamePhase.PLAYING;
            } else {
                switchTurn();
            }
            
            
            return true;
        }
        return false;
    }
    
    
    // Playing phase
    // returns "MISS", "HIT", "HIT & SUNK", "ALREADY_SHOT"
    public String fire(int x, int y) {
        Grid grid = getOpponent().getGrid();

        if (grid.checkShot(x, y) == true) {
            return "ALREADY SHOT";
        }
        
        //if player already fired
        if (this.hasFired) {
            return "ALREADY FIRED";
        }
        
        String shot = grid.placeShot(x, y);

        //changes phase to FINISHED if all the enemy's ships have been sunk
        if (grid.shipsSunk()) {
            phase =  GamePhase.FINISHED;
        }
        
        boolean hasFired = true;
        
        return shot;
        
        
        
    }
    
        
        
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
    
    //returns "" if game not over or the player's name if they won
    public String isOver() {
        
        //if all player1 ships are sunk
        if (player1.getGrid().shipsSunk()) {
            return player2.getName();
        
        } else if (player2.getGrid().shipsSunk()) {
            return player1.getName();
        }
        
        return "";
    }
    //switches turn
    private void switchTurn() {
        
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
        
        boolean hasFired = false;
    }
}
