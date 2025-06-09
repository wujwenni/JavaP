package ui.component;


import util.FontUtil;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class LabelBuilder {
    private final JLabel label;

    private LabelBuilder(String text) {
        this.label = new JLabel(text);
    }

    public static LabelBuilder create(String text) {
        return new LabelBuilder(text);
    }
    
    public LabelBuilder icon(String relativePath, int width, int height) {
        URL resource = getClass().getClassLoader().getResource(relativePath);
        if (resource != null) {
            ImageIcon rawIcon = new ImageIcon(resource);
            Image scaled = rawIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaled));
        } else {
            System.err.println("이미지 로딩 실패: " + relativePath);
        }
        return this;
    }
    
    public LabelBuilder icon(String relativePath) {
        URL resource = getClass().getClassLoader().getResource(relativePath);
        if (resource != null) {
            ImageIcon rawIcon = new ImageIcon(resource);
            label.setIcon(rawIcon);
        } else {
            System.err.println("이미지 로딩 실패: " + relativePath);
        }
        return this;
    }
    
    public LabelBuilder bounds(int x, int y, int width, int height) {
        label.setBounds(x, y, width, height);
        return this;
    }
    
    public LabelBuilder setBorder(Color color, int size) {
    	label.setBorder(BorderFactory.createLineBorder(color, size));
    	return this;
    }
    
    public LabelBuilder opaque(boolean opaque) {
        label.setOpaque(opaque);
        return this;
    }
    
    public LabelBuilder background(Color color) {
    	label.setBackground(color);
    	return this;
    }
    
    public LabelBuilder fontSize(float size) {
        Font defaultFont = FontUtil.getDefaultFont();
        if (defaultFont != null) {
            label.setFont(defaultFont.deriveFont(size));
        }
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
    
    public LabelBuilder verticalAlignment(int alignment) {
        label.setVerticalAlignment(alignment);
        return this;
    }

    
    public JLabel build() {
        return label;
    }
}
