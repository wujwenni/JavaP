package ui.minigames;

import javax.swing.*;
import games.Basket_game;
import ui.UIManager;

public class BasketGameLauncher {
    public static void launch(UIManager uiManager) {
        JFrame frame = new JFrame("바구니 게임");
        BasketGameScreen screen = new BasketGameScreen(null, result -> {
            if (result) {
            	uiManager.getRewardController().giveFertilizerTickets(2);
            }
            System.out.println("게임 결과: " + result);
            frame.dispose();
        });

        frame.setContentPane(screen);
        frame.setSize(400, 450);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        new Thread(() -> Basket_game.startGame(screen)).start();
    }
}
