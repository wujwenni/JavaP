package ui.minigames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import ui.BaseScreen;
import ui.UIManager;

public class BasketGameScreen extends BaseScreen implements BasketGameUI, ActionListener, KeyListener {

    private final List<FallingObject> objects = new ArrayList<>();
    private final Timer timer = new Timer(100, this);
    private int userX = 150;
    private int life = 3;
    private int score = 0;
    private final Consumer<Boolean> resultCallback;

    public BasketGameScreen(UIManager uiManager, Consumer<Boolean> resultCallback) {
        super(uiManager);
        this.resultCallback = resultCallback;
        setFocusable(true);
        addKeyListener(this);
        timer.start();
    }

    private static class FallingObject {
        int x, y;
        boolean isGood;

        public FallingObject(int x, boolean isGood) {
            this.x = x;
            this.y = 0;
            this.isGood = isGood;
        }

        public void move() {
            y += 10;
        }
    }

    @Override
    protected void initialize() {
        // 필요시 레이아웃 설정 가능
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.ORANGE);
        g.fillRect(userX, 350, 60, 15);

        for (FallingObject obj : objects) {
            g.setColor(obj.isGood ? Color.RED : Color.BLACK);
            g.fillOval(obj.x, obj.y, 40, 40);
        }

        g.setColor(Color.BLACK);
        g.drawString("Life: " + life + " | Score: " + score, 10, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Math.random() < 0.1) {
            objects.add(new FallingObject((int)(Math.random() * 300), Math.random() < 0.5));
        }

        Iterator<FallingObject> iter = objects.iterator();
        while (iter.hasNext()) {
            FallingObject obj = iter.next();
            obj.move();
            if (obj.y + 40 >= 350 && obj.y <= 365 && obj.x + 40 >= userX && obj.x <= userX + 60) {
                if (obj.isGood) score++;
                else life--;
                iter.remove();
            } else if (obj.y > 400) {
                if (obj.isGood) life--;
                iter.remove();
            }
        }

        repaint();

        if (life <= 0) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "실패!");
            finishGame(false);
        } else if (score >= 5) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "성공!");
            finishGame(true);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            userX = Math.max(0, userX - 20);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            userX = Math.min(getWidth() - 60, userX + 20);
        }
        repaint();
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    // BasketGameUI 구현부
    @Override public void updateLife(int life) { this.life = life; }
    @Override public void updateScore(int score) { this.score = score; }
    @Override public void repaintGame() { repaint(); }
    @Override public void finishGame(boolean success) {
        resultCallback.accept(success);
    }
}
