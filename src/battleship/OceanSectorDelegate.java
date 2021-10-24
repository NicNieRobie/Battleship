package battleship;

public interface OceanSectorDelegate {
    enum SectorStatus {
        NOT_FIRED,
        FIRED_MISS,
        FIRED_HIT,
        SUNK
    }

    void setSectorContent(Ship ship);
    void clearSectorContent();
    boolean onSectorHitSunk(boolean isTorpedoHit);
    boolean isAvailable();
    Character getSectorRepresentation();
}