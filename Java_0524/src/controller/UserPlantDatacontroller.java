package controller;

import data.transfer.*;
import manager.*;
import plant.*;
import service.*;
import java.util.*;

public class UserPlantDatacontroller {

    private final PlantCareService plantCareService;
    private final PlantQueryService plantQueryService;
    private final UserPlantDataManager plantDataManager;

    public UserPlantDatacontroller(
        PlantCareService plantCareService,
        PlantQueryService plantQueryService,
        UserPlantDataManager plantDataManager,
        UserManager userManager
    ) {
        this.plantCareService = plantCareService;
        this.plantQueryService = plantQueryService;
        this.plantDataManager = plantDataManager;
    }

    public PlantDataTransfer selectInitialPlant(String plantName) {
        Plant plant = plantCareService.registerInitialPlantForCurrentUser(plantName);
        if (plant == null) return null;

        plantDataManager.saveAll();

        return new PlantDataTransfer(plant.getName(), plant.getGrowth());
    } 


    
    public List<Plant> getOwnedPlantsForCurrentUser() {
        UserPlantDataTransfer dto = getCurrentPlantInfo();
        return (dto != null) ? new ArrayList<>(dto.getOwnedPlants()) : Collections.emptyList();
    }

    public void save() {
        plantDataManager.saveAll();
    }

    public UserPlantDataTransfer getCurrentPlantInfo() {
        return plantQueryService.getCurrentUserPlantDTO();
    }
}
