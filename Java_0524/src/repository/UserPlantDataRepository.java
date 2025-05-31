package repository;

import data.UserPlantData;
import data.transfer.UserPlantDataTransfer;
import file.UserPlantDataStore;

import java.util.*;
import java.util.stream.Collectors;

public class UserPlantDataRepository {

    private final UserPlantDataStore fileHandler = new UserPlantDataStore();

    // VO → DTO 변환 후 저장소에 저장
    public void saveAll(List<UserPlantData> allUserPlantData) {
        System.out.println("📦 [Repository] 저장 요청 받은 VO 수: " + allUserPlantData.size());

        List<UserPlantDataTransfer> dtoList = allUserPlantData.stream()
            .map(UserPlantDataTransfer::fromVO)
            .toList();

        for (UserPlantDataTransfer dto : dtoList) {
            System.out.printf("   -> DTO 변환: [%s] 💧%d | 🌾%d | 📦%d개%n",
                dto.getUserId(),
                dto.getWaterTickets(),
                dto.getFertilizerTickets(),
                dto.getOwnedPlants().size()
            );
        }

        fileHandler.saveAll(dtoList);
    }

    // 저장소에서 DTO 불러온 후 VO로 변환
    public Map<String, UserPlantData> loadAll() {
        List<UserPlantDataTransfer> dtoList = fileHandler.loadAll();

        return dtoList.stream()
            .map(UserPlantDataTransfer::toVO)
            .collect(Collectors.toMap(UserPlantData::getUserId, vo -> vo));
    }
}
