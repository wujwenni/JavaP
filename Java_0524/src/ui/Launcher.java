package ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

import controller.PlantManagementController;
import ui.component.OptionPaneBuilder;

public class Launcher {

    // 🌱 메인 애플리케이션 실행
    public static void launch() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = createMainFrame("Green Friend", 800, 900);
            UIManager uiManager = new UIManager(frame);
            EntryScreen entryScreen = new EntryScreen(uiManager);
            PlantManagementController controller = uiManager.getplantManagementController();
            frame.setContentPane(entryScreen);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    OptionPaneBuilder.create()
                        .parent(frame)
                        .title("종료 확인")
                        .message("정말 종료하시겠습니까?")
                        .type(OptionPaneBuilder.Type.CONFIRM)
                        .onConfirm(() -> {
                            controller.handleSave();
                            frame.dispose();
                            System.exit(0);
                        })
                        .show();
                }
            });

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
    
    public static void launchGardenScreen(UIManager uiManager) {
    	SwingUtilities.invokeLater(() -> {
            JFrame gardenFrame = createSubFrame("화단", 1600, 900);
            GardenScreen gardenScreen = new GardenScreen(uiManager);
            gardenFrame.setContentPane(gardenScreen);
            gardenFrame.setVisible(true);
        });
    }
    
    public static void launchChatScreen(UIManager uiManager, Runnable onCloseCallback) {
        JFrame frame = new JFrame("Chat");
        ChattingScreen screen = new ChattingScreen(uiManager);
        screen.setOnCloseCallback(onCloseCallback); // 창 닫힐 때 실행할 콜백 등록
        frame.setContentPane(screen);
        frame.setSize(400, 600);
        frame.setVisible(true);
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
