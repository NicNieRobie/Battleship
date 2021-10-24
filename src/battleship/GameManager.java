package battleship;

import java.util.*;

public class GameManager implements GameManagerDelegate {
    OceanDelegate ocean;
    GameState currentGameState;
    GamePresenterDelegate gamePresenter;

    private boolean tryToInitializeGameOcean() {
        OceanDelegate ocean = null;

        while (true) {
            ConsoleManager.ClearConsole();
            Integer[] oceanSize = readOceanSizeSettings();

            ConsoleManager.ClearConsole();
            Integer[] shipCounts = readShipCountSettings();

            try {
                ocean = Ocean.tryToInitOcean(oceanSize, shipCounts);

                this.ocean = ocean;

                int shipsNotSunk = 0;
                for(final int shipCount : shipCounts) {
                    shipsNotSunk += shipCount;
                }

                currentGameState = new GameState(shipsNotSunk, 0, ocean);

                return true;
            } catch (StackOverflowError error) {
                ConsoleManager.ClearConsole();
                System.out.print("Game could not be initialized with given settings.\n" +
                        "Would you like to try again? (Y\\N):\n" +
                        "> ");

                String answer = ConsoleManager.readInputFromOptions(Arrays.asList("Y", "N"));

                boolean exitFlag = false;
                switch (answer) {
                    case "Y":
                        continue;
                    case "N":
                        exitFlag = true;
                        break;
                }

                if (exitFlag) {
                    break;
                }
            }
        }

        System.out.println("Shutting down. Goodbye!");
        return false;
    }

    private Integer[] readOceanSizeSettings() {
        Integer[] oceanSize = new Integer[2];
        String[] dimensions = {"rows", "columns"};

        System.out.println("═════════════════════════════════");
        System.out.println("       OCEAN SIZE SETTINGS       ");
        System.out.println("═════════════════════════════════");
        System.out.println();

        for (int i = 0; i < 2; i++) {
            String promptMessage = "Please enter the number of " + dimensions[i] + ":";
            oceanSize[i] = ConsoleManager.readPositiveInteger(promptMessage);
        }

        return oceanSize;
    }

    private Integer[] readShipCountSettings() {
        var scanner = new Scanner(System.in);

        Integer[] shipCounts = new Integer[5];
        String[] shipTypes = {"submarine", "destroyer", "cruiser", "battleship", "carrier"};

        System.out.println("═════════════════════════════════");
        System.out.println("       SHIP COUNT SETTINGS       ");
        System.out.println("═════════════════════════════════");
        System.out.println();

        for (int i = 0; i < 5; i++) {
            String promptMessage = "Please enter the number of " + shipTypes[i] + "s:";
            shipCounts[i] = ConsoleManager.readPositiveInteger(promptMessage);
        }

        return shipCounts;
    }

    private Integer[] readUserCommand() {
        Integer[] oceanSize = ocean.getSize();
        return ConsoleManager.readCoordinates(oceanSize);
    }

    public void initializeGame() {
        if (!tryToInitializeGameOcean()) {
            return;
        }

        ConsoleManager.ClearConsole();

        gamePresenter = new GamePresenter();

        gamePresenter.printCurrentGameState(this);

        while (currentGameState.shipsLeft != 0) {
            Integer[] shotCoords = readUserCommand();
            int shotRow = shotCoords[0];
            int shotCol = shotCoords[1];

            try {
                ConsoleManager.ClearConsole();

                if (ocean.onHitSunk(shotRow, shotCol, false)) {
                    currentGameState.shipsLeft--;
                }
                currentGameState.shotsMade++;

                gamePresenter.printCurrentGameState(this);
            } catch (Exception ex) {
                System.err.println(ex.getMessage() + ConsoleANSICode.RESET);
            }
        }
    }

    @Override
    public GameState getGameState() {
        return currentGameState;
    }
}
