package battleship;

import java.util.Objects;

/**
 * Class that represents default Battleship ocean sector.
 */
public class OceanSector implements OceanSectorDelegate {
    // Ship that is located in the sector.
    Ship ship;
    // Current sector status.
    SectorStatus status = SectorStatus.NOT_FIRED;

    /**
     * Sets the reference to ship located in the sector.
     *
     * @param ship ship located in the sector.
     */
    @Override
    public void setSectorContent(Ship ship) {
        this.ship = ship;
    }

    /**
     * Gets the reference to ship located in the sector.
     *
     * @return ship located in the sector.
     */
    @Override
    public Ship getSectorContent() {
        return ship;
    }

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
    @Override
    public boolean onSectorHitSunk(boolean isTorpedoHit, boolean recoveryModeOn, Ship shipLastHit) {
        System.out.println("  ══════════════════════════════════");

        // If sector was previously hit.
        if (isHit()) {
            System.out.println("    Sector was already fired at!");
            System.out.println("  ══════════════════════════════════\n");
            return false;
        }

        // If the sector is empty.
        if (isAvailable()) {
            status = SectorStatus.FIRED_MISS;
            System.out.println("    Miss!");
            System.out.println("  ══════════════════════════════════\n");

            // Recovering the previously hit ship.
            if (recoveryModeOn) {
                if (shipLastHit != ship && !Objects.isNull(shipLastHit)) {
                    if (!shipLastHit.isSunk()) {
                        shipLastHit.recover();
                    }
                }
            }

            return false;
        } else {
            // Recovering the previously hit ship.
            if (recoveryModeOn) {
                if (shipLastHit != ship && !Objects.isNull(shipLastHit)) {
                    if (!shipLastHit.isSunk()) {
                        shipLastHit.recover();
                    }
                }
            }

            // Getting and printing the hit result.
            boolean sunkOnHit = ship.onDamageSunk(isTorpedoHit);
            System.out.println("  ══════════════════════════════════\n");

            // Changing the status.
            if (ship.isSunk()) {
                status = SectorStatus.SUNK;
            } else {
                status = SectorStatus.FIRED_HIT;
            }
            return sunkOnHit;
        }
    }

    /**
     * Checks if the sector is available (no ship is located
     * in the sector).
     *
     * @return boolean - true, if the sector is available.
     */
    @Override
    public boolean isAvailable() {
        return Objects.isNull(ship);
    }

    /**
     * Gets the sector's char representation according
     * to its status.
     *
     * @return sector's char representation.
     */
    @Override
    public Character getSectorRepresentation() {
        // Updating the status.
        if (!Objects.isNull(ship)) {
            if (ship.isSunk()) {
                status = SectorStatus.SUNK;
            } else if (!ship.isHit()) {
                status = SectorStatus.NOT_FIRED;
            }
        }

        return switch (status) {
            case NOT_FIRED -> '~';
            case FIRED_MISS -> '.';
            case FIRED_HIT -> 'X';
            case SUNK -> '+';
        };
    }

    /**
     * Checks if the sector was previously hit.
     *
     * @return boolean - true, if the sector was previously hit.
     */
    @Override
    public boolean isHit() {
        return status == SectorStatus.FIRED_HIT || status == SectorStatus.FIRED_MISS || status == SectorStatus.SUNK;
    }
}
