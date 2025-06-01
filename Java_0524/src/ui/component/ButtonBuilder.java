package ui.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonBuilder {
    private final JButton button;

    private ButtonBuilder(String text) {
        this.button = new JButton(text);
    }

    // 정적 생성자
    public static ButtonBuilder create(String text) {
        return new ButtonBuilder(text);
    }

    // 배경색 설정
    public ButtonBuilder background(Color color) {
        button.setBackground(color);
        button.setOpaque(true);
        return this;
    }

    // 텍스트 색상 설정
    public ButtonBuilder foreground(Color color) {
        button.setForeground(color);
        return this;
    }

    // 폰트 크기 설정
    public ButtonBuilder fontSize(int size) {
        Font current = button.getFont();
        button.setFont(new Font(current.getName(), current.getStyle(), size));
        return this;
    }
    
    public ButtonBuilder font(String path, float size) {
        Font font = util.FontUtil.loadFont(path, size); // FontUtil 경로에 맞게 수정
        button.setFont(font);
        return this;
    }

    // 굵기 설정
    public ButtonBuilder bold(boolean isBold) {
        Font current = button.getFont();
        button.setFont(new Font(current.getName(), isBold ? Font.BOLD : Font.PLAIN, current.getSize()));
        return this;
    }

    // 테두리 제거
    public ButtonBuilder noBorder() {
        button.setBorderPainted(false);
        return this;
    }
    
    public ButtonBuilder size(int width, int height) {
        Dimension d = new Dimension(width, height);
        button.setPreferredSize(d);
        button.setMaximumSize(d);
        button.setMinimumSize(d);
        return this;
    }

    // 이벤트 리스너 등록
    public ButtonBuilder onClick(ActionListener listener) {
        button.addActionListener(listener);
        return this;
    }

    // 정렬 설정 (좌/중앙/우)
    public ButtonBuilder alignCenter() {
        button.setHorizontalAlignment(SwingConstants.CENTER);
        return this;
    }
    
    public ButtonBuilder opaque(boolean opaque) {
        button.setOpaque(opaque);
        return this;
    }

    // 최종 버튼 반환
    public JButton build() {
        return button;
    }
}
