package battleship;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class that represents default Battleship ocean (map).
 */
public class Ocean implements OceanDelegate {
    // Ocean's sectors.
    OceanSectorDelegate[][] sectors;

    /**
     * Ocean object constructor.
     *
     * @param rows amount of rows in the ocean map.
     * @param cols amount of columns in the ocean map.
     */
    public Ocean(final int rows, final int cols) {
        sectors = new OceanSectorDelegate[rows][cols];

        // Initializing sectors.
        for (int i = 0; i < sectors.length; i++) {
            for (int j = 0; j < sectors[i].length; j++) {
                sectors[i][j] = new OceanSector();
            }
        }
    }

    /**
     * Gets the sector on given coordinates.
     *
     * @param row row coordinate of the sector.
     * @param col column coordinate of the sector.
     *
     * @return the sector on given coordinates.
     */
    @Override
    public OceanSectorDelegate getOceanSector(int row, int col) {
        // Getting the sector.
        if (row < sectors.length && col < sectors[0].length) {
            return sectors[row][col];
        } else {
            throw new IllegalArgumentException("Wrong coordinate!");
        }
    }

    /**
     * Checks if the sector on given coordinates
     * was previously hit.
     *
     * @param row row coordinate of the sector.
     * @param col column coordinate of the sector.
     *
     * @return boolean - true, if the sector was hit.
     */
    @Override
    public boolean sectorIsHit(int row, int col) {
        return sectors[row][col].isHit();
    }

    /**
     * Checks if ship in the sector on given
     * coordinates was sunk by the hit.
     *
     * @param row row coordinate of the sector.
     * @param col column coordinate of the sector.
     * @param isTorpedoHit flag of damage being dealt by a torpedo.
     * @param recoveryModeOn flag of recovery mode being on.
     * @param shipLastHit ship that was hit last time.
     *
     * @return boolean - true, if the ship was sunk.
     */
    @Override
    public boolean onHitSunk(int row, int col, boolean isTorpedoHit, boolean recoveryModeOn, Ship shipLastHit) {
        // Calling the sector's processing method.
        if (row < sectors.length && col < sectors[0].length) {
            return sectors[row][col].onSectorHitSunk(isTorpedoHit, recoveryModeOn, shipLastHit);
        } else {
            throw new IllegalArgumentException("Wrong coordinate!");
        }
    }

    /**
     * Prints the ocean representation to console.
     */
    @Override
    public void print() {
        String topBorder = "┌" + String.join("", Collections.nCopies(2 * sectors[0].length + 1, "─")) + "┐";
        String interBorder = "├" + String.join("", Collections.nCopies(2 * sectors[0].length + 1, "─")) + "┤";

        // Printing the column labels - one digit per row starting from the first digit.
        int digitCount = (int)Math.floor(Math.log10(sectors[0].length) + 1);
        for (int i = digitCount; i > 0; i--) {
            System.out.print("    ");
            for (int j = 0; j < sectors[0].length; j++) {
                int colNumDigitCount = (int)Math.floor(Math.log10(j + 1) + 1);
                int digit = (((j + 1) % (int)Math.pow(10, i)) / (int)Math.pow(10, i - 1));
                System.out.print(digit == 0 && colNumDigitCount < i ? " " : digit);
                System.out.print(" ");
            }
            System.out.print('\n');
        }

        System.out.print("  " + topBorder);
        System.out.print('\n');

        // Printing the sectors.
        for (int i = 0; i < sectors.length; i++) {
            // Row character (letter).
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

        // Forming the interim border.
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

        // Printing the legend.
        printLegend(legendWidth);
        System.out.print('\n');
    }

    /**
     * Gets the size of the ocean.
     *
     * @return array of integers - amount of rows,
     * amount of columns
     */
    @Override
    public int[] getSize() {
        return new int[] {sectors.length, sectors[0].length};
    }

    /**
     * "Hits" the whole map to show each sector's content.
     */
    @Override
    public void discoverAllMap() {
        // Hitting each sector.
        for (int i = 0; i < sectors.length; i++) {
            for (int j = 0; j < sectors[i].length; j++) {
                sectors[i][j].onSectorHitSunk(false, false,null);
            }
        }

        ConsoleManager.clearConsole();
    }

    /**
     * Tries to place the ship randomly within given configuration
     * and returns the result of the attempt.
     *
     * @param ship ship being placed.
     * @param possibleBowPositionsRef list of all possible locations
     *                                for the ship's bow.
     * @return boolean - true, if ship was placed successfully
     */
    private boolean tryToPlaceShipRandomly(final Ship ship, List<int[]> possibleBowPositionsRef) {
        if (possibleBowPositionsRef.size() == 0) {
            return false;
        }

        // Making the list's copy for editing.
        List<int[]> possibleBowPositions = new ArrayList<int[]>(possibleBowPositionsRef);

        while (possibleBowPositions.size() != 0) {
            // Randomly picking position and direction.
            int posIndex = possibleBowPositions.size() == 1 ? 0 :
                    ThreadLocalRandom.current().nextInt(0, possibleBowPositions.size() - 1);
            int direction = ThreadLocalRandom.current().nextInt(0, 2);

            int row = possibleBowPositions.get(posIndex)[0];
            int col = possibleBowPositions.get(posIndex)[1];

            // Trying to place the ship.
            boolean couldPlace = tryToPlaceShipInLoc(ship, new int[]{row, col}, direction == 0);

            if (!couldPlace) {
                // Removing the position from possible choices if ship couldn't be placed.
                possibleBowPositions.remove(posIndex);
            } else {
                // Removing the position from possible choices as the ship was placed there.
                possibleBowPositionsRef.remove(posIndex);
                return true;
            }
        }

        return false;
    }

    /**
     * Tries to place the ship in specified location with given
     * direction.
     *
     * @param ship ship being placed.
     * @param coords ship's bow location coordinates.
     * @param isHorizontal flag of ship being placed horizontally.
     *
     * @return boolean - true, if ship was placed successfully
     */
    private boolean tryToPlaceShipInLoc(Ship ship, int[] coords, Boolean isHorizontal) {
        int row = coords[0];
        int col = coords[1];

        // If the ship is placed horizontally.
        if (isHorizontal) {
            if (col + ship.shipSize > sectors[0].length) {
                return false;
            }

            // Calculating the bounds for ship search sector.
            int topLocBound = row == 0 ? row : row - 1;
            int bottomLocBound = row == sectors.length - 1 ? row : row + 1;
            int leftLocBound = col == 0 ? col : col - 1;
            int rightLocBound = col + ship.shipSize == sectors[0].length ?
                    col + ship.shipSize - 1 : col + ship.shipSize;

            // Searching for adjacent or overlapping ships.
            for (int i = topLocBound; i < bottomLocBound + 1; i++) {
                for (int j = leftLocBound; j < rightLocBound + 1; j++) {
                    if (!sectors[i][j].isAvailable()) {
                        return false;
                    }
                }
            }

            // Placing the ship if none were found.
            for (int i = col; i < col + ship.shipSize; i++) {
                sectors[row][i].setSectorContent(ship);
            }
            return true;
        } else {
            if (row + ship.shipSize > sectors.length) {
                return false;
            }

            // Calculating the bounds for ship search sector.
            int topLocBound = row == 0 ? row : row - 1;
            int bottomLocBound = row + ship.shipSize == sectors.length ?
                    row + ship.shipSize - 1 : row + ship.shipSize;
            int leftLocBound = col == 0 ? col : col - 1;
            int rightLocBound = col == sectors[0].length - 1 ? col : col + 1;

            // Searching for adjacent or overlapping ships.
            for (int i = topLocBound; i < bottomLocBound + 1; i++) {
                for (int j = leftLocBound; j < rightLocBound + 1; j++) {
                    if (!sectors[i][j].isAvailable()) {
                        return false;
                    }
                }
            }

            // Placing the ship if none were found.
            for (int i = row; i < row + ship.shipSize; i++) {
                sectors[i][col].setSectorContent(ship);
            }
            return true;
        }
    }

    /**
     * Prints the ocean map's legend.
     *
     * @param contentWidth width of the legend's border box.
     */
    private void printLegend(int contentWidth) {
        if (contentWidth < 32) {
            contentWidth = 32;
        }

        // Printing the legend.
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

    /**
     * Tries to initialize an ocean object with given parameters.
     * !WARNING: Throws StackOverflowError if initialization is impossible!
     *
     * @param oceanSizes size of the ocean: amount of rows,
     *                  amount of columns
     * @param shipSettings amounts of ships in each class
     *
     * @return Ocean that was created.
     */
    public static Ocean tryToInitOcean (final int[] oceanSizes, final int[] shipSettings) throws StackOverflowError {
        int rows = oceanSizes[0];
        int cols = oceanSizes[1];

        Ocean ocean = new Ocean(rows, cols);
        List<int[]> possibleBowPositions = new ArrayList<>();

        // Initializing the list of possible ship bow positions.
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                possibleBowPositions.add(new int[] {i, j});
            }
        }

        for (int i = 0; i < shipSettings.length; i++) {
            for (int j = 0; j < shipSettings[i]; j++) {
                // Creating the ship.
                Ship ship = switch (i) {
                    default -> new Submarine();
                    case 1 -> new Destroyer();
                    case 2 -> new Cruiser();
                    case 3 -> new Battleship();
                    case 4 -> new Carrier();
                };

                // Trying to place the ship.
                if (!ocean.tryToPlaceShipRandomly(ship, possibleBowPositions)) {
                    // Restarting the process if ship couldn't be placed.
                    return tryToInitOcean(oceanSizes, shipSettings);
                }
            }
        }

        return ocean;
    }
}
