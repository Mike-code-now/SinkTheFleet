public class Grid {
    int player;
    Ship [] ships;
    Cell [] grid;
    
    //initialize grid with player 
    public Grid(int player, Ship [] ships) {
        this.ships = ships;
        this.player = player;
        
        //the grid is an array divided first in the columns and inside divided in each cell in a single column aand diferent row
        grid = new Cell[10][10];
        
        //creates each cell in the grid with the starting values
        for(int i =  0; i < 10; i++) {
            for(int j =  0; j < 10; j++){
                Cell x = new Cell(false, 0);
                grid[i][j] = x;
            }
        }
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
    public boolean placeShip(int length, int xCoord, int yCoord, boolean orientation, int shipNum) {
        
        //Creates a new Ship
        Ship x = new Ship(length, xCoord, yCoord, orientation);

        //checks if orientation is horizontal or vertical
        if (orientation == true) {

            //places the ship in its coordinates
            for(int i = 0; i < length; i++) {
                grid[xCoord + i][yCoord].setShip(shipNum);
            }
        } else {

            //places the ship in its coordinates
            for(int j = 0; j < length; j++) {
                grid[xCoord][yCoord + j].setShip(shipNum);
            }
        }
        return true;
    }
    
    //checks if ship can be placed, returns true if able false if not
    public boolean canPlaceShip(int length, int xCoord, int yCoord, boolean orientation) {
        //if out of bounds then also not valid place to put ship
        try {
            //checks if orientation is horizontal or vertical
            if (orientation == true) {

                //checks the coordinates where the ship will be placed
                for(int i = 0; i < length; i++) {
                    if (grid[xCoord + i][yCoord].hasShip == true) {
                        return false;
                    }
                }
            } else {

                //checks the coordinates where the ship will be placed
                for(int j = 0; j < length; j++) {
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
    
    //places shot, returns true if a boat is shoted or false if not
    public boolean placeShot(int xCoord, int yCoord) {
        
    }
    
    //Checks if all the ships are placed through object ships
    public boolean shipsPlaced() {
        
    }
    
    //Checks if all the ships are sunk through object ships
    public boolean shipsSunk() {
        
    }
    
    
}
