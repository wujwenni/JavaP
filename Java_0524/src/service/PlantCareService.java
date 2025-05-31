package service;

import data.*;
import data.transfer.PlantDataTransfer;
import manager.*;
import plant.Plant;
import plant.PlantFactory;


public class PlantCareService {

    private final UserManager userManager;
    private final UserPlantDataManager plantDataManager;

    public PlantCareService(UserManager userManager, UserPlantDataManager plantDataManager) {
        this.userManager = userManager;
        this.plantDataManager = plantDataManager;
    }

    public Plant registerInitialPlantForCurrentUser(String plantName) {
        User user = userManager.getCurrentUser();
        if (user == null) return null;

        UserPlantData data = plantDataManager.getCurrentUserData(user);
        if (data == null) {
            data = new UserPlantData(user);
            plantDataManager.update(data);
        }

        Plant newPlant = PlantFactory.create(plantName, 0);
        data.addOwnedPlant(newPlant);
        plantDataManager.update(data);
        return newPlant;
    }


    public boolean completeGrowthIfNeeded(Plant targetPlant) {
        User user = userManager.getCurrentUser();
        UserPlantData data = plantDataManager.getCurrentUserData(user);

        if (targetPlant != null && targetPlant.getGrowth() >= 100) {
            data.addOwnedPlant(targetPlant);
            plantDataManager.update(data);
            return true;
        }
        return false;
    }

    // PlantDataTransfer를 받아서 처리 (Plant 객체와 동기화)
    public boolean waterPlant(PlantDataTransfer plantDTO) {
        User user = userManager.getCurrentUser();
        if (user == null || plantDTO == null || !user.useWaterTicket()) return false;

        UserPlantData data = plantDataManager.getCurrentUserData(user);
        Plant targetPlant = findPlant(data, plantDTO);
        if (targetPlant == null) return false;

        
        targetPlant.increaseGrowth(3 + (int)(Math.random() * (5 - 3 + 1)));
        plantDTO.setGrowth(targetPlant.getGrowth());
        userManager.saveCurrentUser();
        data.useWaterTicket();
        plantDataManager.update(data);
        return true;
    }

    public boolean fertilizePlant(PlantDataTransfer plantDTO) {
        User user = userManager.getCurrentUser();
        if (user == null || plantDTO == null || !user.useFertilizerTicket()) return false;

        UserPlantData data = plantDataManager.getCurrentUserData(user);
        Plant targetPlant = findPlant(data, plantDTO);
        if (targetPlant == null) return false;

        
        targetPlant.increaseGrowth(3 + (int)(Math.random() * (5 - 3 + 1)));
        plantDTO.setGrowth(targetPlant.getGrowth());
        userManager.saveCurrentUser();
        data.useFertilizerTicket();
        plantDataManager.update(data);
        return true;
    }

    // PlantDataTransfer에 해당하는 Plant 객체 찾기
    private Plant findPlant(UserPlantData data, PlantDataTransfer plantDTO) {
        for (Plant plant : data.getOwnedPlants()) {
            if (plant.getName().equals(plantDTO.getName()) && plant.getGrowth() == plantDTO.getGrowth()) {
                // 이름과 성장도가 같으면 같은 객체로 간주 (id가 있다면 id로 비교)
                return plant;
            }
        }
        return null;
    }
}
