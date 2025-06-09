package ui.minigames;

import games.Color_game;

import javax.swing.*;
import java.util.function.Consumer;
import ui.UIManager;

public class ColorGameLauncher {
	
    public static void launch(UIManager uiManager) {
        JFrame frame = new JFrame("색깔 기억 게임");
        ColorGameScreen screen = new ColorGameScreen(null, result -> {
        	uiManager.getRewardController().giveWaterTickets(result);
        	System.out.println("게임 결과: " + result);
            frame.dispose();
        });

        frame.setContentPane(screen);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        new Thread(() -> Color_game.startGame(screen)).start();  // 게임 실행 로직은 기존 그대로
    }
}
