package battleship;

/**
 * Game ocean interface.
 *
 * Represents the game's map and provides
 * methods for sector hit event processing.
 */
public interface OceanDelegate {
    /**
     * Gets the sector on given coordinates.
     *
     * @param row row coordinate of the sector.
     * @param col column coordinate of the sector.
     *
     * @return the sector on given coordinates.
     */
    OceanSectorDelegate getOceanSector(int row, int col);

    /**
     * Checks if the sector on given coordinates
     * was previously hit.
     *
     * @param row row coordinate of the sector.
     * @param col column coordinate of the sector.
     *
     * @return boolean - true, if the sector was hit.
     */
    boolean sectorIsHit(int row, int col);

    /**
     * Checks if ship in the sector on given
     * coordinates was sunk by the hit.
     *
     * @param row row coordinate of the sector.
     * @param col column coordinate of the sector.
     * @param isTorpedoHit flag of damage being dealt by a torpedo.
     * @param recoveryModeOn flag of recovery mode being on.
     * @param shipLastHit ship that was hit last time.
     *
     * @return boolean - true, if the ship was sunk.
     */
    boolean onHitSunk(int row, int col, boolean isTorpedoHit, boolean recoveryModeOn, Ship shipLastHit);

    /**
     * Prints the ocean representation to console.
     */
    void print();

    /**
     * Gets the size of the ocean.
     *
     * @return array of integers - amount of rows,
     * amount of columns
     */
    int[] getSize();

    /**
     * "Hits" the whole map to show each sector's content.
     */
    void discoverAllMap();
}