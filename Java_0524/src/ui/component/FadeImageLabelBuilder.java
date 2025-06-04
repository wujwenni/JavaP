package ui.component;

import ui.component.*;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class FadeImageLabelBuilder {
    private ImageIcon icon;
    private int x = 0, y = 0, width = 100, height = 100;
    private float initialAlpha = 0f;

    public static FadeImageLabelBuilder create() {
        return new FadeImageLabelBuilder();
    }

    public FadeImageLabelBuilder icon(String path, int width, int height) {
        URL resource = getClass().getClassLoader().getResource(path);
        if (resource != null) {
            Image image = new ImageIcon(resource).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            this.icon = new ImageIcon(image);
            this.width = width;
            this.height = height;
        } else {
            System.err.println("⚠️ 이미지 로딩 실패: " + path);
        }
        return this;
    }

    public FadeImageLabelBuilder bounds(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public FadeImageLabelBuilder alpha(float alpha) {
        this.initialAlpha = alpha;
        return this;
    }

    public FadeImageLabel build() {
        FadeImageLabel label = new FadeImageLabel(icon);
        label.setBounds(x, y, width, height);
        label.setAlpha(initialAlpha);
        return label;
    }
}
