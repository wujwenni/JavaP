package ui.minigames;

import javax.swing.*;
import games.Basket_game;
import ui.UIManager;
// 식물 관리 화면에서 미니게임 버튼을 누르면 미니게임 선택 창이 새 창으로 열리고
// 미니게임 선택 창의 게임 버튼을 클릭하면 해당 게임의 런처를 통해 새 창에서 게임이 실행됨.
public class BasketGameLauncher {
	
    public static void launch(UIManager uiManager) {
        JFrame frame = new JFrame("바구니 게임");
        BasketGameScreen screen = new BasketGameScreen(null, result -> {
            if (result) {
            	uiManager.getRewardController().giveFertilizerTickets(2); // 바구니 게임의 결과가 성공일 때 비료 티켓 두 개 지급.
            }
            //System.out.println("게임 결과: " + result);
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
