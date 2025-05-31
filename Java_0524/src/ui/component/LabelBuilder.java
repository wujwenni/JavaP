package ui.component;

import javax.swing.*;
import java.awt.*;

public class LabelBuilder {
    private final JLabel label;

    private LabelBuilder(String text) {
        this.label = new JLabel(text);
    }

    
    public static LabelBuilder create(String text) {
        return new LabelBuilder(text);
    }

    
    public LabelBuilder fontSize(int size) {
        Font current = label.getFont();
        label.setFont(new Font(current.getName(), current.getStyle(), size));
        return this;
    }

    
    public LabelBuilder bold(boolean isBold) {
        Font current = label.getFont();
        label.setFont(new Font(current.getName(), isBold ? Font.BOLD : Font.PLAIN, current.getSize()));
        return this;
    }

   
    public LabelBuilder color(Color color) {
        label.setForeground(color);
        return this;
    }
    
    public LabelBuilder size(int width, int height) {
        Dimension d = new Dimension(width, height);
        label.setPreferredSize(d);
        label.setMaximumSize(d);
        label.setMinimumSize(d);
        return this;
    }
    
    public LabelBuilder alignCenter() {
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return this;
    }

    public LabelBuilder alignLeft() {
        label.setHorizontalAlignment(SwingConstants.LEFT);
        return this;
    }

    public LabelBuilder alignRight() {
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return this;
    }

    
    public JLabel build() {
        return label;
    }
}
