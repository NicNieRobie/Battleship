package battleship;

/**
 * Class representing the battleship ship class.
 */
public class Battleship extends Ship {
    /**
     * Battleship class constructor.
     */
    public Battleship() {
        // Initializing the parameters.
        this.shipSize = 4;
        this.shipClass = "battleship";
        this.healthPoints = shipSize;
    }
}
