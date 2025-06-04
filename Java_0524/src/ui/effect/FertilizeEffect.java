package ui.effect;

import javax.swing.*;
import java.util.Random;

public class FertilizeEffect {

    public static void play(JPanel panel) {
        int sparkleCount = 10;
        Random rand = new Random();

        for (int i = 0; i < sparkleCount; i++) {
            int size = 40 + rand.nextInt(20);
            int x = 75 + rand.nextInt(300);
            int y = 430 + rand.nextInt(100);

            SparkleEffectManager.addSparkle(panel, x, y, size);
        }
    }
}
