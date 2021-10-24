package battleship;

/**
 * Class representing the cruiser ship class.
 */
public class Cruiser extends Ship {
    /**
     * Cruiser class constructor.
     */
    public Cruiser() {
        // Initializing the parameters.
        this.shipSize = 3;
        this.shipClass = "cruiser";
        this.healthPoints = shipSize;
    }
}
