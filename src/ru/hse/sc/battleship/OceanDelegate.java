package ru.hse.sc.battleship;

import java.util.List;

public interface OceanDelegate {
    OceanSectorDelegate getOceanSector(int row, int col);
    boolean onHitSunk(int row, int col);
    void print();
}