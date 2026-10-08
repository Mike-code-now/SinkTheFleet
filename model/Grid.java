public class Grid {
    Player player;
    
    //empty array of ships
    Ship[] ships = new Ship[5];
    
    //the grid is an array divided first in the columns and inside divided in each cell in a single column aand diferent row
    Cell [][] grid = new Cell[10][10];
    
    //initialize grid with player 
    public Grid(Player player) {
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
    
    public Ship[] getShips() {
        return this.ships;
    }
    
    public Cell[][] getCells() {
        return this.grid;
    }
    
    //places ship and creates the Ship into object ships
    public boolean placeShip(int length, int xCoord, int yCoord, boolean orientation, int shipNum) {
        
        //Creates a new Ship
        Ship x = new Ship(length, xCoord, yCoord, orientation);
        
        ships[shipNum] = x;

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
        
        //tries placing ship in the grid, if the placement is out of bounds retuns false
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

    //returns false if this cell hasnt been shot yet, true if it has
    public boolean checkShot(int xCoord, int yCoord) {
        return grid[xCoord][yCoord].isShot();
    }
        
    //places shot, "HIT" if ship hit but not sunk, "SUNK" if sunk, "MISS" if hit water
    public String placeShot(int xCoord, int yCoord) {
        
        //marks cell as shot
        grid[xCoord][yCoord].markShot();

        //if the cell has a ship
        if (grid[xCoord][yCoord].hasShip()) {
            
            //gets the ship in that cell and adds 1 to hit.
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
        
    
    //Checks if all the ships are placed through object ships
    public boolean shipsPlaced() {
        
        //checks if list of ships has been completed
        for (Ship ship: ships) {
            if (ship == null) {
                return false;
            }
        }
        return true;
    }
    
    //Checks if all the ships are sunk through object ships
    public boolean shipsSunk() {
        
        //checks if every ship has been sunked
        for (Ship ship: ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }
    
    public Cell getCell(int xCoord, int yCoord) {
        return grid[xCoord][yCoord];
    }
    
}
