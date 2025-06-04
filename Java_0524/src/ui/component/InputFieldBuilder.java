package ui.component;

import javax.swing.*;

import util.FontUtil;

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

    public InputFieldBuilder fontSize(float size) {
        Font defaultFont = FontUtil.getDefaultFont();
        if (defaultFont != null) {
            textField.setFont(defaultFont.deriveFont(size));
        }
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
