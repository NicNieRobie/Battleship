package battleship;

/**
 * Class representing the destroyer ship class.
 */
public class Destroyer extends Ship {
    /**
     * Destroyer class constructor.
     */
    public Destroyer() {
        // Initializing the parameters.
        this.shipSize = 2;
        this.shipClass = "destroyer";
        this.healthPoints = shipSize;
    }
}
