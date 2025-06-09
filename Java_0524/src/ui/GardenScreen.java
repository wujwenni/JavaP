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

	    idLabel = LabelBuilder.create(dto.getUserId())
	        .fontSize(14f)
	        .opaque(false)
	        .alignLeft()
	        .build();
	    add(idLabel, BorderLayout.NORTH);

	    gardenPanel = PanelBuilder.create()
	        .gridLayout(5, 9, 5, 5)
	        .opaque(false)
	        .build();

	    
	    int totalCells = 5 * 9;
	    
	    List<Plant> grownPlants = dto.getOwnedPlants().stream()
	        .filter(p -> p.getGrowth() == 100)
	        .toList();

	    for (int i = 0; i < totalCells; i++) {
	        JPanel cell = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
	        cell.setOpaque(false);

	        if (i < grownPlants.size()) {
	            Plant plant = grownPlants.get(i);
	            JLabel plantLabel = LabelBuilder.create("")
	                .icon("plants/" + plant.getName() + "_stage1.png", 100, 100)
	                .alignLeft()
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

	    add(scrollPane, BorderLayout.CENTER); // ✅ gardenPanel은 add하지 않음
	}

}
