package ru.hse.sc.battleship;

public class Main {

    public static void main(String[] args) {
        GameManagerDelegate gameManagerDelegate = new GameManager();
        gameManagerDelegate.initializeGame();
    }
}
