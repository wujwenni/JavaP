package ui;

import controller.UserPlantDatacontroller;
import data.transfer.PlantDataTransfer;
import data.transfer.UserPlantDataTransfer;
import plant.Plant;
import ui.component.*;
import util.FontUtil;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.stream.Stream;

public class OwnedPlantListModalScreen {
    public static void show(UIManager uiManager, JFrame owner) {
        UserPlantDatacontroller controller = uiManager.getUserPlantDataController();
        UserPlantDataTransfer dto = controller.getCurrentPlantInfo();

        if (dto.getOwnedPlants().isEmpty()) {
        	// 보유식물 없을 때 예외처리
            uiManager.showScreen(new PlantSelectionScreen(uiManager));
            return;
        }

        JPanel listPanel = PanelBuilder.create()
                .boxLayout(BoxLayout.Y_AXIS) // 세로로 쌓는 박스레이아웃
                .padding(10) // 컴포넌트 간 간격 설정
                .background(Color.WHITE)
                .build();

        // 정렬을 위한 라디오 버튼 구성
        JRadioButton ascRadio = new JRadioButton("성장도 낮은 순", true);
        JRadioButton descRadio = new JRadioButton("성장도 높은 순");
        ButtonGroup group = new ButtonGroup();
        ascRadio.setFont(FontUtil.getDefaultFont());
        descRadio.setFont(FontUtil.getDefaultFont());
        group.add(ascRadio);
        group.add(descRadio);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        radioPanel.setOpaque(false);
        radioPanel.add(ascRadio);
        radioPanel.add(descRadio);

        // 식물 버튼 구성 함수
        Runnable renderButtons = () -> {
            listPanel.removeAll();

            Stream<Plant> sortedStream = ascRadio.isSelected()
                    ? dto.getOwnedPlants().stream().sorted(Comparator.comparingInt(Plant::getGrowth))
                    : dto.getOwnedPlants().stream().sorted(Comparator.comparingInt(Plant::getGrowth).reversed());

            sortedStream.forEach(plant -> {
                PlantDataTransfer plantDTO = new PlantDataTransfer(plant.getName(), plant.getGrowth());
                JButton plantButton = ButtonBuilder.create(plant.getName() + " (" + plant.getGrowth() + "%)")
                        .fontSize(20f)
                        .background(plant.getName().equals("장미") ? new Color(233, 60, 60) :
                                plant.getName().equals("튤립") ? new Color(233, 161, 136) :
                                plant.getName().equals("해바라기") ? new Color(255, 255, 153) :
                                new Color(207, 233, 99)) // 이퀄스 사용해서 객체의 스트링 멤버와 버튼 색을 매칭.
                        .size(250, 50) // 모달의 리스트에서 식물 선택 버튼 크기 고정시킴
                        .onClick(e -> {
                            owner.getGlassPane().setVisible(false);
                            uiManager.showScreen(new PlantManagementScreen(uiManager, plantDTO));
                        })
                        .build();
                plantButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                plantButton.setMaximumSize(new Dimension(250, 50));
                listPanel.add(plantButton);
                listPanel.add(Box.createVerticalStrut(5));
            });

            listPanel.revalidate();
            listPanel.repaint();
        };

        // 정렬 버튼 클릭 시 동작
        ascRadio.addActionListener(e -> renderButtons.run());
        descRadio.addActionListener(e -> renderButtons.run());

        renderButtons.run(); // 초기 정렬 수행

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setBackground(Color.white);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

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

        JButton gardenButton = ButtonBuilder.create("화단")
                .background(Color.WHITE)
                .fontSize(20f)
                .size(100, 50)
                .onClick(e -> Launcher.launchGardenScreen(uiManager))
                .build();

        JPanel bottomPanel = PanelBuilder.create()
                .flowLayout(FlowLayout.CENTER, 10, 10)
                .padding(10)
                .add(plusButton)
                .add(gardenButton)
                .build();

        JPanel content = PanelBuilder.create()
                .borderLayout()
                .padding(10)
                .add(radioPanel, BorderLayout.NORTH)    
                .add(scroll, BorderLayout.CENTER)       
                .add(bottomPanel, BorderLayout.SOUTH)
                .build();

        ModalBuilder.create(owner)
                .size(400, 600)
                .content(content)
                .buildAndShow();
    }
}
