package ui.component;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class PanelBuilder {
    private final JPanel panel;

    private PanelBuilder() {
        this.panel = new JPanel();
    }

    public static PanelBuilder create() {
        return new PanelBuilder();
    }

    // 레이아웃 지정
    public PanelBuilder borderLayout() {
        panel.setLayout(new BorderLayout());
        return this;
    }

    public PanelBuilder flowLayout(int align) {
        panel.setLayout(new FlowLayout(align));
        return this;
    }

    public PanelBuilder gridLayout(int rows, int cols, int hgap, int vgap) {
        panel.setLayout(new GridLayout(rows, cols, hgap, vgap));
        return this;
    }

    // 배경색
    public PanelBuilder background(Color color) {
        panel.setBackground(color);
        return this;
    }

    // 패딩 (EmptyBorder)
    public PanelBuilder padding(int topBottomLeftRight) {
        panel.setBorder(BorderFactory.createEmptyBorder(
            topBottomLeftRight, topBottomLeftRight, topBottomLeftRight, topBottomLeftRight
        ));
        return this;
    }
    
    public PanelBuilder size(int width, int height) {
        Dimension d = new Dimension(width, height);
        panel.setPreferredSize(d);
        panel.setMaximumSize(d);
        panel.setMinimumSize(d);
        return this;
    }

    // 테두리 제목
    public PanelBuilder titledBorder(String title) {
        panel.setBorder(BorderFactory.createTitledBorder(title));
        return this;
    }
    
    public PanelBuilder opaque(boolean opaque) {
        panel.setOpaque(opaque);
        return this;
    }
    
    public PanelBuilder add(Component comp) {
        panel.add(comp);
        return this;
    }

    public PanelBuilder add(Component comp, Object constraints) {
        panel.add(comp, constraints);
        return this;
    }
    
    public JPanel build() {
        return panel;
    }
}
