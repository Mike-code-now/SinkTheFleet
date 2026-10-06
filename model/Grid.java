public class Grid {
    int player;
    Ship [] ships;
    Cell [] cells;
    
    //initialize grid with player 
    public Grid(int player) {
        
    }
    
    public int getPlayer() {
        return this.player;
    }
    
    public Ship getShips() {
        return this.ships;
    }
    
    public Cell getCells() {
        return this.cells;
    }
    
    //places ship and creates the Ship into object ships
    public boolean placeShip(int xCoord, int yCoord, boolean orientation) {
        
    }
    
    //checks if ship can be placed, true if able false if not
    public boolean canPlaceShip(int xCoord, int yCoord, boolean orientation) {
        
    }
    
    //places shot and return true if a boat is shoted or false if not
    public boolean placeShot(int xCoord, int yCoord) {
        
    }
    
    //Checks if all the ships are placed through object ships
    public boolean shipsPlaced() {
        
    }
    
    //Checks if all the ships are sunk through object ships
    public boolean shipsSunk() {
        
    }
    
    
}
