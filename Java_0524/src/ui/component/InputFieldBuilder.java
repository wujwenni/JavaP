package ui.component;

import javax.swing.*;
import java.awt.*;

public class InputFieldBuilder {
    private final JTextField textField;
    private boolean isPassword = false;

    private InputFieldBuilder(boolean passwordMode) {
        this.isPassword = passwordMode;
        this.textField = passwordMode ? new JPasswordField() : new JTextField();
    }

    // 정적 생성자
    public static InputFieldBuilder create() {
        return new InputFieldBuilder(false);
    }

    public static InputFieldBuilder password() {
        return new InputFieldBuilder(true);
    }

    public InputFieldBuilder columns(int cols) {
        textField.setColumns(cols);
        return this;
    }

    public InputFieldBuilder fontSize(int size) {
        Font current = textField.getFont();
        textField.setFont(new Font(current.getName(), current.getStyle(), size));
        return this;
    }
    
    public InputFieldBuilder font(String path, float size) {
        Font font = util.FontUtil.loadFont(path, size); // FontUtil 경로에 맞게 수정
        textField.setFont(font);
        return this;
    }

    public InputFieldBuilder textColor(Color color) {
        textField.setForeground(color);
        return this;
    }

    public InputFieldBuilder background(Color color) {
        textField.setBackground(color);
        return this;
    }

    public InputFieldBuilder editable(boolean editable) {
        textField.setEditable(editable);
        return this;
    }
    
    public InputFieldBuilder size(int width, int height) {
        Dimension d = new Dimension(width, height);
        textField.setPreferredSize(d);
        textField.setMaximumSize(d);
        textField.setMinimumSize(d);
        return this;
    }

    // 최종 필드 반환
    public JTextField build() {
        return textField;
    }
}
