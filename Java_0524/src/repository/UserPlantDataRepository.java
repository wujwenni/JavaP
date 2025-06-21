package repository;

import data.UserPlantData;
import data.transfer.UserPlantDataTransfer;
import file.UserPlantDataStore;

import java.util.*;
import java.util.stream.Collectors;

// 레포지토리 클래스는 메모리 상에 있는 사용자 식물 데이터와 실제 데이터를 저장하는 파일 입출력 과정의 중간 연결자 역할.
public class UserPlantDataRepository {

	// 실제 파일 입출력 역할을 담당하는 UserPlantDataStore 타입의 fileHandler 멤버 변수
    private final UserPlantDataStore fileHandler = new UserPlantDataStore(); 
    
    // VO에서 DTO로 변환 후 저장소에 저장
    public void saveAll(List<UserPlantData> allUserPlantData) { 
    	// 프로그램 동작 중, 변경이나 수정이 발생한 사용자 식물 데이터를 DTO로 변환하는 과정
        List<UserPlantDataTransfer> dtoList = allUserPlantData.stream()
            .map(UserPlantDataTransfer::fromVO) // UserPlantDataTransfer의 정적 메서드 fromVO를 사용
            .toList();
        fileHandler.saveAll(dtoList);
    }

    // 저장소에서 DTO 불러온 후 VO로 변환, Manager 클래스의 dataMap 변수가 <User, UserPlantData> 타입이기 때문에 필요한 메서드
    // 텍스트 파일에서 사용자 식물 정보를 불러오는 타입을 DTO로 제한하고 loadAll 메서드를 통해 값을 수정할 수 있는 객체로 변환하는 것이 설계 목적에 올바름.
    public Map<String, UserPlantData> loadAll() {
        // 텍스트 파일에서 불러온 DTO 형태의 사용자 식물 정보를 수정 가능한 객체로 바꾸는 과정
    	List<UserPlantDataTransfer> dtoList = fileHandler.loadAll();
        return dtoList.stream()
            .map(UserPlantDataTransfer::toVO) // UserPlantDataTransfer의 정적 메서드 toVO를 사용
            .collect(Collectors.toMap(UserPlantData::getUserId, vo -> vo));
    }
}
