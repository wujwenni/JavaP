package manager;

import data.User;
import data.UserPlantData;
import data.transfer.UserPlantDataTransfer;
import repository.UserPlantDataRepository;

import java.util.*;

public class UserPlantDataManager {
    private final UserPlantDataRepository repository;
    private final Map<String, UserPlantData> dataMap;

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
                user.getWaterTickets(),
                user.getFertilizerTickets()
            );
            
            dataMap.put(user.getId(), vo);
        }
    }


    public void saveAll() {
    	List<UserPlantData> list = dataMap.values().stream()
    	        .toList();
        System.out.println("🧪 [Manager] 저장할 유저 수: " + list.size());
        
        for (UserPlantData data : list) {
            System.out.printf(" - [%s] 물 티켓: %d, 비료 티켓: %d, 보유 식물 수: %d개%n",
                data.getUserId(),
                data.getWaterTickets(),
                data.getFertilizerTickets(),
                data.getOwnedPlants().size()
            );
        }

        repository.saveAll(list);
    }

    public Map<String, UserPlantData> getAll() {
        return Collections.unmodifiableMap(dataMap);
    }

}
