package battleship;

/**
 * Game presenter interface.
 *
 * Represents an object responsible for the game's visual
 * messaging and screens. Provides methods for current
 * game state, game start and game end screens visualization.
 */
public interface GamePresenterDelegate {
    /**
     * Prints current state of the game:
     * <ul>
     *     <li>ocean map;</li>
     *     <li>game statistics (ship count, shots made etc.).</li>
     * </ul>
     *
     * @param gameManager game's manager entity.
     */
    void printCurrentGameState(GameManagerDelegate gameManager);

    /**
     * Prints game over message and game's state at its end:
     * <ul>
     *     <li>ocean map;</li>
     *     <li>game statistics (ship count, shots made etc.).</li>
     * </ul>
     *
     * @param gameManager game's manager entity.
     */
    void printGameOver(GameManagerDelegate gameManager);

    /**
     * Prints the game's opening screen with its rules and title.
     */
    void printOpeningScreen();
}
