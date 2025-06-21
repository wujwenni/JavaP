package controller;

import service.RewardService;
import manager.UserPlantDataManager;

public class RewardController {
    private final RewardService rewardService;
    private final UserPlantDataManager plantDataManager;
    
    // 컨트롤러 생성자, 리워드 서비스, 데이터 매니저 멤버 초기화.
    public RewardController(RewardService rewardService, UserPlantDataManager plantDataManager) {
        this.rewardService = rewardService;
        this.plantDataManager = plantDataManager;
    }
    
    // amount 만큼 물 티켓 부여, amount를 프로그램 로직에 넘김
    public void giveWaterTickets(int amount) {
        rewardService.rewardWaterTickets(amount);
        plantDataManager.saveAll();
    }
    
    // giveWaterTickets와 동일
    public void giveFertilizerTickets(int amount) {
        rewardService.rewardFertilizerTickets(amount);
        plantDataManager.saveAll();
    }
}
