package battleship;

/**
 * Game ocean sector interface.
 *
 * Represents the game map's sector and provides methods
 * to operate with its content (ship located in the sector)
 * and to process the event of a sector being hit.
 */
public interface OceanSectorDelegate {
    /**
     * Enum that represents the sector's status on the map.
     */
    enum SectorStatus {
        NOT_FIRED,
        FIRED_MISS,
        FIRED_HIT,
        SUNK
    }

    /**
     * Sets the reference to ship located in the sector.
     *
     * @param ship ship located in the sector.
     */
    void setSectorContent(Ship ship);

    /**
     * Gets the reference to ship located in the sector.
     *
     * @return ship located in the sector.
     */
    Ship getSectorContent();

    /**
     * Checks if ship in the sector has been sunk by the hit
     * with given parameters and prints the hit result.
     *
     * @param isTorpedoHit torpedo mode flag.
     * @param recoveryModeOn recovery mode flag.
     * @param shipLastHit ship that was hit the last time.
     *
     * @return boolean - true, if the ship has been sunk.
     */
    boolean onSectorHitSunk(boolean isTorpedoHit, boolean recoveryModeOn, Ship shipLastHit);

    /**
     * Checks if the sector is available (no ship is located
     * in the sector).
     *
     * @return boolean - true, if the sector is available.
     */
    boolean isAvailable();

    /**
     * Checks if the sector was previously hit.
     *
     * @return boolean - true, if the sector was previously hit.
     */
    boolean isHit();

    /**
     * Gets the sector's char representation according
     * to its status.
     *
     * @return sector's char representation.
     */
    Character getSectorRepresentation();
}