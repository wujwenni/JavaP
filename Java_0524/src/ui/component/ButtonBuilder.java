package ui.component;

import util.FontUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class ButtonBuilder {
    private String text;
    private boolean border;
    private Font font;
    private Color background;
    private Color foreground;
    private Dimension preferredSize;
    private boolean opaque = true;
    private List<ActionListener> listeners = new ArrayList<>();

    private boolean cooldownEnabled = false;
    private int cooldownMs = 0;

    public static ButtonBuilder create(String text) {
        ButtonBuilder builder = new ButtonBuilder();
        builder.text = text;
        return builder;
    }

    public ButtonBuilder font(Font font) {
        this.font = font;
        return this;
    }

    public ButtonBuilder fontSize(float size) {
        if (FontUtil.getDefaultFont() != null)
            this.font = FontUtil.getDefaultFont().deriveFont(size);
        return this;
    }

    public ButtonBuilder background(Color color) {
        this.background = color;
        return this;
    }

    public ButtonBuilder foreground(Color color) {
        this.foreground = color;
        return this;
    }

    public ButtonBuilder size(int width, int height) {
        this.preferredSize = new Dimension(width, height);
        return this;
    }

    public ButtonBuilder bold(boolean b) {
        if (font != null)
            this.font = font.deriveFont(b ? Font.BOLD : Font.PLAIN);
        return this;
    }

    public ButtonBuilder Border() {
        this.border = true;
        return this;
    }

    public ButtonBuilder opaque(boolean opaque) {
        this.opaque = opaque;
        return this;
    }

    public ButtonBuilder onClick(ActionListener l) {
        listeners.add(l);
        return this;
    }

    public ButtonBuilder cooldown(int ms) {
        this.cooldownEnabled = true;
        this.cooldownMs = ms;
        return this;
    }

    public JButton build() {
        JButton btn;

        if (cooldownEnabled) {
            // ✅ 쿨타임 + 시각 효과 포함된 버튼 생성
            btn = new JButton(text) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);

                    Object prop = getClientProperty("cooldownProgress");
                    if (prop instanceof Double && !isEnabled()) {
                        double progress = (Double) prop;
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setColor(new Color(0, 0, 0, 80));

                        int size = Math.min(getWidth(), getHeight()) - 4;
                        int x = (getWidth() - size) / 2;
                        int y = (getHeight() - size) / 2;

                        int angle = (int) (360 * progress);
                        g2.fillArc(x, y, size, size, 90, -angle);
                        g2.dispose();
                    }
                }
            };

            btn.addActionListener(e -> {
                if (!btn.isEnabled()) return;
                CooldownManager.getInstance().register(btn, cooldownMs, () -> {
                    for (ActionListener l : listeners) l.actionPerformed(e);
                });
            });

        } else {
            // 기본 버튼
            btn = new JButton(text);
            for (ActionListener l : listeners) {
                btn.addActionListener(l);
            }
        }

        // 공통 속성 적용
        if (font != null) btn.setFont(font);
        if (background != null) btn.setBackground(background);
        if (foreground != null) btn.setForeground(foreground);
        if (preferredSize != null) btn.setPreferredSize(preferredSize);
        if (border) btn.setBorderPainted(border);
        btn.setOpaque(opaque);

        return btn;
    }

}
