package battleship;

/**
 * Game manager interface.
 *
 * Represents an object responsible for the game process
 * initialization and running, acts as a core of the game's
 * mechanism of response to user commands, saves the current
 * state of the game.
 */
public interface GameManagerDelegate {
    /**
     * Class that represents the game's state.
     */
    class GameState {
        // Amount of ships not yet sunk.
        int shipsLeft;
        // Amount of shots made by the player.
        int shotsMade;
        // Amount of torpedoes left for the user to use.
        int torpedoesLeft;
        // Game's ocean.
        OceanDelegate gameOcean;
        // Recovery mode flag.
        boolean recoveryModeOn;
        // Ship that was hit the last time.
        Ship shipLastHit;

        /**
         * Game state's constructor.
         *
         * @param shipsLeft amount of ships not yet sunk.
         * @param shotsMade amount of shots made by the player.
         * @param torpedoesCount amount of torpedoes left for the user to use.
         * @param gameOcean game's ocean.
         * @param recoveryModeOn ship that was hit the last time.
         */
        public GameState(int shipsLeft, int shotsMade, int torpedoesCount, OceanDelegate gameOcean, boolean recoveryModeOn) {
            this.shipsLeft = shipsLeft;
            this.gameOcean = gameOcean;
            this.shotsMade = shotsMade;
            this.torpedoesLeft = torpedoesCount;
            this.recoveryModeOn = recoveryModeOn;
        }
    }

    /**
     * Initializes the game parameters by reading them from user input
     * with console prompts and starts the game process.
     */
    void initializeGame();

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
    GameState getGameState();
}
