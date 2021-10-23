package ru.hse.sc.battleship;

import java.util.Objects;

public class OceanSector implements OceanSectorDelegate {
    Ship ship;
    SectorStatus status = SectorStatus.NOT_FIRED;

    @Override
    public void setSectorContent(Ship ship) {
        this.ship = ship;
    }

    @Override
    public boolean onSectorHitSunk(boolean isTorpedoHit) {
        if (isAvailable()) {
            status = SectorStatus.FIRED_MISS;
            return false;
        } else {
            if (status == SectorStatus.FIRED_HIT) {
                System.out.println("Sector was already fired at!");
                return false;
            }

            boolean sunkOnHit = ship.onDamageSunk(isTorpedoHit);
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
        return switch (status) {
            case NOT_FIRED -> '~';
            case FIRED_MISS -> '.';
            case FIRED_HIT -> 'X';
            case SUNK -> '+';
        };
    }
}
