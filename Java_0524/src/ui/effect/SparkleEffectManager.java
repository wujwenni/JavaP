package ui.effect;

import ui.component.FadeImageLabel;
import ui.component.FadeImageLabelBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class SparkleEffectManager {
    private static final List<FadeImageLabel> activeSparkles = new ArrayList<>();
    private static final Timer timer;

    static {
        timer = new Timer(40, e -> update());
        timer.start();
    }

    public static void addSparkle(JPanel panel, int x, int y, int size) {
        FadeImageLabel sparkle = FadeImageLabelBuilder.create()
                .icon("components/labels/sparkle.png", size, size)
                .bounds(x, y)
                .alpha(1f)
                .build();

        panel.add(sparkle);
        panel.setComponentZOrder(sparkle, 0);
        panel.repaint();
        sparkle.putClientProperty("alpha", 1f);
        sparkle.putClientProperty("panel", panel);

        synchronized (activeSparkles) {
            activeSparkles.add(sparkle);
        }
    }

    private static void update() {
        synchronized (activeSparkles) {
            Iterator<FadeImageLabel> it = activeSparkles.iterator();
            while (it.hasNext()) {
                FadeImageLabel sparkle = it.next();
                float alpha = (float) sparkle.getClientProperty("alpha");
                alpha -= 0.05f;

                if (alpha <= 0f) {
                    it.remove();
                    JPanel panel = (JPanel) sparkle.getClientProperty("panel");
                    panel.remove(sparkle);
                    panel.repaint();
                } else {
                    sparkle.setAlpha(alpha);
                    sparkle.putClientProperty("alpha", alpha);
                }
            }
        }
    }
}
