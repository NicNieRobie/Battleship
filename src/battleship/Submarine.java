package battleship;

/**
 * Class representing the submarine ship class.
 */
public class Submarine extends Ship {
    /**
     * Submarine class constructor.
     */
    public Submarine() {
        // Initializing the parameters.
        this.shipSize = 1;
        this.shipClass = "submarine";
        this.healthPoints = shipSize;
    }
}
