package ui.component;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class PanelBuilder {
    private final JPanel panel;

    private PanelBuilder(JPanel panel) {
        this.panel = panel;
    }

    /** 기존 빈 JPanel 생성용 */
    public static PanelBuilder create() {
        return new PanelBuilder(new JPanel());
    }
    
    public static PanelBuilder create(JPanel customPanel) {
        return new PanelBuilder(customPanel);
    }
    // 레이아웃 지정
    public PanelBuilder borderLayout() {
        panel.setLayout(new BorderLayout());
        return this;
    }
    
    public PanelBuilder boxLayout(int axis) {
        panel.setLayout(new BoxLayout(panel, axis));
        return this;
    }

    public PanelBuilder flowLayout(int align) {
        panel.setLayout(new FlowLayout(align));
        return this;
    }
    
    public PanelBuilder flowLayout(int align, int vgap, int hgap) {
        panel.setLayout(new FlowLayout(align, vgap, hgap));
        return this;
    }

    public PanelBuilder gridLayout(int rows, int cols, int hgap, int vgap) {
        panel.setLayout(new GridLayout(rows, cols, hgap, vgap));
        return this;
    }
    
    public PanelBuilder gridLayout() {
        panel.setLayout(new GridLayout());
        return this;
    }
    
    
    public PanelBuilder gridBagLayout() {
        panel.setLayout(new GridBagLayout());
        return this;
    }
    
    public PanelBuilder nullLayout() {
        panel.setLayout(null);
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
