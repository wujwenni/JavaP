package ui;

import ui.component.ButtonBuilder;
import ui.component.LabelBuilder;
import ui.minigames.BasketGameLauncher;
import ui.minigames.ColorGameLauncher;

import javax.swing.*;
import java.awt.*;

public class MiniGameScreen extends BaseScreen {

    private volatile boolean gameRunning = false;

    public MiniGameScreen(UIManager uiManager) {
        super(uiManager);
        initialize();
    }

    @Override
    protected void initialize() {
        setLayout(new BorderLayout());

        //상단 제목
        JLabel titleLabel = LabelBuilder.create("미니게임을 선택하세요!")
                .fontSize(20)
                .bold(true)
                .alignCenter()
                .build();
        add(titleLabel, BorderLayout.NORTH);

        //중앙 버튼 패널
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 10, 50));

        JButton colorGameBtn = ButtonBuilder.create("색깔 기억 게임")
                .fontSize(24)
                .background(new Color(207, 233, 99))
                .onClick(e -> runGameInThread(() -> ColorGameLauncher.launch(uiManager)))
                .build();

        JButton basketGameBtn = ButtonBuilder.create("바구니 게임")
                .fontSize(24)
                .background(new Color(207, 233, 99))
                .onClick(e -> runGameInThread(() -> BasketGameLauncher.launch(uiManager)))
                .build();

        //colorGameBtn.addActionListener(e -> runGameInThread(() -> ColorGameLauncher.launch(uiManager)));
        //basketGameBtn.addActionListener(e -> runGameInThread(() -> BasketGameLauncher.launch(uiManager)));

        buttonPanel.add(colorGameBtn);
        buttonPanel.add(basketGameBtn);

        add(buttonPanel, BorderLayout.CENTER);

        
    }

    private void runGameInThread(Runnable gameLogic) {
        synchronized (this) {
            if (gameRunning) return;
            gameRunning = true;
        }

        new Thread(() -> {
            gameLogic.run();
            synchronized (this) {
                gameRunning = false;
            }
        }).start();
    }
}
