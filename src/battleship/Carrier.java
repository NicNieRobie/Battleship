package battleship;

/**
 * Class representing the carrier ship class.
 */
public class Carrier extends Ship {
    /**
     * Carrier class constructor.
     */
    public Carrier() {
        // Initializing the parameters.
        this.shipSize = 5;
        this.shipClass = "carrier";
        this.healthPoints = shipSize;
    }
}
