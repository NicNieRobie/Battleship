package battleship;

public class Carrier extends Ship {
    public Carrier() {
        this.shipSize = 5;
        this.shipClass = "carrier";
        this.healthPoints = shipSize;
    }
}
