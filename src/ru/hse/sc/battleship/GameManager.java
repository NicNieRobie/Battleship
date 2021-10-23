package ru.hse.sc.battleship;

import java.util.*;

public class GameManager implements GameManagerDelegate {
    OceanDelegate ocean;
    Integer shipsNotSunk;
    Integer shotsFired;

    private boolean tryToInitializeGameOcean() {
        OceanDelegate ocean = null;
        boolean exitFlag = false;

        while (!exitFlag) {
            Integer[] oceanSize = readOceanSizeSettings();
            Integer[] shipCounts = readShipCountSettings();
            ocean = Ocean.tryToInitOcean(oceanSize, shipCounts);

            if (!Objects.isNull(ocean)) {
                this.ocean = ocean;

                shipsNotSunk = 0;
                for(final int shipCount : shipCounts) {
                    shipsNotSunk += shipCount;
                }

                return true;
            }

            System.out.println("Game could not be initialized with given settings.\n" +
                                "Would you like to try again? (Y\\N):\n" +
                                "> ");

            String answer = ConsoleReader.readInputFromOptions(Arrays.asList("Y", "N"));

            switch (answer) {
                case "Y":
                    continue;
                case "N":
                    exitFlag = true;
            }
        }

        System.out.println(ConsoleANSICode.YELLOW + "Shutting down. Goodbye!" + ConsoleANSICode.RESET);
        return false;
    }

    private Integer[] readOceanSizeSettings() {
        Integer[] oceanSize = new Integer[2];
        String[] dimensions = {"rows", "columms"};

        for (int i = 0; i < 2; i++) {
            String promptMessage = "Please enter the number of " + dimensions[i] + ":";
            oceanSize[i] = ConsoleReader.readInteger(promptMessage);
        }

        return oceanSize;
    }

    private Integer[] readShipCountSettings() {
        var scanner = new Scanner(System.in);

        Integer[] shipCounts = new Integer[5];
        String[] shipTypes = {"submarine", "destroyer", "cruiser", "battleship", "carrier"};

        for (int i = 0; i < 5; i++) {
            String promptMessage = "Please enter the number of " + shipTypes[i] + "s:";
            shipCounts[i] = ConsoleReader.readInteger(promptMessage);
        }

        return shipCounts;
    }

    private void printCurrentSituation() {
        System.out.println(ConsoleANSICode.CLEAR);
        System.out.println("Shots fired: " + shotsFired);
        System.out.println("---------------------------------------------\n\n");
        ocean.print();
        System.out.println("\n\n");
    }

    private Integer[] readUserCommand() {
        int row = ConsoleReader.readInteger("Enter the row number: ");
        int col = ConsoleReader.readInteger("Enter the column number: ");

        return new Integer[]{row, col};
    }

    public void initializeGame() {
        if (!tryToInitializeGameOcean()) {
            return;
        }

        while (shipsNotSunk != 0) {
            printCurrentSituation();
            Integer[] shotCoords = readUserCommand();

            System.out.println(ConsoleANSICode.CLEAR);

            int shotRow = shotCoords[0];
            int shotCol = shotCoords[1];

            try {
                if (ocean.onHitSunk(shotRow, shotCol)) {
                    shipsNotSunk--;
                }
            } catch (Exception ex) {
                System.err.println(ConsoleANSICode.RED + ex.getMessage() + ConsoleANSICode.RESET);
            }
        }
    }
}
