package ru.hse.sc.battleship;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ConsoleReader {
    public static String readInputFromOptions(List<String> options) {
        var scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!options.contains(input)) {
            System.err.println(ConsoleColorString.RED +
                    "ERROR: Invalid value! Please try again." +
                    ConsoleColorString.RESET);
            input = scanner.nextLine();
        }

        return input;
    }

    public static int readInteger(String inputPromptMessage) {
        int input = 0;

        while(true) {
            Scanner scanner = new Scanner(System.in);

            try {
                System.out.print(inputPromptMessage + '\n');
                input = scanner.nextInt();
                scanner.close();
                break;
            } catch (InputMismatchException notNumber) {
                System.err.println(ConsoleColorString.RED +
                        "ERROR: Invalid value! Please try again." +
                        ConsoleColorString.RESET);
            }
        }

        return input;
    }
}
