package controller;

import data.*;
import data.transfer.*;
import manager.UserManager;
import manager.UserPlantDataManager;


public class UserController {
	private final UserManager userManager;
    private final UserPlantDataManager plantDataManager;

    public UserController(UserManager userManager, UserPlantDataManager plantDataManager) {
        this.userManager = userManager;
        this.plantDataManager = plantDataManager;
    }

    public boolean login(UserDataTransfer dto) {
        User user = new User(dto.getId(), dto.getPassword(), 
        		dto.getWaterTickets(), dto.getFertilizerTickets());
        boolean result = userManager.login(user);
        if (result) {
            plantDataManager.registerIfAbsent(user);  // ✅ 컨트롤러 내부에서 처리
        }
        return result;
    }

    // 회원가입 처리
    public boolean register(UserDataTransfer dto) {
    	User user = new User(dto.getId(), dto.getPassword(), 
        		dto.getWaterTickets(), dto.getFertilizerTickets());
        return userManager.register(user);
    }

    // 현재 로그인된 사용자 조회
    public User getCurrentUser() {
        return userManager.getCurrentUser();
    }
}
