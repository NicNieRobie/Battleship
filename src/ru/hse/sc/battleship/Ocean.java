package ru.hse.sc.battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Ocean implements OceanDelegate {
    OceanSectorDelegate[][] sectors;

    public Ocean(final int rows, final int cols) {
        sectors = new OceanSectorDelegate[rows][cols];

        for (int i = 0; i < sectors.length; i++) {
            for (int j = 0; j < sectors[i].length; j++) {
                sectors[i][j] = new OceanSector();
            }
        }
    }

    private boolean tryToPlaceShipRandomly(final Ship ship, List<Integer[]> possibleBowPositionsRef) {
        if (possibleBowPositionsRef.size() == 0) {
            return false;
        }

        List<Integer[]> possibleBowPositions = new ArrayList<Integer[]>(possibleBowPositionsRef);

        while (possibleBowPositions.size() != 0) {
            int posIndex = ThreadLocalRandom.current().nextInt(0, possibleBowPositions.size() - 1);
            int direction = ThreadLocalRandom.current().nextInt(0, 1);

            int row = possibleBowPositions.get(posIndex)[0];
            int col = possibleBowPositions.get(posIndex)[1];

            boolean couldPlace = tryToPlaceShipInLoc(ship, new Integer[]{row, col}, direction == 0);

            if (!couldPlace) {
                possibleBowPositions.remove(posIndex);
            } else {
                possibleBowPositionsRef.remove(posIndex);
                return true;
            }
        }

        return false;
    }

    private boolean tryToPlaceShipInLoc(Ship ship, Integer[] coords, Boolean isHorizontal) {
        int row = coords[0];
        int col = coords[1];

        if (isHorizontal) {
            if (col + ship.shipSize > sectors[0].length) {
                return false;
            }

            int topLocBound = row == 0 ? row : row - 1;
            int bottomLocBound = row == sectors.length - 1 ? row : row + 1;
            int leftLocBound = col == 0 ? col : col - 1;
            int rightLocBound = col + ship.shipSize == sectors[0].length - 1 ?
                    col + ship.shipSize : col + ship.shipSize + 1;

            for (int i = topLocBound; i < bottomLocBound; i++) {
                for (int j = leftLocBound; j < rightLocBound; j++) {
                    if (!sectors[i][j].isAvailable()) {
                        return false;
                    }
                }
            }

            for (int i = col; i < col + ship.shipSize; i++) {
                sectors[row][i].setSectorContent(ship);
            }
            return true;
        } else {
            if (row + ship.shipSize > sectors.length) {
                return false;
            }

            int topLocBound = row == 0 ? row : row - 1;
            int bottomLocBound = row + ship.shipSize == sectors.length - 1 ?
                    row + ship.shipSize : row + ship.shipSize + 1;
            int leftLocBound = col == 0 ? col : col - 1;
            int rightLocBound = col == sectors[0].length - 1 ? col : col + 1;

            for (int i = topLocBound; i < bottomLocBound; i++) {
                for (int j = leftLocBound; j < rightLocBound; j++) {
                    if (!sectors[i][j].isAvailable()) {
                        return false;
                    }
                }
            }

            for (int i = row; i < row + ship.shipSize; i++) {
                sectors[i][col].setSectorContent(ship);
            }
            return true;
        }
    }

    @Override
    public void print() {
        for (int i = 0; i < sectors.length; i++) {
            for (int j = 0; j < sectors[i].length; j++) {
                System.out.print(sectors[i][j].getSectorRepresentation());
            }
            System.out.print('\n');
        }
    }

    @Override
    public OceanSectorDelegate getOceanSector(int row, int col) {
        if (row < sectors.length && col < sectors[0].length) {
            return sectors[row][col];
        } else {
            throw new IllegalArgumentException("Wrong coordinate!");
        }
    }

    @Override
    public boolean onHitSunk(int row, int col) {
        if (row < sectors.length && col < sectors[0].length) {
            if (!sectors[row][col].isAvailable()) {
                System.out.println("Hit!");
                return sectors[row][col].onSectorHitSunk(false);
            } else {
                System.out.println("Miss!");
                return false;
            }
        } else {
            throw new IllegalArgumentException("Wrong coordinate!");
        }
    }

    public static Ocean tryToInitOcean(final Integer[] oceanSizes, final Integer[] shipSettings) {
        Integer rows = oceanSizes[0];
        Integer cols = oceanSizes[1];

        Ocean ocean = new Ocean(rows, cols);
        Boolean[][] oceanMap = new Boolean[rows][cols];
        List<Integer[]> possibleBowPositions = new ArrayList<Integer[]>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                possibleBowPositions.add(new Integer[] {i, j});
            }
        }

        for (int i = 0; i < shipSettings.length; i++) {
            Ship ship = switch (i) {
                default -> new Submarine();
                case 1 -> new Destroyer();
                case 2 -> new Cruiser();
                case 3 -> new Battleship();
                case 4 -> new Carrier();
            };

            if (!ocean.tryToPlaceShipRandomly(ship, possibleBowPositions)) {
                return null;
            }
        }

        return ocean;
    }
}
