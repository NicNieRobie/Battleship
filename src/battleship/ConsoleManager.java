package battleship;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ConsoleManager {
    static final Scanner scanner = new Scanner(System.in);

    public static String readInputFromOptions(List<String> options) {
        scanner.nextLine();
        String input = scanner.nextLine();

        while (!options.contains(input)) {
            System.err.print(ConsoleANSICode.RED +
                    "ERROR: Invalid value! Please try again: " +
                    ConsoleANSICode.RESET);
            input = scanner.nextLine();
        }

        return input;
    }

    public static int readInteger(String inputPromptMessage) {
        int input = 0;

        while(true) {
            try {
                System.out.print(inputPromptMessage + '\n');
                input = scanner.nextInt();
                break;
            } catch (InputMismatchException notNumber) {
                System.err.print("ERROR: Invalid value! Please try again: ");
            }
        }

        return input;
    }

    public static int readPositiveInteger(String inputPromptMessage) {
        int input = 0;

        while(true) {
            try {
                System.out.print(inputPromptMessage + '\n');
                if (scanner.hasNextInt()) {
                    input = scanner.nextInt();
                }

                if (input >= 0) {
                    break;
                } else {
                    System.err.print("ERROR: Non-positive value entered! Please try again: ");
                }
            } catch (InputMismatchException notNumber) {
                System.err.print("ERROR: Invalid value! Please try again: ");
            }
        }

        return input;
    }

    public static String readWord() {
        return scanner.next();
    }

    public static Integer[] readCoordinates(Integer[] fieldSize) {
        System.out.print("Enter the coordinates (for example A1): \n> ");

        int row = 0, col = 0;

        while (true) {
            String reply = readWord();
            if (reply.charAt(0) < 65 + fieldSize[0] && reply.charAt(0) > 64) {
                row = (int)reply.charAt(0) - 65;
            } else {
                System.err.println("ERROR: Invalid row value! Please try again.");
                scanner.nextLine();
            }

            String colNumberString = reply.substring(1, reply.length());

            try {
                int colNumber = Integer.parseInt(colNumberString);
                if (colNumber - 1 < fieldSize[1] && colNumber - 1 >= 0) {
                    col = colNumber - 1;
                    break;
                } else {
                    System.err.println("ERROR: Invalid column value! Please try again.");
                    scanner.nextLine();
                }
            } catch (NumberFormatException e) {
                System.err.println("ERROR: Invalid column value! Please try again.");
                scanner.nextLine();
            }
        }

        return new Integer[]{row, col};
    }

    public static void ClearConsole(){
        try{
            String operatingSystem = System.getProperty("os.name");

            if(operatingSystem.contains("Windows")){
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
