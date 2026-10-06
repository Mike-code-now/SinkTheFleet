public class Player {
    String name;
    Grid grid;
    
    //initilizer
    public Player(String name) {
        this.name = name;
        this.grid = new Grid(this);
    }
    
    //returns the name of the player
    public String getName() {
        return this.name;
    }
    
    //returns the gris object of that player
    public Grid getGrid() {
        return this.grid;
    }  
}
