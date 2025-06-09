package ui.component;

import javax.swing.*;
import java.awt.*;

public class OptionPaneBuilder {

    public enum Type { MESSAGE, CONFIRM, INPUT }

    private Component parent = null;
    private String title = "알림";
    private String message = "";
    private Type type = Type.MESSAGE;

    private Runnable onConfirm = () -> {};
    private Runnable onCancel = () -> {};

    private OptionPaneBuilder() {}

    public static OptionPaneBuilder create() {
        return new OptionPaneBuilder();
    }

    public OptionPaneBuilder parent(Component parent) {
        this.parent = parent;
        return this;
    }

    public OptionPaneBuilder title(String title) {
        this.title = title;
        return this;
    }

    public OptionPaneBuilder message(String message) {
        this.message = message;
        return this;
    }

    public OptionPaneBuilder type(Type type) {
        this.type = type;
        return this;
    }

    public OptionPaneBuilder onConfirm(Runnable action) {
        this.onConfirm = action;
        return this;
    }

    public OptionPaneBuilder onCancel(Runnable action) {
        this.onCancel = action;
        return this;
    }

    public void show() {
        switch (type) {
            case MESSAGE -> {
                JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
            }
            case CONFIRM -> {
                int result = JOptionPane.showConfirmDialog(
                    parent, message, title, JOptionPane.YES_NO_OPTION
                );
                if (result == JOptionPane.YES_OPTION) onConfirm.run();
                else onCancel.run();
            }
            case INPUT -> {
                String input = JOptionPane.showInputDialog(parent, message);
                if (input != null && !input.trim().isEmpty()) onConfirm.run();
                else onCancel.run();
            }
        }
    }
}
