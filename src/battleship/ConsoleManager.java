package battleship;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

/**
 * Console manager helper class that provides methods
 * for console input read.
 */
public class ConsoleManager {
    // Class' scanner.
    static final Scanner scanner = new Scanner(System.in);

    /**
     * Reads input and asks for retry if input is not
     * in the given options list.
     *
     * @param options options list.
     *
     * @return string - user input.
     */
    public static String readInputFromOptions(List<String> options) {
        scanner.nextLine();
        String input = scanner.nextLine();

        // Input read cycle.
        while (!options.contains(input)) {
            System.err.print("ERROR: Invalid value! Please try again: ");
            input = scanner.nextLine();
        }

        return input;
    }

    /**
     * Reads an integer from the user console input and
     * asks to retry if the integer doesn't meet the
     * conditions presented by the predicate.
     *
     * @param inputPromptMessage message displayed when asking for input.
     * @param predicate condition that integer has to meet.
     *
     * @return integer - user input.
     */
    public static int readIntegerWithCondition(String inputPromptMessage, Function<Integer, Boolean> predicate) {
        int input = 0;

        System.out.print(inputPromptMessage);
        while(true) {
            try {
                // Reading input.
                if (scanner.hasNextInt()) {
                    input = scanner.nextInt();
                }

                // Checking the predicate.
                if (predicate.apply(input)) {
                    break;
                } else {
                    System.err.print("ERROR: Invalid value! Please try again: ");
                }
             // If input wasn't an integer.
            } catch (InputMismatchException notNumber) {
                System.err.print("ERROR: Invalid value! Please try again: ");
            }
        }

        return input;
    }

    /**
     * Reads the string from user input until the first whitespace.
     *
     * @return string - user input
     */
    public static String readWord() {
        return scanner.next();
    }

    /**
     * Waits for user to press Enter (Return).
     */
    public static void waitForEnter() {
        scanner.nextLine();
    }

    /**
     * Reads parameters of the shot in Battleship game.
     *
     * @param fieldSize size of the game map.
     * @param torpedoesLeft amount of torpedoes left.
     *
     * @return array of integers - shot row, shot column, if torpedo is used - 1 if yes, 0 if no.
     */
    public static int[] readShotParams(int[] fieldSize, boolean torpedoesLeft) {
        System.out.print("Enter the coordinates (for example A1). To shoot a torpedo, type 'T' before the coordinates: \n> ");

        int row = 0, col = 0;
        boolean isTorpedo = false;

        while (true) {
            // Reading the input.
            String reply = readWord();

            // If user wants to use the torpedo.
            if (reply.charAt(0) == 'T' && (int)reply.charAt(1) < 65 + fieldSize[0] && (int)reply.charAt(1) > 64) {
                // Checking torpedo availability.
                if (!torpedoesLeft) {
                    System.err.println("ERROR: No torpedoes left! Please try again.");
                    scanner.nextLine();
                    continue;
                }

                // Setting the flag value.
                isTorpedo = true;
                reply = reply.substring(1);
            } else {
                isTorpedo = false;
            }

            // Reading the row char.
            if ((int)reply.charAt(0) < 65 + fieldSize[0] && (int)reply.charAt(0) > 64) {
                row = (int)reply.charAt(0) - 65;
            } else {
                System.err.println("ERROR: Invalid row value! Please try again.");
                scanner.nextLine();
                continue;
            }

            // Substring with the column number.
            String colNumberString = reply.substring(1, reply.length());

            // Trying to read the column number.
            try {
                // Parsing input.
                int colNumber = Integer.parseInt(colNumberString);

                // Reading column number.
                if (colNumber - 1 < fieldSize[1] && colNumber - 1 >= 0) {
                    col = colNumber - 1;
                    break;
                } else {
                    System.err.println("ERROR: Invalid column value! Please try again.");
                    scanner.nextLine();
                }
            } catch (NumberFormatException e) {
                // If column number isn't an integer.
                System.err.println("ERROR: Invalid column value! Please try again.");
                scanner.nextLine();
            }
        }

        return new int[]{row, col, isTorpedo ? 1 : 0};
    }

    /**
     * Clears the console (works outside the IDE only).
     */
    public static void clearConsole(){
        try {
            // Getting the machine's OS name.
            String operatingSystem = System.getProperty("os.name");

            // For Windows.
            if (operatingSystem.contains("Windows")) {
                // Launching cls via cmd.
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            // For UNIX.
            } else {
                // Launching clear.
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
