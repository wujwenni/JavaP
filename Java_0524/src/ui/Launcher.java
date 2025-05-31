package ui;

import controller.UserController;
import manager.UserManager;

import javax.swing.*;

public class Launcher {

    public static void launch() {
        SwingUtilities.invokeLater(() -> {
            // 1. 메인 프레임 생성
            JFrame frame = new JFrame("Green Friend");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 500);
            frame.setLocationRelativeTo(null); // 화면 중앙
            frame.setResizable(true);

            // 2. UI 전환 관리자 생성
            UIManager uiManager = new UIManager(frame);

            // 3. 컨트롤러 및 매니저 연결

            // 4. 첫 화면 설정 (로그인 화면)
            LoginScreen loginScreen = new LoginScreen(uiManager);
            frame.setContentPane(loginScreen);
            frame.setVisible(true);
        });
    }
}
