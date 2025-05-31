package service;

import data.User;
import data.UserPlantData;
import data.transfer.UserPlantDataTransfer;
import manager.UserManager;
import manager.UserPlantDataManager;

public class PlantQueryService {

    private final UserManager userManager;
    private final UserPlantDataManager plantDataManager;

    public PlantQueryService(UserManager userManager, UserPlantDataManager plantDataManager) {
        this.userManager = userManager;
        this.plantDataManager = plantDataManager;
    }

    //현재 사용자 식물 정보를 DTO로 반환
    public UserPlantDataTransfer getCurrentUserPlantDTO() {
        User user = userManager.getCurrentUser();
        UserPlantData data = plantDataManager.getCurrentUserData(user);
        return UserPlantDataTransfer.fromVO(data);
    }
}
