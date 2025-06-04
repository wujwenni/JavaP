package ui.component;

import javax.swing.*;
import java.awt.*;

public class FadeImageLabel extends JLabel {
    private float alpha = 0f;

    public FadeImageLabel(ImageIcon icon) {
        super(icon);
        setOpaque(false);
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        super.paintComponent(g2);
        g2.dispose();
    }
}
