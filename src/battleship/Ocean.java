package battleship;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
            int posIndex = possibleBowPositions.size() == 1 ? 0 :
                    ThreadLocalRandom.current().nextInt(0, possibleBowPositions.size() - 1);
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
            int rightLocBound = col + ship.shipSize == sectors[0].length ?
                    col + ship.shipSize - 1 : col + ship.shipSize;

            for (int i = topLocBound; i < bottomLocBound + 1; i++) {
                for (int j = leftLocBound; j < rightLocBound + 1; j++) {
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
            int bottomLocBound = row + ship.shipSize == sectors.length ?
                    row + ship.shipSize - 1 : row + ship.shipSize;
            int leftLocBound = col == 0 ? col : col - 1;
            int rightLocBound = col == sectors[0].length - 1 ? col : col + 1;

            for (int i = topLocBound; i < bottomLocBound + 1; i++) {
                for (int j = leftLocBound; j < rightLocBound + 1; j++) {
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

    private String canPlaceShipInLoc(Ship ship, Integer[] coords) {
        int row = coords[0];
        int col = coords[1];

        boolean isHorizontal = ThreadLocalRandom.current().nextBoolean();
        int checkCount = 0;

        while (checkCount != 2) {
            if (isHorizontal) {
                if (col + ship.shipSize >= sectors[0].length) {
                    checkCount++;
                    isHorizontal = false;
                    continue;
                }

                int topLocBound = row == 0 ? row : row - 1;
                int bottomLocBound = row == sectors.length - 1 ? row : row + 1;
                int leftLocBound = col == 0 ? col : col - 1;
                int rightLocBound = col + ship.shipSize == sectors[0].length ?
                        col + ship.shipSize - 1 : col + ship.shipSize;

                boolean posIsWrong = false;
                for (int i = topLocBound; i < bottomLocBound + 1; i++) {
                    for (int j = leftLocBound; j < rightLocBound + 1; j++) {
                        if (!sectors[i][j].isAvailable()) {
                            checkCount++;
                            isHorizontal = false;
                            posIsWrong = true;
                            break;
                        }
                    }

                    if (posIsWrong) {
                        break;
                    }
                }

                if (posIsWrong) {
                    continue;
                }

                return "horizontal";
            } else {
                if (row + ship.shipSize >= sectors.length) {
                    checkCount++;
                    isHorizontal = true;
                    continue;
                }

                int topLocBound = row == 0 ? row : row - 1;
                int bottomLocBound = row + ship.shipSize == sectors.length ?
                        row + ship.shipSize - 1 : row + ship.shipSize;
                int leftLocBound = col == 0 ? col : col - 1;
                int rightLocBound = col == sectors[0].length - 1 ? col : col + 1;

                boolean posIsWrong = false;
                for (int i = topLocBound; i < bottomLocBound + 1; i++) {
                    for (int j = leftLocBound; j < rightLocBound + 1; j++) {
                        if (!sectors[i][j].isAvailable()) {
                            checkCount++;
                            isHorizontal = true;
                            posIsWrong = true;
                            break;
                        }
                    }

                    if (posIsWrong) {
                        break;
                    }
                }

                if (posIsWrong) {
                    continue;
                }

                return "vertical";
            }
        }

        return "none";
    }

    @Override
    public void print() {
        String topBorder = "┌" + String.join("", Collections.nCopies(2 * sectors[0].length + 1, "─")) + "┐";
        String interBorder = "├" + String.join("", Collections.nCopies(2 * sectors[0].length + 1, "─")) + "┤";
        String bottomBorder = "└" + String.join("", Collections.nCopies(2 * sectors[0].length + 1, "─")) + "┘";

        System.out.print("    ");
        for (int i = 0; i < sectors[0].length; i++) {
            System.out.print(i + 1);
            System.out.print(" ");
        }

        System.out.print('\n');
        System.out.print("  " + topBorder);
        System.out.print('\n');

        for (int i = 0; i < sectors.length; i++) {
            Character rowChar = (char)(65 + i);
            System.out.print(rowChar);
            System.out.print(" │ ");
            for (int j = 0; j < sectors[i].length; j++) {
                System.out.print(sectors[i][j].getSectorRepresentation());
                System.out.print(" ");
            }
            System.out.print("│");
            System.out.print('\n');
        }

        int legendWidth = Math.max(2 * sectors[0].length + 1, 32);

        if (legendWidth < 2 * sectors[0].length + 1) {
            String newBottomBorder = "├" + String.join("", Collections.nCopies(32, "─")) + "┴" +
                    String.join("", Collections.nCopies(2 * sectors[0].length - 32, "─")) + "┘";
            System.out.println("  " + newBottomBorder);
        } else if (legendWidth > 2 * sectors[0].length + 1) {
            String newBottomBorder = "├" + String.join("", Collections.nCopies(2 * sectors[0].length + 1, "─")) + "┴" +
                    String.join("", Collections.nCopies(32 - 2 * sectors[0].length - 2, "─")) + "┐";
            System.out.println("  " + newBottomBorder);
        } else {
            System.out.println("  " + interBorder);
        }

        printLegend(legendWidth);
        System.out.print('\n');
    }

    private void printLegend(int contentWidth) {
        if (contentWidth < 32) {
            contentWidth = 32;
        }

        System.out.println("  │" + String.join("", Collections.nCopies(contentWidth / 2 - 3, " ")) +
                "LEGEND" + String.join("", Collections.nCopies(contentWidth / 2 + contentWidth % 2 - 3, " ")) + "│");
        System.out.println("  │ ~ - unknown (not yet fired at) " +
                String.join("", Collections.nCopies(contentWidth - 32, " ")) + "│");
        System.out.println("  │ . - empty " +
                String.join("", Collections.nCopies(contentWidth - 11, " ")) + "│");
        System.out.println("  │ X - enemy ship fired at " +
                String.join("", Collections.nCopies(contentWidth - 25, " ")) + "│");
        System.out.println("  │ + - enemy ship destroyed " +
                String.join("", Collections.nCopies(contentWidth - 26, " ")) + "│");
        System.out.println("  └" + String.join("", Collections.nCopies(contentWidth, "─")) + "┘");
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
    public boolean onHitSunk(int row, int col, boolean isTorpedoShot) {
        if (row < sectors.length && col < sectors[0].length) {
            return sectors[row][col].onSectorHitSunk(isTorpedoShot);
        } else {
            throw new IllegalArgumentException("Wrong coordinate!");
        }
    }

    public static Ocean tryToInitOcean(final Integer[] oceanSizes, final Integer[] shipSettings) {
        Integer rows = oceanSizes[0];
        Integer cols = oceanSizes[1];

        Ocean ocean = new Ocean(rows, cols);
        List<Integer[]> possibleBowPositions = new ArrayList<Integer[]>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                possibleBowPositions.add(new Integer[] {i, j});
            }
        }

        for (int i = 0; i < shipSettings.length; i++) {
            for (int j = 0; j < shipSettings[i]; j++) {
                Ship ship = switch (i) {
                    default -> new Submarine();
                    case 1 -> new Destroyer();
                    case 2 -> new Cruiser();
                    case 3 -> new Battleship();
                    case 4 -> new Carrier();
                };

                if (!ocean.tryToPlaceShipRandomly(ship, possibleBowPositions)) {
                    return tryToInitOcean(oceanSizes, shipSettings);
                }
            }
        }

        return ocean;
    }

    public static Ocean tryToInitOcean1(final Integer[] oceanSizes, final Integer[] shipSettings) {
        Integer rows = oceanSizes[0];
        Integer cols = oceanSizes[1];

        Ocean ocean = new Ocean(rows, cols);
        List<Integer[]> possibleBowPositions = new ArrayList<Integer[]>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                possibleBowPositions.add(new Integer[] {i, j});
            }
        }

        List<Ship> shipsToBePlaced = new ArrayList<>();

        for (int i = 0; i < shipSettings.length; i++) {
            for (int j = 0; j < shipSettings[i]; j++) {
                Ship ship = switch (i) {
                    default -> new Submarine();
                    case 1 -> new Destroyer();
                    case 2 -> new Cruiser();
                    case 3 -> new Battleship();
                    case 4 -> new Carrier();
                };

                shipsToBePlaced.add(ship);
            }
        }

        if (tryToFitShip(shipsToBePlaced.toArray(new Ship[0]), 0, ocean, possibleBowPositions)) {
            return ocean;
        } else {
            return null;
        }
    }

    private void placeShipInLoc(Ship ship, Integer[] bowCoords, boolean isHorizontal) {
        if (isHorizontal) {
            for (int i = bowCoords[1]; i < bowCoords[1] + ship.shipSize; i++) {
                sectors[bowCoords[0]][i].setSectorContent(ship);
            }
        } else {
            for (int i = bowCoords[0]; i < bowCoords[0] + ship.shipSize; i++) {
                sectors[i][bowCoords[1]].setSectorContent(ship);
            }
        }
    }

    private void removeShipInLoc(Ship ship, Integer[] bowCoords, boolean isHorizontal) {
        if (isHorizontal) {
            for (int i = bowCoords[1]; i < bowCoords[1] + ship.shipSize; i++) {
                sectors[bowCoords[0]][i].clearSectorContent();
            }
        } else {
            for (int i = bowCoords[0]; i < bowCoords[0] + ship.shipSize; i++) {
                sectors[i][bowCoords[1]].clearSectorContent();
            }
        }
    }

    private static boolean tryToFitShip(Ship[] ships, int currIndex, Ocean currentOcean, List<Integer[]> possibleBowPositions) {
        var currPossibleBowPositions = new ArrayList<>(possibleBowPositions);

        while (currPossibleBowPositions.size() != 0) {
            int posIndex = currPossibleBowPositions.size() == 1 ? 0 :
                    ThreadLocalRandom.current().nextInt(0, currPossibleBowPositions.size() - 1);

            Integer[] coords = currPossibleBowPositions.get(posIndex);

            String couldPlaceInPos = currentOcean.canPlaceShipInLoc(ships[currIndex], coords);

            if (couldPlaceInPos.equals("none")) {
                currPossibleBowPositions.remove(posIndex);
                continue;
            }

            currentOcean.placeShipInLoc(ships[currIndex], coords, couldPlaceInPos.equals("horizontal"));

            if (currIndex == ships.length - 1) {
                return true;
            }

            possibleBowPositions.remove(coords);
            boolean placementSuccessful = tryToFitShip(ships, currIndex + 1, currentOcean, possibleBowPositions);

            if (!placementSuccessful) {
                currentOcean.removeShipInLoc(ships[currIndex], coords, couldPlaceInPos.equals("horizontal"));
                possibleBowPositions.add(coords);
                currPossibleBowPositions.remove(posIndex);
            } else {
                return true;
            }
        }

        return false;
    }

    @Override
    public void showAllMap() {
        for (int i = 0; i < sectors.length; i++) {
            for (int j = 0; j < sectors[i].length; j++) {
                sectors[i][j].onSectorHitSunk(false);
            }
        }
    }

    @Override
    public Integer[] getSize() {
        return new Integer[] {sectors.length, sectors[0].length};
    }
}
