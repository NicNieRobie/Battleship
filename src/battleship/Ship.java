package battleship;

/**
 * Base abstract class for the ship object logic.
 */
public abstract class Ship {
    // Ship's class.
    protected String shipClass;
    // Ship's size.
    protected int shipSize;
    // Ship's HP.
    protected int healthPoints;

    /**
     * Checks if the ship is sunk.
     *
     * @return boolean - true, if the ship is sunk.
     */
    public boolean isSunk() {
        return healthPoints == 0;
    }

    /**
     * Processes the event of ship being hit and checks
     * if the hit sunk the ship.
     *
     * @param isTorpedoDamage flag of the damage being dealt
     *                        by a torpedo
     *
     * @return boolean - true, if the ship has been sunk
     */
    public boolean onDamageSunk(boolean isTorpedoDamage) {
        // Checking if the ship had already been sunk.
        if (isSunk()) {
            System.out.println("    Ship already sunk!");
            return false;
        }

        // Calculating the damage.
        healthPoints -= isTorpedoDamage ? healthPoints : 1;

        // If ship has been sunk.
        if (healthPoints == 0) {
            System.out.println("    You just have sunk a " + shipClass + ".");
            return true;
        }

        // If ship has not been sunk.
        System.out.println("    Hit!");
        return false;
    }

    // Recovers the ship's HP.
    public void recover() {
        healthPoints = shipSize;
    }

    // Checks if damage was dealt to the ship.
    public boolean isHit() {
        return healthPoints != shipSize;
    }
}
