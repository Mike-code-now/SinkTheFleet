
public class Cell {
    boolean hasShip = false;
    int shipNum = 0;
    boolean isShot = false;
    
    
    //Initializer
    public Cell(boolean ship, int number) {
        this.hasShip = ship;
        this.shipNum = number;
        this.isShot = false;
    }
    
    //returns true if cell has ship, false otherwise
    public boolean hasShip() {
        return this.hasShip;
    }
    
    //sets ship to cell
    public void setShip(int num) {
        this.hasShip = true;
        this.shipNum = num;
    }
    
    //returns true if cell is shot. False otherwise
    public boolean isShot() {
        return this.isShot;
    }
    
    //Sets a cell to shot if it has been shot
    public void markShot() {
        this.isShot = true;
    }
}
