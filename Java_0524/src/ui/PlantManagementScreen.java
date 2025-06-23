package ui;

import controller.PlantManagementController;
import data.transfer.PlantDataTransfer;
import ui.component.*;
import ui.effect.WateringEffect;
import ui.effect.FertilizeEffect;
import ui.effect.GrowEffect;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class PlantManagementScreen extends BaseScreen {

    private PlantDataTransfer selectedPlant;
    private JLabel ticketLabel;
    private JLabel plantLabel;
    private JProgressBar progressBar;
    private JPanel plantDisplayPanel;
    private PlantManagementController controller;
    
    // 1:1 비율 이미지를 사용했고, 아래 상수 설정으로 최대한 이미지가 찌그러지지 않게 함.
    // 또한 해당 상수 설정은 식물 성장 애니메이션 구현을 위해 설정함.
    private static final int PANEL_WIDTH  = 450;   // 식물 디스플레이 영역의 전체 너비
    private static final int POT_TOP_Y    = 450;   // 화분 이미지 맨 위 좌표
    private static final int POT_W        = 300;   // 화분 이미지 표시 너비
    private static final int POT_H        = 270;   // 화분 이미지 표시 높이
    private static final int FULL_W      = 450;    
    private static final int FULL_H      = 450;
    
    
    public PlantManagementScreen(UIManager uiManager, PlantDataTransfer plant) {
        super(uiManager);
        this.selectedPlant = plant;
        this.controller = uiManager.getplantManagementController();
        setBackgroundImage("backgrounds/managementscreen.png");
        initialize();
    }
    
    
    
    

    @Override
    protected void initialize() {
    	setLayout(new BorderLayout());
    	
        controller.setSelectedPlant(selectedPlant);
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
        	    .add(createMenuButton(), BorderLayout.EAST)
        	    .build();

        	add(topPanel, BorderLayout.NORTH);


        	plantDisplayPanel = PanelBuilder.create()
                    .size(PANEL_WIDTH, 800)    // 높이는 임의로 800 지정 (프로그램 창 기본 크기: 800 by 900)
                    .opaque(false)
                    .nullLayout()
                    .build();
        	
        	
        	int displayH = FULL_H;          
        	int displayW = FULL_W ;    // 크기 및 비율 동일 유지

        	// 위치 계산도 동일
        	int x = (PANEL_WIDTH - displayW) / 2;
        	int y = POT_TOP_Y - displayH + 150;

        	plantLabel = LabelBuilder.create("")
        	    .verticalAlignment(SwingConstants.BOTTOM)
        	    .icon(
        	        "plants/" +
        	        ( selectedPlant.getGrowth() < 25  ? "stage0"
        	        : selectedPlant.getGrowth() < 50    ? "stage1"
        	        : selectedPlant.getGrowth() < 75    ? "stage2"
        	        : selectedPlant.getGrowth() < 100    ? "stage3"
        	        : selectedPlant.getName() + "_stage4"
        	        ) + ".png",
        	        displayW, displayH
        	    )
        	    .bounds(x, y, displayW, displayH)
        	    .build();

            plantDisplayPanel.add(plantLabel);
           

        		// pot 이미지 : 150 by 80, y=450
        		JLabel potLabel = LabelBuilder.create("")  // 텍스트 없는 라벨으로 이미지만 띄우기
                        .icon("pot.png", POT_W, POT_H)
                        // x: (전체 너비 − 아이콘 너비)/2 , 중앙 정렬
                        // y: POT_TOP_Y 에 아이콘 맨 위를 맞춤
                        .bounds((PANEL_WIDTH - POT_W) / 2, POT_TOP_Y, POT_W, POT_H)
                        .build();
                plantDisplayPanel.add(potLabel);

        		// 성장도 프로그레스바 : 400 by 20, y=700
        		plantDisplayPanel.add(progressBar);
        		progressBar.setBounds(20, 100 + 400 + 20 + 150 + 20, 400, 20);

        		// 컴포넌트 부착
        		JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        		centerWrapper.setOpaque(false);
        		centerWrapper.add(plantDisplayPanel);
        		add(centerWrapper, BorderLayout.CENTER);


        // 하단 버튼 부착할 패널
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        buttonPanel.setOpaque(false);

        // 물 주기 버튼
        buttonPanel.add(ButtonBuilder.create("물 주기")
            .fontSize(14f).bold(true).cooldown(3000)
            .background(new Color(173, 216, 230))
            .onClick(e -> {
            	int oldGrowth = selectedPlant.getGrowth();
                if (controller.handleWater()) {
                    refreshTickets();
                    updateGrowthBar(selectedPlant.getGrowth());
                    updatePlantImageWithAnimation(oldGrowth, selectedPlant.getGrowth(), selectedPlant.getName(), true);
                    WateringEffect.play(plantDisplayPanel);
                } else {
                    OptionPaneBuilder.create()
                        .title("NOTIFICATION")
                        .message("물 티켓의 개수 또는 식물의 성장도를 확인해주세요")
                        .show();
                }
            }).build());

        // 대화 창 버튼
        buttonPanel.add(ButtonBuilder.create("대화하기")
            .fontSize(14f).bold(true)
            .background(new Color(255, 250, 205))
            .onClick(e -> Launcher.launchChatScreen(uiManager, () -> {
                int old = selectedPlant.getGrowth();
                if (controller.handleTalk()) {
                    OptionPaneBuilder.create()
                        .title("NOTIFICATION")
                        .message("성장도 " + (selectedPlant.getGrowth() - old) + "% 획득했습니다 !!")
                        .show();
                    updateGrowthBar(selectedPlant.getGrowth());
                    updatePlantImageWithAnimation(old, selectedPlant.getGrowth(), selectedPlant.getName(), true);
                } else {
                    OptionPaneBuilder.create()
                        .title("NOTIFICATION")
                        .message("진지한 대화가 하고 싶대요")
                        .show();
                }
            })).build());

        // 비료 주기 버튼
        buttonPanel.add(ButtonBuilder.create("비료 주기")
            .fontSize(14f).bold(true).cooldown(3000)
            .background(new Color(168, 119, 71))
            .onClick(e -> {
                int oldGrowth = selectedPlant.getGrowth();
            	if (controller.handleFertilize()) {
                    refreshTickets();
                    updateGrowthBar(selectedPlant.getGrowth());
                    FertilizeEffect.play(plantDisplayPanel);
                    updatePlantImageWithAnimation(oldGrowth, selectedPlant.getGrowth(), selectedPlant.getName(), true);
                } else {
                    OptionPaneBuilder.create()
                        .title("NOTIFICATION")
                        .message("비료 티켓의 개수 또는 식물의 성장도를 확인해주세요")
                        .show();
                }
            }).build());

        // 미니게임 버튼
        buttonPanel.add(ButtonBuilder.create("미니게임")
            .fontSize(14f).bold(true)
            .background(new Color(221, 160, 221))
            .onClick(e -> Launcher.launchMiniGameSelection(uiManager))
            .build());
        add(buttonPanel, BorderLayout.SOUTH);

        // 포커스 얻을 때 티켓 갱신
        // 메인 프로그램 창에 focus가 갈 때, 티켓의 수를 나타내는 라벨을 계속 갱신함
        SwingUtilities.invokeLater(() -> {
            Window w = SwingUtilities.getWindowAncestor(this);
            if (w != null) {
                w.addWindowFocusListener(new WindowAdapter() {
                    @Override
                    public void windowGainedFocus(WindowEvent e) {
                        refreshTickets();
                    }
                });
            }
        });
    }

    private void refreshTickets() {
        int water = uiManager.getUserPlantDataController()
            .getCurrentPlantInfo().getWaterTickets();
        int fert  = uiManager.getUserPlantDataController()
            .getCurrentPlantInfo().getFertilizerTickets();
        ticketLabel.setText(
            "Water Tickets: " + water + " / Fertilizer Tickets: " + fert
        ); // setText로 라벨의 텍스트 수정.
    }

    private void updateGrowthBar(int newGrowth) {
        selectedPlant.setGrowth(newGrowth);
        progressBar.setValue(newGrowth);   
    }
    
    private void updatePlantImageWithAnimation(int oldGrowth, int newGrowth, String plantName, boolean a) {
        int oldStage, newStage;
        // 성장도 값의 증가로 newStage의 값이 증가하면 애니메이션을 재생하기 위한 메서드
        // 아래 식은 성장도를 식물의 단계로 바꾸어 주는 것
        if 		(newGrowth < 25)   newStage = 0;
        else if (newGrowth < 50)  newStage = 1;
        else if (newGrowth < 75)  newStage = 2;
        else if (newGrowth < 100) newStage = 3;
        else                   	  newStage = 4;
        
        if 		(oldGrowth < 25)  oldStage = 0;
        else if (oldGrowth < 50)  oldStage = 1;
        else if (oldGrowth < 75)  oldStage = 2;
        else if (oldGrowth < 100) oldStage = 3;
        else                   	  oldStage = 4;

        if (a && newStage > oldStage) {
        	GrowEffect.updateAndPlay(
                    plantLabel,        // 애니메이션 대상 라벨
                    PANEL_WIDTH,       // 패널 전체 너비
                    POT_TOP_Y + 150,   // 화분 꼭대기 Y 좌표
                    FULL_W,            // 원본 너비
                    FULL_H,            // 원본 높이
                    newStage,          // 계산된 스테이지
                    plantName,         // 장미, 튤립, 해바라기
                    1000,              // 애니메이션 재생 시간 (밀리세컨드)
                    45                 // 프레임 수
                );
        }
        
    }

    private JButton createMenuButton() {
        JButton menuButton = ButtonBuilder.create("Menu")
            .fontSize(10f).background(Color.WHITE).bold(true)
            .size(100, 30).build();
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem saveExit = new JMenuItem("저장 후 종료");
        saveExit.addActionListener(e -> { controller.handleSave(); System.exit(0); });
        JMenuItem garden = new JMenuItem("화단으로 이동");
        garden.addActionListener(e -> Launcher.launchGardenScreen(uiManager));
        JMenuItem saveMain = new JMenuItem("저장 후 메인화면");
        saveMain.addActionListener(e -> { controller.handleSave(); uiManager.showScreen(new EntryScreen(uiManager)); });
        popupMenu.add(saveExit); popupMenu.add(garden); popupMenu.add(saveMain);
        menuButton.addActionListener(e -> popupMenu.show(menuButton,
            menuButton.getWidth() - popupMenu.getPreferredSize().width,
            menuButton.getHeight()));
        return menuButton;
    }
}
