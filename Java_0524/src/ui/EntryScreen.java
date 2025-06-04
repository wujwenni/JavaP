package ui;


import java.awt.*;
import javax.swing.*;

import ui.component.*;

public class EntryScreen extends BaseScreen {

	public EntryScreen(UIManager uiManager) {
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

	        // Title
	        JLabel title = LabelBuilder.create("")
	        	    .icon("components/labels/titlelabel.png", 480, 330)  // 실제 경로와 크기에 맞게 조정
	        	    .alignCenter()
	        	    .build();
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.gridx = 0;
	        gbc.gridy = 0;
	        gbc.gridwidth = 1;
	        gbc.anchor = GridBagConstraints.CENTER;
	        gbc.insets = new Insets(20, 0, 20, 0);

	        panel.add(title, gbc);

	        add(panel, BorderLayout.CENTER);
	        
	        JButton startButton = ButtonBuilder.create("START")
	        		.fontSize(40f)
	        		.size(300, 150)
	                .background(Color.WHITE)
	                .foreground(Color.BLACK)
	                .onClick(e -> uiManager.showScreen(new LoginScreen(uiManager)))
	                .build();
	        
	        gbc.gridy = 1; // title 아래에 버튼 위치
	        gbc.insets = new Insets(40, 0, 0, 0); // 버튼 위쪽 여백

	        panel.add(startButton, gbc);

	        add(panel, BorderLayout.CENTER);
	}

}
