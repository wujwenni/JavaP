package data.transfer;

import data.UserPlantData;
import plant.Plant;
import plant.PlantFactory;

import java.util.*;
import java.util.stream.Collectors;

// 프로그램 개발 목적에 따른 DataTransfer 객체의 기본적인 특징
// 1. has only getter methods - 값의 변경을 직접적으로 나타내거나 표현할 수 없어야 함.
// 2. DataTransfer의 기본형 객체로 다시 표현되어 값을 변경한 이후 새로운 DataTransfer 객체에 복사한 후 컨트롤러와 뷰에 전달.
// 3. UserDataManager, UserPlantDataManager를 통해 메모리에 띄워져 있는 값으로 만들어짐.
// 텍스트 파일에서 UserPlantData 객체를 하나 만들고, 레포지토리 클래스를 통해 DTO나 Domain(기존 UserPlantData) 객체로 변환함.
public class UserPlantDataTransfer {
	// final 사용으로 단 한 번만 초기화 (DTO의 특성인 값의 변경이나 수정을 새로운 객체 생성을 통해 표현하도록 함.)
    private final String userId;
    private final List<Plant> ownedPlants; // List 내부 요소(Plant) 변경은 가능합니다, final로 선언하더라도 ownedPlants 자체를 다른 리스트 객체로 변경할 수 없음을 나타냄.
    private final int waterTickets;
    private final int fertilizerTickets;
    
    public UserPlantDataTransfer(String userId, List<Plant> ownedPlants,
                               int waterTickets, int fertilizerTickets) {
        this.userId = userId;
        this.ownedPlants = ownedPlants;
        this.waterTickets = waterTickets;
        this.fertilizerTickets = fertilizerTickets;
    }
    
    // UserPlantDataTranfer 객체의 정보를 기반으로 한 UserPlantData 객체 반환 전역 메서드 
    // 프로그램 로직 메서드 실행을 위해 만들었음
    // 아래 두 개 전역 메서드는 컨트롤러, 뷰, 모델의 관심사 및 역할 분리 목적으로 만들었음.
    // fromVO: 변경된 UserPlantData의 상태를 외부에 복사해서 전달하는 전역 메서드.
    public static UserPlantDataTransfer fromVO(UserPlantData vo) {
    	List<Plant> owned = new ArrayList<>(vo.getOwnedPlants()); 
        return new UserPlantDataTransfer(
            vo.getUserId(),
            owned,
            vo.getWaterTickets(),
            vo.getFertilizerTickets()
        );
    }	
    // toVO: UserPlantDataTransfer 타입 객체를 값의 변경을 반영할 수 있는 UserPlantData 객체로 변환. 
    public static UserPlantData toVO(UserPlantDataTransfer dto) {
        UserPlantData vo = new UserPlantData(dto.userId, dto.waterTickets, dto.fertilizerTickets);
        for (Plant plant : dto.ownedPlants) {
            vo.addOwnedPlant(plant);
        }
        return vo;
    }

    // 온리 getter.
    public String getUserId() { 
    	return userId; 
    }
    
    public List<Plant> getOwnedPlants() { 
    	return ownedPlants; 
    }
    
    public int getWaterTickets() { 
    	return waterTickets; 
    }
    
    public int getFertilizerTickets() { 
    	return fertilizerTickets;	
    }
}
