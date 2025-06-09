package games;

import ui.minigames.BasketGameUI;

import javax.swing.*;
import javax.swing.Timer;
import java.util.*;


public class Basket_game {

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

    public static void startGame(BasketGameUI ui) {
        List<FallingObject> objects = new ArrayList<>();
        int userX = 150;
        final int[] life  = {3};
        final int[] score = {0};

        Timer timer = new Timer(100, e -> { //Timer 1

            if (Math.random() < 0.1) {
                objects.add(new FallingObject((int)(Math.random() * 300), Math.random() < 0.5));
            }

            Iterator<FallingObject> iter = objects.iterator();
            while (iter.hasNext()) {
                FallingObject obj = iter.next();
                obj.move();

                if (obj.y + 40 >= 350 && obj.y <= 365 && obj.x + 40 >= userX && obj.x <= userX + 60) {
                    if (obj.isGood) score[0]++;
                    else life[0]--;
                    iter.remove();
                } else if (obj.y > 400) {
                    if (obj.isGood) life[0]--;
                    iter.remove();
                }
            }

            ui.updateLife(life[0]);
            ui.updateScore(score[0]);
            ui.repaintGame();

            if (life[0] <= 0) {
                ((Timer)e.getSource()).stop();
                JOptionPane.showMessageDialog(null, "실패!");
                ui.finishGame(false);
            } else if (score[0] >= 5) {
                ((Timer)e.getSource()).stop();
                JOptionPane.showMessageDialog(null, "성공!");
                ui.finishGame(true);
            }
        });

        
    }
}
