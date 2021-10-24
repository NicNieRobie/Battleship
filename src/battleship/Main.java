package battleship;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        GameManagerDelegate gameManagerDelegate = new GameManager();
        gameManagerDelegate.initializeGame();
    }
}
