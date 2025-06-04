package ui.component;

import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

public class CooldownManager {
    private static final CooldownManager instance = new CooldownManager();
    private final Map<JButton, Integer> cooldownMap = new HashMap<>();
    private final Timer timer;
    private final Map<JButton, Integer> cooldownMaxMap = new HashMap<>();

    private CooldownManager() {
        timer = new Timer(30, e -> tick());
        timer.start();
    }

    public static CooldownManager getInstance() {
        return instance;
    }

    public void register(JButton btn, int cooldownMs, Runnable onClick) {
        if (cooldownMap.containsKey(btn)) return;

        btn.setEnabled(false);
        cooldownMap.put(btn, cooldownMs);
        cooldownMaxMap.put(btn, cooldownMs);
        onClick.run();
    }

    private void tick() {
        Iterator<Map.Entry<JButton, Integer>> it = cooldownMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<JButton, Integer> entry = it.next();
            JButton btn = entry.getKey();
            int timeLeft = entry.getValue() - 30;
            int max = cooldownMaxMap.get(btn);

            if (timeLeft <= 0) {
                btn.setEnabled(true);
                it.remove();
                cooldownMaxMap.remove(btn);
                btn.putClientProperty("cooldownProgress", null);
            } else {
                cooldownMap.put(btn, timeLeft);
                double progress = (double) timeLeft / max;
                btn.putClientProperty("cooldownProgress", progress); // 0.0~1.0
                btn.repaint();
            }
        }
    }

}
