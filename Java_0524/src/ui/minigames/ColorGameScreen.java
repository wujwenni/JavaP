package ui.minigames;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

import ui.*;
import ui.UIManager;

public class ColorGameScreen extends BaseScreen implements ColorGameUI {
	private JPanel colorPanel;
    private JPanel buttonPanel;
    private JLabel countdownLabel;

    private int userClick = -1;
    private final Object lock = new Object();
    private final Consumer<Boolean> resultCallback;

    public ColorGameScreen(UIManager uiManager, Consumer<Boolean> resultCallback) {
        super(uiManager);
        this.resultCallback = resultCallback;
        initialize();
    }
    
    private void initialize() {
        setLayout(new BorderLayout());

        // 패널 구성
        colorPanel = new JPanel(new BorderLayout());
        colorPanel.setBackground(Color.WHITE);
        add(colorPanel, BorderLayout.CENTER);

        countdownLabel = new JLabel("", SwingConstants.CENTER);
        countdownLabel.setFont(new Font("Arial", Font.BOLD, 80));
        countdownLabel.setForeground(Color.DARK_GRAY);
        colorPanel.add(countdownLabel, BorderLayout.CENTER);

        buttonPanel = new JPanel(new GridLayout(1, 5));
        addColorButton("Red", Color.RED, 1);
        addColorButton("Yellow", Color.YELLOW, 2);
        addColorButton("Green", Color.GREEN, 3);
        addColorButton("Blue", Color.BLUE, 4);
        addColorButton("Black", Color.BLACK, 5);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addColorButton(String name, Color color, int code) {
        JButton btn = new JButton(name);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.addActionListener(e -> {
            synchronized (lock) {
                userClick = code;
                lock.notify();
            }
        });
        buttonPanel.add(btn);
    }

    public void showColor(int colorCode) {
        Color color = switch (colorCode) {
            case 1 -> Color.RED;
            case 2 -> Color.YELLOW;
            case 3 -> Color.GREEN;
            case 4 -> Color.BLUE;
            case 5 -> Color.BLACK;
            default -> Color.WHITE;
        };
        colorPanel.setBackground(color);
    }
    public int getUserInput() {
        userClick = -1;
        synchronized (lock) {
            try {
                while (userClick == -1) lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return userClick;
    }

    public void showCountdownNumber(String text) {
        countdownLabel.setText(text);
        colorPanel.repaint();
    }

    public void clearCountdownNumber() {
        countdownLabel.setText("");
        colorPanel.repaint();
    }

    public Consumer<Boolean> getResultCallback() {
        return resultCallback;
    }

}
