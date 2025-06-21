package ui;

import controller.UserPlantDatacontroller;
import data.transfer.PlantDataTransfer;
import ui.UIManager;
import ui.component.ButtonBuilder;
import ui.component.LabelBuilder;

import javax.swing.*;
import java.awt.*;

public class PlantSelectionScreen extends BaseScreen {

    public PlantSelectionScreen(UIManager uiManager) {
        super(uiManager);
        setBackgroundImage("backgrounds/login_backgroundimage.png");
        initialize();
    }

    @Override
    protected void initialize() {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        // 제목
        JLabel title = LabelBuilder.create("키울 식물을 선택하세요!")
            .fontSize(20)
            .bold(true)
            .alignCenter()
            .build();
        panel.add(title, gbc(0, 0, 3, GridBagConstraints.CENTER));

        // 식물 선택 버튼 3개
        panel.add(createPlantButton("장미"), gbc(0, 1, 1, GridBagConstraints.CENTER));
        panel.add(createPlantButton("튤립"), gbc(1, 1, 1, GridBagConstraints.CENTER));
        panel.add(createPlantButton("해바라기"), gbc(2, 1, 1, GridBagConstraints.CENTER));

        add(panel, BorderLayout.CENTER);
    }

    private JButton createPlantButton(String plantName) {
        UserPlantDatacontroller controller = uiManager.getUserPlantDataController();

        return ButtonBuilder.create(plantName)
            .fontSize(16)
            .bold(true)
            .size(140, 50)
            .background(plantName.equals("장미") 		? new Color(233, 60, 60)   :
            			plantName.equals("튤립") 		? new Color(233, 161, 136) :
            			plantName.equals("해바라기") 	? new Color(255, 255, 153) :
            										  new Color(207, 233, 99))
            .foreground(Color.BLACK)
            .onClick(e -> {
                PlantDataTransfer dto = controller.selectInitialPlant(plantName);
                if (dto != null) {
                    JOptionPane.showMessageDialog(this, plantName + "을(를) 선택하셨습니다!");
                    uiManager.showScreen(new PlantManagementScreen(uiManager, dto));
                }
            })
            .build();
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
