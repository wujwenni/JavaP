package controller;

import service.RewardService;
import manager.UserPlantDataManager;

public class RewardController {
    private final RewardService rewardService;
    private final UserPlantDataManager plantDataManager;

    public RewardController(RewardService rewardService, UserPlantDataManager plantDataManager) {
        this.rewardService = rewardService;
        this.plantDataManager = plantDataManager;
    }

    /** 물 티켓 보상 → 저장 */
    public void giveWaterTickets(int amount) {
        rewardService.rewardWaterTickets(amount);
        plantDataManager.saveAll();
    }

    /** 비료 티켓 보상 → 저장 */
    public void giveFertilizerTickets(int amount) {
        rewardService.rewardFertilizerTickets(amount);
        plantDataManager.saveAll();
    }
}
