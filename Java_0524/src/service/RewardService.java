package service;

import data.User;
import data.UserPlantData;
import manager.UserManager;
import manager.UserPlantDataManager;


public class RewardService {
	private final UserManager userManager;
    private final UserPlantDataManager plantDataManager;
    
    public RewardService(UserManager userManager, UserPlantDataManager plantDataManager) {
    	this.userManager = userManager;
    	this.plantDataManager = plantDataManager;
    }
    
    public void rewardWaterTickets(int amount) {
        User current = userManager.getCurrentUser();
        if (current == null || amount <= 0) return;

        UserPlantData data = plantDataManager.getCurrentUserData(current);
        if (data == null) return;
        data.addWaterTickets(amount);
    }

    /** 비료 티켓 보상 처리 */
    public void rewardFertilizerTickets(int amount) {
        User current = userManager.getCurrentUser();
        if (current == null || amount <= 0) return;

        UserPlantData data = plantDataManager.getCurrentUserData(current);
        if (data == null) return;
        data.addFertilizerTickets(amount);
    }
}
