package ui;

import controller.*;
import data.transfer.PlantDataTransfer;
import ui.component.*;
import ui.effect.FertilizeEffect;
import ui.effect.WateringEffect;

import javax.swing.*;
import java.awt.*;

public class PlantManagementScreen extends BaseScreen {

    private PlantDataTransfer selectedPlant;
    private JLabel ticketLabel;
    private JProgressBar progressBar;
    private JPanel plantDisplayPanel;

    public PlantManagementScreen(UIManager uiManager, PlantDataTransfer plant) {
        super(uiManager);
        setBackgroundImage("backgrounds/managementscreen.png");
        this.selectedPlant = plant;
        initialize();
    }

    @Override
    protected void initialize() {
        setLayout(new BorderLayout());
        PlantManagementController controller = uiManager.getplantManagementController();
        controller.setSelectedPlant(selectedPlant);

        // 티켓 정보 라벨 생성
        int water = uiManager.getUserPlantDataController().getCurrentPlantInfo().getWaterTickets();
        int fert = uiManager.getUserPlantDataController().getCurrentPlantInfo().getFertilizerTickets();
        ticketLabel = LabelBuilder.create("Water Tickets: " + water + " / Fertilizer Tickets: " + fert)
                .fontSize(16f)
                .bold(true)
                .background(new Color(207, 233, 99))
                .size(380, 30)
                .alignCenter()
                .setBorder(Color.darkGray, 1)
                .opaque(true)
                .build();
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(selectedPlant.getGrowth());
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(29, 71, 8));
        progressBar.setPreferredSize(new Dimension(400, 20));
        

        // 상단
        JPanel topPanel = PanelBuilder.create()
                .borderLayout()
                .opaque(false)
                .padding(10)
                .add(ticketLabel, BorderLayout.WEST)
                .add(ButtonBuilder.create("저장 후 종료")
                        .fontSize(14f)
                        .bold(true)
                        .background(Color.white)
                        .size(150, 40)
                        .onClick(e -> controller.handleSaveAndExit())
                        .build(), BorderLayout.EAST)
                .build();

        add(topPanel, BorderLayout.NORTH);

        // 식물 이미지
        plantDisplayPanel = PanelBuilder.create()
                .size(450, 800)
                .opaque(false)
                .nullLayout()
                .add(LabelBuilder.create("")
                        .icon("plants/" + selectedPlant.getName() + "_stage1.png", 300, 260)
                        .bounds(75, 270, 300, 260)
                        .build())
                .add(LabelBuilder.create("")
                        .icon("pot.png", 300, 180)
                        .bounds(75, 450, 300, 150)
                        .build())
                .build();

        plantDisplayPanel.add(progressBar);
        progressBar.setBounds(25, 700, 400, 20); // (450 - 400) / 2 = 25
        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.setBorder(BorderFactory.createEmptyBorder());
        centerWrapper.add(plantDisplayPanel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerPanel.setOpaque(false);
        centerPanel.add(plantDisplayPanel);

        add(centerPanel, BorderLayout.CENTER);

        // 하단 버튼
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        buttonPanel.setOpaque(false);

        // 물 주기
        buttonPanel.add(ButtonBuilder.create("물 주기")
                .fontSize(14f)
                .bold(true)
                .cooldown(3000)
                .background(new Color(173, 216, 230))
                .onClick(e -> {
                    if (controller.handleWater()) {
                        updateTicketLabel(-1, 0);
                        updateGrowthBar(selectedPlant.getGrowth());
                        WateringEffect.play(plantDisplayPanel);
                    } else {
                        JOptionPane.showMessageDialog(this, "물 티켓이 부족합니다!");
                    }
                })
                .build());

        // 대화하기 (티켓 X)
        buttonPanel.add(ButtonBuilder.create("대화하기")
                .fontSize(14f)
                .cooldown(3000)
                .bold(true)
                .background(new Color(255, 250, 205))
                .onClick(e -> controller.handleTalk())
                .build());

        // 비료 주기
        buttonPanel.add(ButtonBuilder.create("비료 주기")
                .fontSize(14f)
                .cooldown(3000)
                .bold(true)
                .background(new Color(168, 119, 71))
                .onClick(e -> {
                    if (controller.handleFertilize()) {
                        updateTicketLabel(0, -1);
                        updateGrowthBar(selectedPlant.getGrowth());
                        FertilizeEffect.play(plantDisplayPanel);
                    } else {
                        JOptionPane.showMessageDialog(this, "비료 티켓이 부족합니다!");
                    }
                })
                .build());

        // 미니게임
        buttonPanel.add(ButtonBuilder.create("미니게임")
                .fontSize(14f)
                .bold(true)
                .background(new Color(221, 160, 221))
                .onClick(e -> Launcher.launchMiniGameSelection(uiManager))
                .build());

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void updateTicketLabel(int waterDelta, int fertDelta) {
        String[] parts = ticketLabel.getText().split(" ");
        int water = Integer.parseInt(parts[2]);
        int fert = Integer.parseInt(parts[6]);

        water += waterDelta;
        fert += fertDelta;

        ticketLabel.setText("Water Tickets: " + water + " / Fertilizer Tickets: " + fert);
    }
    
    private void updateGrowthBar(int newGrowth) {
        selectedPlant.setGrowth(newGrowth);
        progressBar.setValue(newGrowth);
    }
    
}
