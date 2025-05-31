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
        	    .gridLayout(0, 1, 0, 10) // 한 열로 나열, 버튼 사이 간격 10px
        	    .padding(10)              // 패딩
        	    .background(Color.WHITE)  // 배경색
        	    .build();

        	for (Plant plant : dto.getOwnedPlants()) {
        	    PlantDataTransfer plantDTO = new PlantDataTransfer(plant.getName(), plant.getGrowth());
        	    JButton plantButton = ButtonBuilder.create(plant.getName() + " (" + plant.getGrowth() + "%)")
        	        .fontSize(14)
        	        .size(200, 30)
        	        .onClick(e -> {
        	            owner.getGlassPane().setVisible(false);
        	            uiManager.showScreen(new PlantManagementScreen(uiManager, plantDTO));
        	        })
        	        .build();
        	    listPanel.add(plantButton);
        	}


        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        JButton plusButton = ButtonBuilder.create("+")
            .fontSize(20)
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
            .add(LabelBuilder.create("보유한 식물 목록").fontSize(16).bold(true).alignCenter().build(), BorderLayout.NORTH)
            .add(scroll, BorderLayout.CENTER)
            .add(bottomPanel, BorderLayout.SOUTH)
            .build();

        ModalBuilder.create(owner)
            .size(320, 400)
            .content(content)
            .buildAndShow();
    }
}
