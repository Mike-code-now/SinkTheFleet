public class Ship {
    int length;
    int startCoordinateX;
    int startCoordinateY;
    boolean orientation; //true is horizontal, false is vertical
    int numHits = 0;
    
    //Initialzer
    public Ship(int length, int xCoor, int yCoor, boolean orient) {
        this.length = length;
        this.startCoordinateX = xCoor;
        this.startCoordinateY = yCoor;
        this.orientation = orient;
        this.numHits = 0;
    }
    
    //Adds 1 to numHits
    public void registerHit() {
        this.numHits++;
    }
    
    
    //returns true if is sunk, false otherwise
    public boolean isSunk() {
        return (numHits == length);
    }
    
    //returns the coordinates of all the cells where the ship is
    public int[][] getPositions() {
        ...
    }
}
