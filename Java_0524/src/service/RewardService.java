package service;

import data.User;
import data.UserPlantData;
import manager.UserManager;
import manager.UserPlantDataManager;

// 티켓 보상 처리를 담당하는 클래스
// 컨트롤러와 연결되어 있음. 컨트롤러에서 amount 를 주면서 호출함.
// 그러면 이 클래스에서 amount 만큼 티켓 수를 증가 시킴.
// PlantCareService와 비슷한 원리로 동작함.
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

    
    public void rewardFertilizerTickets(int amount) {
        User current = userManager.getCurrentUser();
        if (current == null || amount <= 0) return;

        UserPlantData data = plantDataManager.getCurrentUserData(current);
        if (data == null) return;
        
        data.addFertilizerTickets(amount);
    }
}
