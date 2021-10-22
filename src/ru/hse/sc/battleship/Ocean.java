package ru.hse.sc.battleship;

import java.util.List;

public class Ocean implements OceanDelegate {
    OceanSectorDelegate[][] sectors;

    @Override
    public boolean tryToPlaceShipRandomly() {
        return false;
    }

    @Override
    public boolean tryToInitOcean() {
        return false;
    }
}
