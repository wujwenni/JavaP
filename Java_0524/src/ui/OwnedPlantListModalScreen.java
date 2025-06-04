package ui;

import controller.UserPlantDatacontroller;
import data.transfer.PlantDataTransfer;
import data.transfer.UserPlantDataTransfer;
import plant.Plant;
import ui.component.*;

import javax.swing.*;
import java.awt.*;

public class OwnedPlantListModalScreen {
    public static void show(UIManager uiManager, JFrame owner) {
        UserPlantDatacontroller controller = uiManager.getUserPlantDataController();
        UserPlantDataTransfer dto = controller.getCurrentPlantInfo();
        System.out.println("[DEBUG] 보유 식물 수: " + dto.getOwnedPlants().size());
        if (dto.getOwnedPlants().isEmpty()) {
            uiManager.showScreen(new PlantSelectionScreen(uiManager));
            return;
        }

        JPanel listPanel = PanelBuilder.create()
                .boxLayout(BoxLayout.Y_AXIS)  // 박스 레이아웃
                .padding(10)
                .background(Color.WHITE)
                .build();

        for (Plant plant : dto.getOwnedPlants()) {
            PlantDataTransfer plantDTO = new PlantDataTransfer(plant.getName(), plant.getGrowth());
            JButton plantButton = ButtonBuilder.create(plant.getName() + " (" + plant.getGrowth() + "%)")
                .fontSize(20f)
                .background(plant.getName().equals("장미") ? new Color(233, 60, 60) :
                    		plant.getName().equals("튤립") ? new Color(233, 161, 136) :
                    		plant.getName().equals("해바라기") ? new Color(255, 255, 153):
                            new Color(207, 233, 99))
                //.bold(true)
                .size(250, 50)
                .onClick(e -> {
                    owner.getGlassPane().setVisible(false);
                    uiManager.showScreen(new PlantManagementScreen(uiManager, plantDTO));
                })
                .build();
            plantButton.setAlignmentX(Component.CENTER_ALIGNMENT); // 중앙 정렬
            plantButton.setMaximumSize(new Dimension(250, 50));
            listPanel.add(plantButton);
            listPanel.add(Box.createVerticalStrut(5)); // 간격 추가
        }

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setBackground(Color.white);

        JButton plusButton = ButtonBuilder.create("+")
        	.fontSize(14f)
        	.background(Color.WHITE)
            .bold(true)
            .size(50, 50)
            .onClick(e -> {
                owner.getGlassPane().setVisible(false);
                uiManager.showScreen(new PlantSelectionScreen(uiManager));
            })
            .build();

        JPanel bottomPanel = PanelBuilder.create()
            .flowLayout(FlowLayout.CENTER)
            .padding(10)
            .add(plusButton)
            .build();

        JPanel content = PanelBuilder.create()
            .borderLayout()
            .padding(10)
            .add(LabelBuilder.create("보유한 식물 목록").fontSize(14f).bold(true).alignCenter().build(), BorderLayout.NORTH)
            .add(scroll, BorderLayout.CENTER)
            .add(bottomPanel, BorderLayout.SOUTH)
            .build();

        ModalBuilder.create(owner)
            .size(320, 400)
            .content(content)
            .buildAndShow();
    }
}
