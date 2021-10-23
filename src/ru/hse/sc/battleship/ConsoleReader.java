package ru.hse.sc.battleship;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ConsoleReader {
    static final Scanner scanner = new Scanner(System.in);

    public static String readInputFromOptions(List<String> options) {
        String input = scanner.nextLine();

        while (!options.contains(input)) {
            System.err.println(ConsoleANSICode.RED +
                    "ERROR: Invalid value! Please try again." +
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
                System.err.println(ConsoleANSICode.RED +
                        "ERROR: Invalid value! Please try again." +
                        ConsoleANSICode.RESET);
            }
        }

        return input;
    }
}
