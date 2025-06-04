package ui;

import data.transfer.UserDataTransfer;
import ui.component.*;
import javax.swing.*;
import controller.UserController;
import java.awt.*;

public class JoinScreen extends BaseScreen {
    
    public JoinScreen(UIManager uiManager) {
        super(uiManager);
        setBackgroundImage("backgrounds/login_backgroundimage.png");
        initialize();
    }
    
    @Override
    protected void initialize() {
        setLayout(new BorderLayout());
        JPanel panel = PanelBuilder.create()
        		.gridBagLayout()
        		.opaque(false)
        		.build();
        // 제목
        JLabel title = LabelBuilder.create("회원가입")
            .fontSize(20)
            .bold(true)
            .alignCenter()
            .opaque(false)
            .build();
        panel.add(title, gbc(0, 0, 2, GridBagConstraints.CENTER));

        // 아이디
        JLabel labelId = LabelBuilder.create("아이디")
        		.fontSize(22)
        		.opaque(false)
        		.build();
        JTextField tfId = InputFieldBuilder.create()
            .fontSize(14)
            .size(200, 30)
            .build();
        panel.add(labelId, gbc(0, 1, 1, GridBagConstraints.EAST));
        panel.add(tfId, gbc(1, 1, 1, GridBagConstraints.WEST));

        // 비밀번호
        JLabel labelPw = LabelBuilder.create("비밀번호")
        		.fontSize(22)
        		.opaque(false)
        		.build();
        JPasswordField tfPw = (JPasswordField) InputFieldBuilder.password()
            .fontSize(14)
            .size(200, 30)
            .build();
        panel.add(labelPw, gbc(0, 2, 1, GridBagConstraints.EAST));
        panel.add(tfPw, gbc(1, 2, 1, GridBagConstraints.WEST));

        // 버튼
        JButton btnRegister = ButtonBuilder.create("가입하기")
        		.fontSize(14)
             	.bold(true)
             	.size(120, 35)
                .background(Color.white)
                .foreground(Color.BLACK)
            .onClick(e -> {
                String id = tfId.getText().trim();
                String pw = new String(tfPw.getPassword());

                if (id.isEmpty() || pw.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "ID와 비밀번호를 입력하세요.");
                    return;
                }
                UserController controller = uiManager.getUserController();
                UserDataTransfer dto = new UserDataTransfer(id, pw);
                boolean success = controller.register(dto);

                if (success) {
                    JOptionPane.showMessageDialog(this, "회원가입 성공! 로그인 화면으로 이동합니다.");
                    uiManager.showScreen(new LoginScreen(uiManager));
                } else {
                    JOptionPane.showMessageDialog(this, "이미 존재하는 ID입니다.");
                }
            })
            .build();

        JButton btnBack = ButtonBuilder.create("뒤로가기")
        		.fontSize(14)
             	.bold(true)
             	.size(120, 35)
                .background(Color.white)
                .foreground(Color.BLACK)
            .onClick(e -> uiManager.showScreen(new LoginScreen(uiManager)))
            .build();

        panel.add(btnBack, gbc(0, 3, 1, GridBagConstraints.EAST));
        panel.add(btnRegister, gbc(1, 3, 1, GridBagConstraints.WEST));

        add(panel, BorderLayout.CENTER);
        
    }

    private GridBagConstraints gbc(int x, int y, int width, int anchor) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.anchor = anchor;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        return gbc;
    }
}
