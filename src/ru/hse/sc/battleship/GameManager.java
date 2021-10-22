package ru.hse.sc.battleship;

import java.util.*;

public class GameManager implements GameManagerDelegate {
    OceanDelegate ocean;
    Integer shipsNotSunk;
    Integer shotsFired;

    private void tryToInitializeOcean() {
        OceanDelegate ocean = null;
        boolean exitFlag = false;
        var scanner = new Scanner(System.in);

        while (!exitFlag) {
            Integer[] oceanSize = readOceanSizeSettings();
            Integer[] shipCounts = readShipCountSettings();
            ocean = initializeOcean(oceanSize, shipCounts);

            if (!Objects.isNull(ocean)) {
                this.ocean = ocean;
                return;
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

        System.out.println(ConsoleColorString.YELLOW + "Shutting down. Goodbye!" + ConsoleColorString.RESET);
    }

    private Integer[] readOceanSizeSettings() {
        var scanner = new Scanner(System.in);

        Integer[] oceanSize = new Integer[2];
        String[] dimensions = {"rows", "columms"};

        for (int i = 0; i < 5; i++) {
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
            String promptMessage = "Please enter the number of " + shipTypes[i] + ":";
            shipCounts[i] = ConsoleReader.readInteger(promptMessage);
        }

        return shipCounts;
    }

    public OceanDelegate initializeOcean(Integer[] oceanSize, Integer[] shipCounts) {
        Ocean ocean = new Ocean();
        ocean.
    }

    public void printOcean() {

    }

    public void initializeGame() {

    }
}
