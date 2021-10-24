package battleship;

import java.util.*;

/**
 * Class that represents default Battleship game manager.
 */
public class GameManager implements GameManagerDelegate {
    // Current state of the game.
    GameState currentGameState;
    // Game's presenter.
    GamePresenterDelegate gamePresenter;
    // Flag of having the settings successfully read from command line arguments.
    boolean readFromArguments;
    // Settings read from command line arguments.
    int[] settings;

    /**
     * Initializes game manager class object.
     */
    public GameManager() { }

    /**
     * Initializes game manager class object with given parameters
     * for ocean size and ship counts.
     *
     * @param paramStrings array of strings representing integers - game parameter values.
     */
    public GameManager(String[] paramStrings) {
        // Settings array.
        settings = new int[7];

        // Trying to parse the passed arguments.
        boolean successfulRead = true;
        for (int i = 0; i < paramStrings.length; i++) {
            try {
                int paramValue = Integer.parseInt(paramStrings[i]);

                if (paramValue < 0 || (i < 2 && (paramValue == 0 || paramValue > 20))) {
                    successfulRead = false;
                    break;
                }

                settings[i] = paramValue;
            } catch (NumberFormatException e) {
                successfulRead = false;
                break;
            }
        }

        // If read failed.
        if (!successfulRead) {
            System.err.println("Could not read settings from arguments, settings will be read in-game.");
            System.out.print("Press Enter to continue: ");
            ConsoleManager.waitForEnter();
            readFromArguments = false;
        // If read succeeded - initialize the game with these settings.
        } else {
            readFromArguments = true;
        }
    }

    /**
     * Initializes the game parameters by reading them from user input
     * with console prompts and starts the game process.
     */
    @Override
    public void initializeGame() {
        // Initializing the presenter.
        gamePresenter = new GamePresenter();

        // Printing the opening screen.
        gamePresenter.printOpeningScreen();
        ConsoleManager.waitForEnter();

        if (readFromArguments) {
            // Trying to initialize and run the game with given settings.
            if (!tryToInitializeGameOceanFromArguments(settings)) {
                return;
            }
        } else {
            // Trying to initialize and run the game.
            if (!tryToInitializeGameOceanFromInput()) {
                return;
            }
        }

        runGame();
    }

    /**
     * Returns the current game state in a GameState object.
     *
     * GameState object contains:
     * <ul>
     *     <li> ships left in the game;</li>
     *     <li> amount of shots made by the player;</li>
     *     <li> amount of torpedoes left for the player to use;</li>
     *     <li> game's ocean (field);</li>
     *     <li> recovery mode flag;</li>
     *     <li> reference to the ship last hit.</li>
     * </ul>
     *
     * @return current game state.
     */
    @Override
    public GameState getGameState() {
        return currentGameState;
    }

    /**
     * Reads the ocean size game settings from user with
     * a console prompt.
     *
     * @return array of integers: amount of rows, amount of columns
     */
    private int[] readOceanSizeSettings() {
        int[] oceanSize = new int[2];
        String[] dimensions = {"rows", "columns"};

        // Printing the prompt label.
        System.out.println("═════════════════════════════════");
        System.out.println("       OCEAN SIZE SETTINGS       ");
        System.out.println("═════════════════════════════════");
        System.out.println();

        // Reading the values.
        for (int i = 0; i < 2; i++) {
            String promptMessage = "Please enter the number of " + dimensions[i] + " (0 < n < 20):\n> ";
            oceanSize[i] = ConsoleManager.readIntegerWithCondition(promptMessage, x -> x > 0 && x < 20);
        }

        return oceanSize;
    }

    /**
     * Reads the ship counts game settings from user with
     * a console prompt.
     *
     * @return array of integers - amount of ships of each class
     * starting with the submarine class
     */
    private int[] readShipCountSettings() {
        int[] shipCounts = new int[5];
        String[] shipTypes = {"submarine", "destroyer", "cruiser", "battleship", "carrier"};

        // Printing the prompt label.
        System.out.println("═════════════════════════════════");
        System.out.println("       SHIP COUNT SETTINGS       ");
        System.out.println("═════════════════════════════════");
        System.out.println();

        // Reading the values.
        for (int i = 0; i < 5; i++) {
            String promptMessage = "Please enter the number of " + shipTypes[i] + "s:\n> ";
            shipCounts[i] = ConsoleManager.readIntegerWithCondition(promptMessage, x -> x >= 0);
        }

        return shipCounts;
    }

    /**
     * Reads the torpedoes amount game setting from user with
     * a console prompt.
     *
     * @return integer - amount of torpedoes
     */
    private int readTorpedoModeSettings() {
        int torpedoCount = 0;

        // Printing the prompt label.
        System.out.println("═════════════════════════════════");
        System.out.println("      TORPEDO MODE SETTINGS      ");
        System.out.println("═════════════════════════════════");
        System.out.println();

        // Reading the value.
        String promptMessage = "Please enter the number of torpedoes:\n> ";
        torpedoCount = ConsoleManager.readIntegerWithCondition(promptMessage, x -> x >= 0);

        return torpedoCount;
    }

    /**
     * Reads the recovery mode flag fame setting from user with
     * a console prompt.
     *
     * @return boolean - recovery mode flag
     */
    private boolean readRecoveryModeSettings() {
        // Printing the prompt label.
        System.out.println("═════════════════════════════════");
        System.out.println("      RECOVERY MODE SETTINGS     ");
        System.out.println("═════════════════════════════════");
        System.out.println();

        // Reading the value.
        System.out.print("Do you wish to enable the recovery mode? (Y\\N):\n> ");
        String reply = ConsoleManager.readInputFromOptions(Arrays.asList("Y", "N"));

        return reply.equals("Y");
    }

    /**
     * Tries to initialize the game's ocean with given parameters
     * and a number of settings read from user input in the console.
     *
     * @param settings game's ocean size and ship counts parameters.
     *                 Values 0 and 1 - rows and columns of the game field.
     *                 Values 2-6 - ship counts for each ship class.
     *
     * @return boolean - if the ocean was initialized successfully
     */
    private boolean tryToInitializeGameOceanFromArguments(int[] settings) {
        OceanDelegate ocean;

        try {
            // Given settings.
            int[] oceanSize = new int[]{settings[0], settings[1]};
            int[] shipCounts = new int[]{settings[2], settings[3], settings[4], settings[5], settings[6]};

            // Reading additional parameters.
            ConsoleManager.clearConsole();
            System.out.println("Settings read from arguments successfully!\n");
            int torpedoCount = readTorpedoModeSettings();

            ConsoleManager.clearConsole();
            boolean recoveryModeOn = readRecoveryModeSettings();

            // Trying to init the ocean.
            ocean = Ocean.tryToInitOcean(oceanSize, shipCounts);

            int shipsNotSunk = 0;
            for(final int shipCount : shipCounts) {
                shipsNotSunk += shipCount;
            }

            // Init the game state.
            currentGameState = new GameState(shipsNotSunk, 0, torpedoCount,
                    ocean, recoveryModeOn);

            return true;
        // If ocean could not be created.
        } catch (StackOverflowError error) {
            // Asking for retry.
            ConsoleManager.clearConsole();
            System.out.print("Game could not be initialized with given settings.\n" +
                    "Would you like to try again? (Y\\N):\n> ");

            String answer = ConsoleManager.readInputFromOptions(Arrays.asList("Y", "N"));

            boolean exitFlag = false;
            switch (answer) {
                case "Y":
                    // Retry with console input.
                    tryToInitializeGameOceanFromInput();
                case "N":
                    exitFlag = true;
                    break;
            }
        }

        // Shutting down the game if ocean could not be created.
        System.out.println("Shutting down. Goodbye!");
        return false;
    }

    /**
     * Tries to initialize the game's ocean with settings read
     * from user input in the console.
     *
     * @return boolean - if the ocean was initialized successfully
     */
    private boolean tryToInitializeGameOceanFromInput() {
        OceanDelegate ocean;

        while (true) {
            // Reading settings.
            ConsoleManager.clearConsole();
            int[] oceanSize = readOceanSizeSettings();

            ConsoleManager.clearConsole();
            int[] shipCounts = readShipCountSettings();

            ConsoleManager.clearConsole();
            int torpedoCount = readTorpedoModeSettings();

            ConsoleManager.clearConsole();
            boolean recoveryModeOn = readRecoveryModeSettings();

            try {
                // Trying to init the ocean.
                ocean = Ocean.tryToInitOcean(oceanSize, shipCounts);

                int shipsNotSunk = 0;
                for(final int shipCount : shipCounts) {
                    shipsNotSunk += shipCount;
                }

                // Init the game state if creation was successful.
                currentGameState = new GameState(shipsNotSunk, 0, torpedoCount,
                        ocean, recoveryModeOn);

                return true;
            // If ocean could not be created.
            } catch (StackOverflowError error) {
                // Asking for retry.
                ConsoleManager.clearConsole();
                System.out.print("Game could not be initialized with given settings.\n" +
                        "Would you like to try again? (Y\\N):\n> ");

                String answer = ConsoleManager.readInputFromOptions(Arrays.asList("Y", "N"));

                boolean exitFlag = false;
                switch (answer) {
                    case "Y":
                        // Retry with console input.
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

        // Shutting down the game if ocean could not be created.
        System.out.println("Shutting down. Goodbye!");
        return false;
    }

    /**
     * Reads user's command on the game turn -
     * attack parameters and coordinates.
     *
     * @return array of integers - parameters of the attack:
     * <ul>
     *     <li>row coordinate of the attack;</li>
     *     <li>column coordinate of the attack;</li>
     *     <li>if torpedo is used (0 - no, 1 - yes).</li>
     * </ul>
     */
    private int[] readUserCommand() {
        int[] oceanSize = currentGameState.gameOcean.getSize();
        return ConsoleManager.readShotParams(oceanSize, currentGameState.torpedoesLeft != 0);
    }

    /**
     * Performs the game's logic for its turn - processing
     * the user's command and changing the game state.
     */
    private void performGameTurn() {
        // Parsing user's command params.
        int[] shotParams = readUserCommand();
        int shotRow = shotParams[0];
        int shotCol = shotParams[1];
        boolean isTorpedo = shotParams[2] == 1;

        try {
            ConsoleManager.clearConsole();

            // Checking if the sector is eligible for being hit.
            if (!currentGameState.gameOcean.sectorIsHit(shotRow, shotCol)) {
                currentGameState.shotsMade++;
                if (isTorpedo) {
                    currentGameState.torpedoesLeft--;
                }
            }

            // Checking if the ship was sunk on hit.
            if (currentGameState.gameOcean.onHitSunk(shotRow, shotCol, isTorpedo,
                    currentGameState.recoveryModeOn, currentGameState.shipLastHit)) {
                currentGameState.shipsLeft--;
            }

            // Saving the ship for the recovery operations.
            if (currentGameState.recoveryModeOn) {
                currentGameState.shipLastHit =
                        currentGameState.gameOcean.getOceanSector(shotRow, shotCol).getSectorContent();
            }

            // Presenting the current state of the game.
            gamePresenter.printCurrentGameState(this);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Runs the game from current state.
     */
    private void runGame() {
        // Presenting the initial state of the game.
        ConsoleManager.clearConsole();
        gamePresenter.printCurrentGameState(this);

        // Performing the turns.
        while (currentGameState.shipsLeft != 0) {
            performGameTurn();
        }

        // Presenting the game over screen.
        gamePresenter.printGameOver(this);
    }
}
