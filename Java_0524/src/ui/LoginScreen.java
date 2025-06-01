package ui;

import controller.UserController;
import data.transfer.UserDataTransfer;
import ui.component.*;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends BaseScreen {
   

    public LoginScreen(UIManager uiManager) {
        super(uiManager);
        setBackgroundImage("backgrounds/login_backgroundimage.png");
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());

        JPanel panel = PanelBuilder.create()
        		.gridBagLayout()
        		.opaque(false)
        		.build();

        // Title
        JLabel title = LabelBuilder.create("로그인")
        	.font("/fonts/SUITE-Medium.ttf", 30f)
            .bold(true)
            .alignCenter()
            .build();
        panel.add(title, gbc(0, 0, 2, GridBagConstraints.CENTER));

        // 아이디
        JLabel labelId = LabelBuilder.create("아이디")
        		.font("/fonts/SUITE-Medium.ttf", 22f)
        		.bold(true)
        		.build();
        JTextField tfId = InputFieldBuilder.create()
        		.font("/fonts/SUITE-Medium.ttf", 14f)
        		.size(200, 30)
        		.build();
        panel.add(labelId, gbc(0, 1, 1, GridBagConstraints.EAST));
        panel.add(tfId, gbc(1, 1, 1, GridBagConstraints.WEST));

        // 비밀번호
        JLabel labelPw = LabelBuilder.create("비밀번호")
        		.font("/fonts/SUITE-Medium.ttf", 22f)
        		.bold(true)
        		.build();
        JPasswordField tfPw = (JPasswordField) InputFieldBuilder.password()
        		.fontSize(14)
        		.size(200, 30)
        		.build();
        panel.add(labelPw, gbc(0, 2, 1, GridBagConstraints.EAST));
        panel.add(tfPw, gbc(1, 2, 1, GridBagConstraints.WEST));

        // 버튼
        JButton btnLogin = ButtonBuilder.create("로그인")
        		.font("/fonts/SUITE-Medium.ttf", 14f)	
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

                UserDataTransfer dto = new UserDataTransfer(id, pw);
                UserController controller = uiManager.getUserController();
                if (controller.login(dto)) {
                    JOptionPane.showMessageDialog(this, "로그인 성공!");
                    OwnedPlantListModalScreen.show(uiManager, (JFrame) SwingUtilities.getWindowAncestor(this));
                } else {
                    JOptionPane.showMessageDialog(this, "로그인 실패! 아이디/비밀번호를 확인하세요.");
                }
            })
            .build();

        JButton btnJoin = ButtonBuilder.create("회원가입")
        		.font("/fonts/SUITE-Medium.ttf", 14f)	
        		.bold(true)
             	.size(120, 35)
                .background(Color.white)
                .foreground(Color.BLACK)
                .onClick(e -> uiManager.showScreen(new JoinScreen(uiManager)))
                .build();

        panel.add(btnJoin, gbc(0, 3, 1, GridBagConstraints.EAST));
        panel.add(btnLogin, gbc(1, 3, 1, GridBagConstraints.WEST));

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
