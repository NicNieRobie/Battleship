package battleship;

public class Battleship extends Ship {
    public Battleship() {
        this.shipSize = 4;
        this.shipClass = "battleship";
        this.healthPoints = shipSize;
    }
}
