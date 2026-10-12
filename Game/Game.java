public class Game {
    Player player1;
    Player player2;
    Player currentPlayer;
    Player enemyPlayer;
    GamePhase phase;
    
    //Initilaizer
    public Game(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
        this.currentPlayer = player1;
        this.enemyPlayer = player2;
    }

    //Setup phase
    //returns false if not all ships have been placed. if they have changes gamephase and then returns true
    public boolean finishPlacement(Grid grid) {

        //if player has placed its ships switches turn and if both players have placed changes phase to PLAYING
        if (currentPlayer.shipsPlaced()) {
            if (currentPlayer == player1) {
                currentPlayer = player2;
                enemyPlayer = player1;
            } else {
                currentPlayer = player1;
                enemyPlayer = player2;
                phase = PLAYING;
            }
            return true;
        }
        return false;
    }
    
    
    // Playing phase
    // returns "MISS", "HIT", "SUNK", "ALREADY_SHOT"
    public String fire(int x, int y) {
        Grid grid = enemyPlayer.getGrid();

        if (grid.checkShot(x, y) == true) {
            return "ALREADY SHOT";
        }
        
        String shot = grid.placeShot(x, y);

        //changes phase to FINISHED if all the enemy's ships have been sunk
        if (grid.shipsSunk()) {
            phase = FINISHED;
        }

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
    
    private void switchTurn() {
        
        if (this.currentPlayer == this.player1) {
            this.currentPlayer = this.player2;
        
        } else {
            this.currentPlayer = this.player1;
        }
    }
}
