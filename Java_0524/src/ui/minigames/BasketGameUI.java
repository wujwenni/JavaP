package ui.minigames;

public interface BasketGameUI {
    void updateLife(int life);
    void updateScore(int score);
    void repaintGame();
    void finishGame(boolean success);
}