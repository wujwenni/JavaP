package ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import controller.UserPlantDatacontroller;
import data.transfer.UserPlantDataTransfer;
import plant.Plant;
import ui.component.*;

public class GardenScreen extends BaseScreen {
	
	private JPanel gardenPanel;
	private JLabel idLabel;
	UserPlantDatacontroller controller = uiManager.getUserPlantDataController();
    UserPlantDataTransfer dto = controller.getCurrentPlantInfo();
	
	public GardenScreen(UIManager uiManager) {
		super(uiManager);
		initialize();
	}

	@Override
	protected void initialize() {
	    setLayout(new BorderLayout());

	    idLabel = LabelBuilder.create(dto.getUserId() + "의 화단")
	        .fontSize(24f)
	        .opaque(false)
	        .alignCenter()
	        .build();
	    add(idLabel, BorderLayout.NORTH);

	    gardenPanel = PanelBuilder.create()
	        .gridLayout(5, 9, 5, 5)
	        .opaque(true)
	        .background(new Color(128, 72, 22))
	        .build();

	    
	    int totalCells = 5 * 9;
	    
	    List<Plant> grownPlants = dto.getOwnedPlants().stream()
	        .filter(p -> p.getGrowth() == 100)
	        .toList();
	    
	    for (int i = 0; i < totalCells; i++) {
	        // 짝수(i % 2 == 0)면 image2, 홀수면 image1
	        String bgPath = (i % 2 == 0)
	            ? "components/labels/garden2.png"
	            : "components/labels/garden1.png";

	        JPanel cell = PanelBuilder.create()
	            .flowLayout(FlowLayout.LEFT)
	            .backgroundImage(bgPath)  // 여기서 경로 지정
	            .opaque(false)
	            .build();

	        // 성장된 식물이 있으면 아이콘 추가
	        if (i < grownPlants.size()) {
	            Plant plant = grownPlants.get(i);
	            JLabel plantLabel = LabelBuilder.create("")
	                .icon("plants/" + plant.getName() + "_stage4.png", 150, 150)
	                .build();
	            cell.add(plantLabel);
	        }

	        gardenPanel.add(cell);
	    }


	    JScrollPane scrollPane = new JScrollPane(
	        gardenPanel,
	        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
	    );
	    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
	    scrollPane.setOpaque(false);
	    scrollPane.getViewport().setOpaque(false);
	    scrollPane.setBorder(null);

	    add(scrollPane, BorderLayout.CENTER);
	}

}
