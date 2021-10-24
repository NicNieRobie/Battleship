package battleship;

public interface OceanDelegate {
    OceanSectorDelegate getOceanSector(int row, int col);
    boolean onHitSunk(int row, int col, boolean isTorpedoShot);
    void showAllMap();
    void print();
    Integer[] getSize();
}