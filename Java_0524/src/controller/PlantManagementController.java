package controller;

import data.transfer.*;
import manager.*;
import service.ChatTracker;
import service.PlantCareService;
import service.PlantQueryService;


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
    	if (careService.waterPlant(selectedPlant)) System.out.println("true");
    	return careService.waterPlant(selectedPlant);
    }

    public boolean handleTalk() {
        return careService.chatPlant(selectedPlant, ChatTracker.getAccumulatedGrowth());
    }

    public boolean handleFertilize() {
    	return careService.fertilizePlant(selectedPlant);
    }


    public void handleSave() {
        plantDataManager.saveAll();
    }
}

