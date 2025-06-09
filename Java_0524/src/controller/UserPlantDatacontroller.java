package controller;

import data.*;
import data.transfer.*;
import manager.*;
import plant.*;
import service.*;
import java.util.*;

public class UserPlantDatacontroller {

    private final PlantCareService plantCareService;
    private final PlantQueryService plantQueryService;
    private final UserPlantDataManager plantDataManager;
    private final UserManager userManager;

    public UserPlantDatacontroller(
        PlantCareService plantCareService,
        PlantQueryService plantQueryService,
        UserPlantDataManager plantDataManager,
        UserManager userManager
    ) {
        this.plantCareService = plantCareService;
        this.plantQueryService = plantQueryService;
        this.plantDataManager = plantDataManager;
        this.userManager = userManager;
    }

    // 🌱 초기 식물 선택
    public PlantDataTransfer selectInitialPlant(String plantName) {
        Plant plant = plantCareService.registerInitialPlantForCurrentUser(plantName);
        if (plant == null) return null;

        UserPlantData data = plantDataManager.getCurrentUserData(userManager.getCurrentUser());
        plantDataManager.saveAll();

        return new PlantDataTransfer(plant.getName(), plant.getGrowth());
    } 


    
    public List<Plant> getOwnedPlantsForCurrentUser() {
        UserPlantDataTransfer dto = getCurrentPlantInfo();
        return (dto != null) ? new ArrayList<>(dto.getOwnedPlants()) : Collections.emptyList();
    }
    // ✅ 수동 저장 (저장 후 종료 등)
    public void save() {
        plantDataManager.saveAll();
    }

    // 🔍 현재 상태 조회 (DTO 반환)
    public UserPlantDataTransfer getCurrentPlantInfo() {
        return plantQueryService.getCurrentUserPlantDTO();
    }
}
