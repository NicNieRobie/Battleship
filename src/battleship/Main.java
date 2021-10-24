package battleship;

/**
 * Main class - entry point for the program.
 */
public class Main {
    /**
     * Entry point of the program.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Checking the number of arguments.
        if (args.length == 7) {
            // Trying to initialize the game with the arguments.
            GameManagerDelegate gameManagerDelegate = new GameManager(args);
            gameManagerDelegate.initializeGame();
            return;
        } else if (args.length != 0) {
            // Notifying user of the inability to use arguments as settings.
            System.err.println("Wrong number of arguments specified, settings will be read in-game.");
            System.out.print("Press Enter to continue: ");
            ConsoleManager.waitForEnter();
        }

        // Initializing the game with settings from console input.
        GameManagerDelegate gameManagerDelegate = new GameManager();
        gameManagerDelegate.initializeGame();
    }
}
