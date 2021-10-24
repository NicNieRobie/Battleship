package battleship;

public interface GameManagerDelegate {
    public class GameState {
        int shipsLeft;
        int shotsMade;
        OceanDelegate gameOcean;

        public GameState(int shipsLeft, int shotsMade, OceanDelegate gameOcean) {
            this.shipsLeft = shipsLeft;
            this.gameOcean = gameOcean;
            this.shotsMade = shotsMade;
        }
    }

    void initializeGame();
    GameState getGameState();
}
