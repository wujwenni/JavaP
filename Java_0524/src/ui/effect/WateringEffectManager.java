package ui.effect;

import ui.component.FadeImageLabel;
import ui.component.FadeImageLabelBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class WateringEffectManager {
    private static class WateringInstance {
        FadeImageLabel label;
        JPanel panel;
        float alpha = 0f;
        boolean fadingIn = true;
        int holdTime = 500; // milliseconds
        int elapsedHold = 0;
    }

    private static final List<WateringInstance> instances = new LinkedList<>();
    private static final Timer timer;

    static {
        timer = new Timer(40, e -> update());
        timer.start();
    }

    public static void play(JPanel panel) {
        WateringInstance inst = new WateringInstance();
        inst.label = FadeImageLabelBuilder.create()
                .icon("components/labels/water.png", 200, 200)
                .bounds(30, 300)
                .alpha(0f)
                .build();

        inst.panel = panel;
        panel.add(inst.label);
        panel.setComponentZOrder(inst.label, 0);
        panel.repaint();

        synchronized (instances) {
            instances.add(inst);
        }
    }

    private static void update() {
        synchronized (instances) {
            Iterator<WateringInstance> it = instances.iterator();
            while (it.hasNext()) {
                WateringInstance inst = it.next();
                if (inst.fadingIn) {
                    inst.alpha += 0.05f;
                    if (inst.alpha >= 1f) {
                        inst.alpha = 1f;
                        inst.fadingIn = false;
                    }
                } else if (inst.elapsedHold < inst.holdTime) {
                    inst.elapsedHold += 40;
                } else {
                    inst.alpha -= 0.05f;
                    if (inst.alpha <= 0f) {
                        inst.panel.remove(inst.label);
                        inst.panel.repaint();
                        it.remove();
                        continue;
                    }
                }

                inst.label.setAlpha(inst.alpha);
                inst.panel.repaint();
            }
        }
    }
}
