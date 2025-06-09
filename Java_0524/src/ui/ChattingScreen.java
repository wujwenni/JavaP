package ui;

import data.transfer.PlantDataTransfer;
import service.ChatTracker;
import ui.component.*;
import util.FontUtil;

import javax.swing.*;

import controller.PlantManagementController;

import java.awt.*;
import java.awt.event.*;

public class ChattingScreen extends BaseScreen {

    private final ChatTracker tracker = new ChatTracker();
    private Runnable onCloseCallback;
    private JTextArea chatArea;
    private JTextField inputField;

    public ChattingScreen(UIManager uiManager) {
        super(uiManager);
        //setBackgroundImage("backgrounds/chat_background.png");
        initialize();
    }
    
    public void setOnCloseCallback(Runnable callback) {
        this.onCloseCallback = callback;
    }

    @Override
    protected void initialize() {
        setLayout(new BorderLayout());

        // 💬 상단 제목
        JLabel title = LabelBuilder.create("Chatting")
            .fontSize(17f)
            .bold(true)
            .alignCenter()
            .build();
        add(title, BorderLayout.NORTH);

        // 💬 채팅 영역
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(FontUtil.getDefaultFont().deriveFont(20f));
        chatArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        // 💬 하단 입력창 + 버튼
        inputField = new JTextField();
        JButton sendButton = ButtonBuilder.create("전송")
            .fontSize(14f)
            .background(Color.BLUE)
            .foreground(Color.WHITE)
            .size(80, 30)
            .onClick(e -> handleUserInput())
            .build();

        inputField.addActionListener(e -> handleUserInput()); // 엔터 입력 지원

        JPanel inputPanel = new JPanel(new BorderLayout(5, 0));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);
    }

    private void handleUserInput() {
        String input = inputField.getText().trim();
        if (input.isEmpty()) return;

        // 채팅창에 사용자 입력 추가
        chatArea.append("나: " + input + "\n");
        
        // 트래커로 처리
        tracker.processUserInput(input);


        inputField.setText("");
    }
    

    @Override
    public void addNotify() {
        super.addNotify();
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        SwingUtilities.getWindowAncestor(this).addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	
                OptionPaneBuilder.create()
                .title("종료 확인")
                .parent(frame)
                .message("정말 종료하시겠습니까?")
                .type(OptionPaneBuilder.Type.CONFIRM)
                .onConfirm(() -> {
                	frame.dispose();
                	if (onCloseCallback != null) onCloseCallback.run();
                	tracker.reset();
                })
                .show();
            }
        });
    }

}
