package battleship;

import java.util.Collections;

public class GamePresenter implements GamePresenterDelegate {
    public void printCurrentGameState(GameManagerDelegate gameManager) {
        var gameState = gameManager.getGameState();
        Integer[] oceanSize = gameState.gameOcean.getSize();
        gameState.gameOcean.print();

        System.out.println();

        int contentBoxWidth = Math.max(2 * oceanSize[1] + 1, 32);

        String shipsLeftDataString = "Ships left: " + gameState.shipsLeft;
        String shotsMadeDataString = "Shots made: " + gameState.shotsMade;

        System.out.println("  ┌" + String.join("", Collections.nCopies(contentBoxWidth / 2 - 3, "─")) +
                " STATS " + String.join("", Collections.nCopies(contentBoxWidth / 2 + contentBoxWidth % 2 - 4, "─")) + "┐");
        System.out.println("  │ " + shipsLeftDataString +
                String.join("", Collections.nCopies(contentBoxWidth - shipsLeftDataString.length() - 1, " ")) + "│");
        System.out.println("  │ " + shotsMadeDataString +
                String.join("", Collections.nCopies(contentBoxWidth - shotsMadeDataString.length() - 1, " ")) + "│");
        System.out.println("  └" + String.join("", Collections.nCopies(contentBoxWidth, "─")) + "┘");
        System.out.println();
    }
}
