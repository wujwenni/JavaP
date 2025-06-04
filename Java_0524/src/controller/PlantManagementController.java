package controller;

import data.transfer.PlantDataTransfer;
import manager.*;
import service.PlantCareService;
import plant.Plant;

public class PlantManagementController {
    private final PlantCareService careService;
    private final UserPlantDataManager plantDataManager;
    private PlantDataTransfer selectedPlant;

    public PlantManagementController(UserManager userManager, UserPlantDataManager plantDataManager) {
        this.plantDataManager = plantDataManager;
        this.careService = new PlantCareService(userManager, plantDataManager);
    }

    public void setSelectedPlant(PlantDataTransfer plant) {
        this.selectedPlant = plant;
    }

    public boolean handleWater() {
        if (selectedPlant != null) {
        	System.out.println("water");
            return careService.waterPlant(selectedPlant);
        }
        else 
        {
        	System.out.println("no plant");
        	return false;
        }
    }

    public void handleTalk() {
        if (selectedPlant != null) {
            System.out.println("선택된 식물과 대화: " + selectedPlant.getName());
        }
        else System.out.println("no plant");
    }

    public boolean handleFertilize() {
        if (selectedPlant != null) {
            System.out.println("fertilize");
            return careService.fertilizePlant(selectedPlant);
            
        }
        else 
        {
        	System.out.println("no plant");
        	return false;
        }
    }


    public void handleSaveAndExit() {
        plantDataManager.saveAll();
        System.exit(0);
    }
}

