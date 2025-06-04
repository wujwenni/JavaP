package ui.component;

import javax.swing.*;
import java.awt.*;

public class ModalBuilder {
	private final JFrame owner;
    private JPanel content;
    private int width = 300;
    private int height = 200;
    private Runnable onClose = () -> {};

    private ModalBuilder(JFrame owner) {
        this.owner = owner;
    }

    public static ModalBuilder create(JFrame owner) {
        return new ModalBuilder(owner);
    }

    public ModalBuilder content(JPanel content) {
        this.content = content;
        return this;
    }

    public ModalBuilder size(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public ModalBuilder onClose(Runnable onClose) {
        this.onClose = onClose;
        return this;
    }

    public void buildAndShow() {
        JPanel glass = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0, 0, 0, 100));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        glass.setOpaque(false);
        glass.setLayout(new GridBagLayout());

        JButton close = ButtonBuilder.create("✕")
            .Border()
            .background(Color.RED)
            .foreground(Color.WHITE)
            .onClick(e -> {
                glass.setVisible(false);
                onClose.run();
            })
            .build();

        JPanel top = PanelBuilder.create()
            .flowLayout(FlowLayout.RIGHT)
            .opaque(false)
            .add(close)
            .build();

        JPanel modalWrapper = PanelBuilder.create()
            .borderLayout()
            .size(width, height)
            .background(Color.WHITE)
            .add(top, BorderLayout.NORTH)
            .add(content, BorderLayout.CENTER)
            .build();

        glass.add(modalWrapper, new GridBagConstraints());
        owner.setGlassPane(glass);
        glass.setVisible(true);
    }
}
