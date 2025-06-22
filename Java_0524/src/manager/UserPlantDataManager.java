package manager;

import data.User;
import data.UserPlantData;
import repository.UserPlantDataRepository;

import java.util.*;

// 사용자 id와 매칭하여 식물 성장 상태, 티켓 개수 데이터를 메모리에서 관리하고 필요한 경우 저장소와 동기화 함.
// 작동 기반 객체는 UserPlantData.
public class UserPlantDataManager {
    private final UserPlantDataRepository repository;
    private final Map<String, UserPlantData> dataMap; //

    public UserPlantDataManager(UserPlantDataRepository repository) {
        this.repository = repository;
        this.dataMap = new HashMap<>();

        Map<String, UserPlantData> loaded = repository.loadAll();
        for (Map.Entry<String, UserPlantData> entry : loaded.entrySet()) {
            dataMap.put(entry.getKey(), entry.getValue());
        }
    }

    public UserPlantData getCurrentUserData(User currentUser) {
        return (currentUser == null) ? null : dataMap.get(currentUser.getId());
    }

    public UserPlantData getByUserId(String userId) {
        return dataMap.get(userId);
    }

    public void update(UserPlantData data) {
        if (data != null) {
            dataMap.put(data.getUserId(), data);
        }
    }

    public void registerIfAbsent(User user) {
        if (!dataMap.containsKey(user.getId())) {
            UserPlantData vo = new UserPlantData(
                user.getId(),
                0, 0
            );
            
            dataMap.put(user.getId(), vo);
        }
    }

    public void saveAll() {
    	List<UserPlantData> list = dataMap.values().stream()
    	        .toList();
        repository.saveAll(list);
    }
}
