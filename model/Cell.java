
public class Cell {
    boolean hasShip = false;
    int shipNum = 0;
    boolean IsShot = false  ;
    
    
    //Initializer
    public Cell(boolean ship, int number) {
        pass
    }
    
    //returns true if cell has ship, false otherwise
    public boolean hasShip() {
        return false;
    }
    
    //sets ship to cell. True if worked, false otherwise
    public boolean setShip(int xCoordinate, int yCoordinate) {
        return false;
    }
    
    //returns true if cell is shot. False otherwise
    public boolean isShot(int xCoordinate, int yCoordinate) {
        return false;
    }
    
    //returns true if succeeded, false otherwise
    public boolean markShot(int xCoordinate, int yCoordinate) {
        return false;
    }
}