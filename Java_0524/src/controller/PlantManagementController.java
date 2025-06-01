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

    public void handleWater() {
        if (selectedPlant != null) {
            careService.waterPlant(selectedPlant);
            System.out.println("water");
        }
        else System.out.println("no plant");
    }

    public void handleTalk() {
        if (selectedPlant != null) {
            System.out.println("선택된 식물과 대화: " + selectedPlant.getName());
        }
        else System.out.println("no plant");
    }

    public void handleFertilize() {
        if (selectedPlant != null) {
            careService.fertilizePlant(selectedPlant);
            System.out.println("fertilize");
        }
        else System.out.println("no plant");
    }


    public void handleSaveAndExit() {
        plantDataManager.saveAll();
        System.exit(0);
    }
}

