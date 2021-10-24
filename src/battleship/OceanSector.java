package battleship;

import java.util.Objects;

public class OceanSector implements OceanSectorDelegate {
    Ship ship;
    SectorStatus status = SectorStatus.NOT_FIRED;

    @Override
    public void setSectorContent(Ship ship) {
        this.ship = ship;
    }

    @Override
    public void clearSectorContent() {
        this.ship = null;
    }

    @Override
    public boolean onSectorHitSunk(boolean isTorpedoHit) {
        System.out.println("  ════════════════════════════════");
        if (status == SectorStatus.FIRED_HIT || status == SectorStatus.FIRED_MISS) {
            System.out.println("  Sector was already fired at!");
            System.out.println("  ════════════════════════════════\n");
            return false;
        }

        if (isAvailable()) {
            status = SectorStatus.FIRED_MISS;
            System.out.println("  Miss!");
            System.out.println("  ════════════════════════════════\n");
            return false;
        } else {
            boolean sunkOnHit = ship.onDamageSunk(isTorpedoHit);
            System.out.println("  ════════════════════════════════\n");

            if (ship.isSunk()) {
                status = SectorStatus.SUNK;
            } else {
                status = SectorStatus.FIRED_HIT;
            }
            return sunkOnHit;
        }
    }

    @Override
    public boolean isAvailable() {
        return Objects.isNull(ship);
    }

    @Override
    public Character getSectorRepresentation() {
        if (!Objects.isNull(ship) && ship.isSunk()) {
            status = SectorStatus.SUNK;
        }

        return switch (status) {
            case NOT_FIRED -> '~';
            case FIRED_MISS -> '.';
            case FIRED_HIT -> 'X';
            case SUNK -> '+';
        };
    }
}
