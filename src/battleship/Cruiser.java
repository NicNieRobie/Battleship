package battleship;

public class Cruiser extends Ship {
    public Cruiser() {
        this.shipSize = 3;
        this.shipClass = "cruiser";
        this.healthPoints = shipSize;
    }
}
