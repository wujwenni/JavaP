package games;

import ui.minigames.ColorGameUI;

import javax.swing.*;
import java.util.Random;

public class Color_game {

    public static void startGame(ColorGameUI ui) {
        int attempts = 3;
        int correctCount = 0;

        // 카운트다운
        for (int i = 3; i >= 1; i--) {
            ui.showColor(0);
            ui.showCountdownNumber(String.valueOf(i));
            sleep(1000);
        }
        ui.clearCountdownNumber();
        ui.showColor(0);

        Random rand = new Random();

        // 최대 3번 도전
        for (int round = 0; round < attempts; round++) {
            int[] correctSequence = new int[3];

            for (int i = 0; i < 3; i++) {
                correctSequence[i] = rand.nextInt(5) + 1;
                ui.showColor(correctSequence[i]);
                sleep(300);
                ui.showColor(0);
                sleep(100);
            }

            int[] userSequence = new int[3];
            for (int i = 0; i < 3; i++) {
                userSequence[i] = ui.getUserInput();
            }

            boolean isCorrect = true;
            for (int i = 0; i < 3; i++) {
                if (userSequence[i] != correctSequence[i]) {
                    isCorrect = false;
                    break;
                }
            }

            if (isCorrect) {
                correctCount++;
                JOptionPane.showMessageDialog(null, "정답입니다! +2 티켓");
            } else {
                JOptionPane.showMessageDialog(null, "오답입니다.");
            }
        }

        int totalReward = correctCount * 2;
        boolean success = correctCount >= 2;

        JOptionPane.showMessageDialog(null,
                "게임 종료!\n획득한 티켓: " + totalReward +
                "\n" + (success ? "성공!" : "실패!"));

        // UI에 결과 전달 (성공 여부)
        if (ui instanceof ui.minigames.ColorGameScreen screen) {
            screen.getResultCallback().accept(totalReward);
        }
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
