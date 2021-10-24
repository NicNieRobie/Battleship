package battleship;

import java.util.Collections;

/**
 * Class that represents default Battleship game presenter.
 */
public class GamePresenter implements GamePresenterDelegate {
    /**
     * Prints current state of the game:
     * <ul>
     *     <li>ocean map;</li>
     *     <li>game statistics (ship count, shots made etc.).</li>
     * </ul>
     *
     * @param gameManager game's manager entity.
     */
    @Override
    public void printCurrentGameState(GameManagerDelegate gameManager) {
        // Getting the game state.
        var gameState = gameManager.getGameState();
        int[] oceanSize = gameState.gameOcean.getSize();

        // Printing the ocean.
        gameState.gameOcean.print();

        System.out.println();
        int contentBoxWidth = Math.max(2 * oceanSize[1] + 1, 32);

        String shipsLeftDataString = "Ships left: " + gameState.shipsLeft;
        String shotsMadeDataString = "Shots made: " + gameState.shotsMade;
        String torpedoCountDataString = "Torpedoes left: " + gameState.torpedoesLeft;
        String recoveryModeDataString = "Recovery mode is ON";

        // Printing stats.
        System.out.println("  ┌" + String.join("", Collections.nCopies(contentBoxWidth / 2 - 3, "─")) +
                " STATS " +
                String.join("", Collections.nCopies(contentBoxWidth / 2 + contentBoxWidth % 2 - 4, "─"))
                + "┐");
        System.out.println("  │ " + shipsLeftDataString +
                String.join("", Collections.nCopies(contentBoxWidth - shipsLeftDataString.length() - 1, " "))
                + "│");
        System.out.println("  │ " + shotsMadeDataString +
                String.join("", Collections.nCopies(contentBoxWidth - shotsMadeDataString.length() - 1, " "))
                + "│");
        System.out.println("  │ " + torpedoCountDataString +
                String.join("", Collections.nCopies(contentBoxWidth - torpedoCountDataString.length() - 1, " "))
                + "│");

        if (gameState.recoveryModeOn) {
            System.out.println("  │ " + recoveryModeDataString +
                    String.join("", Collections.nCopies(contentBoxWidth - recoveryModeDataString.length() - 1, " "))
                    + "│");
        }

        System.out.println("  └" + String.join("", Collections.nCopies(contentBoxWidth, "─")) + "┘");
        System.out.println();
    }

    /**
     * Prints game over message and game's state at its end:
     * <ul>
     *     <li>ocean map;</li>
     *     <li>game statistics (ship count, shots made etc.).</li>
     * </ul>
     *
     * @param gameManager game's manager entity.
     */
    @Override
    public void printGameOver(GameManagerDelegate gameManager) {
        // Getting the game state.
        var gameState = gameManager.getGameState();

        // Revealing the map.
        gameState.gameOcean.discoverAllMap();
        ConsoleManager.clearConsole();

        // Printing the screen.
        System.out.println("  ═════════════════════════════════");
        System.out.println("  │           GAME OVER!          │");
        System.out.println("  ═════════════════════════════════");
        System.out.println();

        printCurrentGameState(gameManager);

        System.out.println("Game is over! Press Enter to leave the game: ");
        ConsoleManager.waitForEnter();
    }

    /**
     * Prints the game's opening screen with its rules and title.
     */
    @Override
    public void printOpeningScreen() {
        ConsoleManager.clearConsole();

        // Printing the screen.
        System.out.println("  ╔" + String.join("", Collections.nCopies(90, "═")) + "╗");
        System.out.println("  ║" + String.join("", Collections.nCopies(90, " ")) + "║");
        System.out.println("  ║      ██████╗  █████╗ ████████╗████████╗██╗     ███████╗███████╗██╗  ██╗██╗██████╗        ║");
        System.out.println("  ║      ██╔══██╗██╔══██╗╚══██╔══╝╚══██╔══╝██║     ██╔════╝██╔════╝██║  ██║██║██╔══██╗       ║");
        System.out.println("  ║      ██████╔╝███████║   ██║      ██║   ██║     █████╗  ███████╗███████║██║██████╔╝       ║");
        System.out.println("  ║      ██╔══██╗██╔══██║   ██║      ██║   ██║     ██╔══╝  ╚════██║██╔══██║██║██╔═══╝        ║");
        System.out.println("  ║      ██████╔╝██║  ██║   ██║      ██║   ███████╗███████╗███████║██║  ██║██║██║            ║");
        System.out.println("  ║      ╚═════╝ ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚══════╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝            ║");
        System.out.println("  ║" + String.join("", Collections.nCopies(90, " ")) + "║");
        System.out.println("  ╠" + String.join("", Collections.nCopies(90, "═")) + "╣");
        System.out.println("  ║" + String.join("", Collections.nCopies(90, " ")) + "║");
        System.out.println("  ║ Welcome to the Battleship game!                                                          ║");
        System.out.println("  ║ You will be playing against the computer, and your goal is to sink all of its ships.     ║");
        System.out.println("  ║" + String.join("", Collections.nCopies(90, " ")) + "║");
        System.out.println("  ║ There are five types of ships in total, each with its own size:                          ║");
        System.out.println("  ║   1. Submarine - 1 cell (sector) in size                                                 ║");
        System.out.println("  ║   2. Destroyer - 2 cells (sectors) in size                                               ║");
        System.out.println("  ║   3. Cruiser - 3 cells (sectors) in size                                                 ║");
        System.out.println("  ║   4. Battleship - 4 cells (sectors) in size                                              ║");
        System.out.println("  ║   5. Carrier - 5 cell (sectors) in size                                                  ║");
        System.out.println("  ║" + String.join("", Collections.nCopies(90, " ")) + "║");
        System.out.println("  ║ Before the start of the game, you'll be asked to enter a number of settings:             ║");
        System.out.println("  ║   1. Ocean size settings - how big the game's map is                                     ║");
        System.out.println("  ║   2. Ship count settings - how many ships of each type does computer have                ║");
        System.out.println("  ║   3. Torpedo mode settings - how many torpedoes (one-hit destruction shots) you'll have  ║");
        System.out.println("  ║   4. Recovery mode settings - if ships will be recovered if don't hit them with every    ║");
        System.out.println("  ║        shot starting from the first hit until the ship is sunk                           ║");
        System.out.println("  ║" + String.join("", Collections.nCopies(90, " ")) + "║");
        System.out.println("  ║ The game will end when you destroy all of the computer's ships.                          ║");
        System.out.println("  ║ Your goal is to destroy them with as few shots as possible.                              ║");
        System.out.println("  ║ Good luck, and enjoy the game!                                                           ║");
        System.out.println("  ║" + String.join("", Collections.nCopies(90, " ")) + "║");
        System.out.println("  ╚" + String.join("", Collections.nCopies(90, "═")) + "╝");
        System.out.println();
        System.out.println("  Press Enter to continue:");
        System.out.print("  ");
    }
}
