package ru.hse.sc.battleship;

public interface OceanSectorDelegate {
    enum SectorStatus {
        NOT_FIRED,
        FIRED_MISS,
        FIRED_HIT,
        SUNK
    }

    void setSectorContent(Ship ship);
    void onSectorHit(boolean isTorpedoHit);
    boolean isAvailable();
    Character getSectorRepresentation();
}