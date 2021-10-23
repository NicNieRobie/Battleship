package ru.hse.sc.battleship;

public enum ConsoleANSICode {
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),
    CLEAR("\033[H\033[2J")
    ;

    private final String code;

    /**
     * @param code
     */
    ConsoleANSICode(final String code) {
        this.code = code;
    }

    /**
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return code;
    }
}