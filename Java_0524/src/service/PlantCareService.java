package service;

import data.*;
import data.transfer.PlantDataTransfer;
import manager.*;
import plant.Plant;
import plant.PlantFactory;


public class PlantCareService {

    private final UserManager userManager; // 현재 실행중인 프로그램의 사용자 정보를 가져오기 위한 UserManager 클래스 변수 선언
    private final UserPlantDataManager plantDataManager; // 현재 실행중인 프로그램의 사용자 식물 정보를 가져오기 위한 UserPlantDataManager 클래스 변수 선언

    public PlantCareService(UserManager userManager, UserPlantDataManager plantDataManager) {
        this.userManager = userManager;
        this.plantDataManager = plantDataManager;
    }

    public Plant registerInitialPlantForCurrentUser(String plantName) {
        // 초기 식물 생성 메서드.
    	// PlantManagementController의 selectInitialPlant 메서드와 연결되어 있음.
    	// 식물 선택 화면에서 새로운 식물을 선택할 때 실행됨.
    	User user = userManager.getCurrentUser();
        if (user == null) return null;
        UserPlantData data = plantDataManager.getCurrentUserData(user);
        if (data == null) {
            data = new UserPlantData(user.getId(), 0 , 0);
            plantDataManager.update(data);
        }

        // 현재 메서드를 실행한다는 것은 새로운 식물을 하나 추가하는 것이기 때문에 성장도가 0인 plantName을 가진 식물을 PlantFactory 클래스로 새로 만들기
        Plant newPlant = PlantFactory.create(plantName, 0); 
        data.addOwnedPlant(newPlant); // 현재 사용자 식물 정보의 ownedPlant 변수에 newPlant 추가.
        plantDataManager.update(data); // 사용자 식물 정보를 갱신하여 다음 실행 시에나 다른 클래스에서도 식물 정보의 변경되어 있는 상태를 일관적으로 읽을 수 있어야 함.
        return newPlant;
    }

    // PlantDataTransfer를 받아서 처리 (Plant 객체와 동기화)
    public boolean waterPlant(PlantDataTransfer plantDTO) {
    	// targetPlant는 DTO와 동일한 이름과 성장도를 가진 Plant 객체를 findPlant로 검색한 결과이고, 해당 객체를 직접 참조하여 상태를 변경할 수 있음.
    	User user = userManager.getCurrentUser();
    	UserPlantData data = plantDataManager.getCurrentUserData(user);
        Plant targetPlant = findPlant(data, plantDTO); 
        
        if (user == null || plantDTO == null) return false;
        if (targetPlant == null) return false;        
        if (data.getWaterTickets() <= 0) {
            //System.out.println("물티켓부족"); //for debug
            return false;
        }
        else {
        	// 물 티켓의 개수가 0 이상이고, 현재 로그인한 유저가 존재하고, 해당 유저의 식물이 존재할 때 실행문
        	if (targetPlant.getGrowth() == 100) {
        		return false;
        	}
        	targetPlant.increaseGrowth(3 + (int)(Math.random() * 3));
            plantDTO.setGrowth(targetPlant.getGrowth());
            userManager.saveCurrentUser();
            data.useWaterTicket();
            plantDataManager.update(data);
            //System.out.println("true");
            return true;
        }
    }

    public boolean fertilizePlant(PlantDataTransfer plantDTO) {
    	User user = userManager.getCurrentUser();
    	UserPlantData data = plantDataManager.getCurrentUserData(user);
        Plant targetPlant = findPlant(data, plantDTO);
        if (user == null || plantDTO == null) return false;
        if (targetPlant == null) return false;
        if (data.getFertilizerTickets() <= 0) {
           //System.out.println("in service, no ftickets");
            return false;
        }
        else {
        	if (targetPlant.getGrowth() == 100) {
        		return false;
        	}
        	targetPlant.increaseGrowth(3 + (int)(Math.random() * 3));
            plantDTO.setGrowth(targetPlant.getGrowth());
            userManager.saveCurrentUser();
            data.useFertilizerTicket();
            plantDataManager.update(data);
            //System.out.println("true");
            return true;
        }
    }
    
    public boolean chatPlant(PlantDataTransfer plantDTO, boolean b) {
    	// chatTracker의 불리언 타입 반환 값에 따라 성장도 반영 여부를 판단.
    	User user = userManager.getCurrentUser();
    	UserPlantData data = plantDataManager.getCurrentUserData(user);
        Plant targetPlant = findPlant(data, plantDTO);
        
        if (user == null || plantDTO == null) return false;
        if (targetPlant == null) return false;
        if (b) {
    		targetPlant.increaseGrowth((int)(Math.random() * 3));
    		plantDTO.setGrowth(targetPlant.getGrowth());
            userManager.saveCurrentUser();
    		return true;
    	}
        
    	else return false;
    }

    // PlantDataTransfer에 해당하는 Plant 객체 찾기
    private Plant findPlant(UserPlantData data, PlantDataTransfer plantDTO) {
        for (Plant plant : data.getOwnedPlants()) {
            if (plant.getName().equals(plantDTO.getName()) && plant.getGrowth() == plantDTO.getGrowth()) {
                // 성장도로 구분.
                return plant;
            }
        }
        return null;
    }
}
