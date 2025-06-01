package ui;

import controller.*;
import data.transfer.PlantDataTransfer;
import plant.Plant;
import ui.component.*;

import javax.swing.*;
import java.awt.*;

public class PlantManagementScreen extends BaseScreen {

    private PlantDataTransfer selectedPlant;

    public PlantManagementScreen(UIManager uiManager, PlantDataTransfer plant) {
        super(uiManager);
        this.selectedPlant = plant;
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        PlantManagementController controller = uiManager.getplantManagementController();
        controller.setSelectedPlant(selectedPlant);
        // 상단: 저장 버튼
        JButton saveButton = ButtonBuilder.create("저장 후 종료")
                .fontSize(14)
                .bold(true)
                .size(150, 40)
                .onClick(e -> controller.handleSaveAndExit())
                .build();
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);
        topPanel.add(saveButton);
        add(topPanel, BorderLayout.NORTH);
        
        
        
        String plantImageFile = selectedPlant.getName() + "_stage1.png"; 
        // 중앙: 식물 이미지 자리 표시자
        JPanel plantDisplayPanel = PanelBuilder.create()
        	    .size(300, 600)
        	    .opaque(false)
        	    .nullLayout()
        	    .add(LabelBuilder.create("")
        	            .icon("plants/장미_stage1.png", 300, 260)
        	            .bounds(0, 270, 300, 260)  // 식물 약간 위에
        	            .build())
        	    .add(LabelBuilder.create("")
        	            .icon("pot.png", 300, 180)
        	            .bounds(0, 450, 300, 150)  // 거의 바닥까지
        	            .build())
        	    .build();

        	JPanel centerWrapper = new JPanel(new BorderLayout());
        	centerWrapper.setOpaque(false);
        	centerWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        	centerWrapper.add(plantDisplayPanel, BorderLayout.SOUTH);
        	add(centerWrapper, BorderLayout.CENTER);

        

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(plantDisplayPanel);

        add(centerPanel, BorderLayout.CENTER);

        // 하단: 성장 액션 버튼 + 미니게임 버튼
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        buttonPanel.setOpaque(false);

        buttonPanel.add(ButtonBuilder.create("물 주기")
                .fontSize(14)
                .bold(true)
                .background(new Color(173, 216, 230))
                .onClick(e -> controller.handleWater())
                .build());

        buttonPanel.add(ButtonBuilder.create("대화하기")
                .fontSize(14)
                .bold(true)
                .background(new Color(255, 250, 205))
                .onClick(e -> controller.handleTalk())
                .build());

        buttonPanel.add(ButtonBuilder.create("비료 주기")
                .fontSize(14)
                .bold(true)
                .background(new Color(144, 238, 144))
                .onClick(e -> controller.handleFertilize())
                .build());

        buttonPanel.add(ButtonBuilder.create("미니게임")
                .fontSize(14)
                .bold(true)
                .background(new Color(221, 160, 221))
                .onClick(e -> Launcher.launchMiniGameSelection(uiManager))
                .build());

        add(buttonPanel, BorderLayout.SOUTH);
    }

}
