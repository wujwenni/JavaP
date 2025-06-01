package ui;

import javax.swing.*;

public class Launcher {

    // 🌱 메인 애플리케이션 실행
    public static void launch() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = createMainFrame("Green Friend", 800, 900);
            UIManager uiManager = new UIManager(frame);
            LoginScreen loginScreen = new LoginScreen(uiManager);
            frame.setContentPane(loginScreen);
            frame.setVisible(true);
        });
    }

    // 🎮 미니게임 선택 화면 실행
    public static void launchMiniGameSelection(UIManager uiManager) {
        SwingUtilities.invokeLater(() -> {
            JFrame gameFrame = createSubFrame("미니게임 선택", 400, 300);
            MiniGameScreen miniGameScreen = new MiniGameScreen(uiManager);
            gameFrame.setContentPane(miniGameScreen);
            gameFrame.setVisible(true);
        });
    }

    // ✅ 메인 프레임 생성
    private static JFrame createMainFrame(String title, int width, int height) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        return frame;
    }

    // ✅ 서브 프레임 생성 (미니게임용)
    private static JFrame createSubFrame(String title, int width, int height) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        return frame;
    }
}
