package data.transfer;

import data.UserPlantData;
import plant.Plant;
import plant.PlantFactory;

import java.util.*;
import java.util.stream.Collectors;

public class UserPlantDataTransfer {

    private final String userId;
    private final List<Plant> ownedPlants;
    private final int waterTickets;
    private final int fertilizerTickets;

    public UserPlantDataTransfer(String userId,
                               List<Plant> ownedPlants,
                               int waterTickets, int fertilizerTickets) {
        this.userId = userId;
        this.ownedPlants = ownedPlants;
        this.waterTickets = waterTickets;
        this.fertilizerTickets = fertilizerTickets;
    }

    public static UserPlantDataTransfer fromVO(UserPlantData vo) {
        List<Plant> owned = new ArrayList<>(vo.getOwnedPlants()); // 리스트 복사
        return new UserPlantDataTransfer(
            vo.getUserId(),
            owned,
            vo.getWaterTickets(),
            vo.getFertilizerTickets()
        );
    }

    public static UserPlantData toVO(UserPlantDataTransfer dto) {
        UserPlantData vo = new UserPlantData(dto.userId, dto.waterTickets, dto.fertilizerTickets);
        for (Plant plant : dto.ownedPlants) {
            vo.addOwnedPlant(plant);
        }
        return vo;
    }

    // getter만 제공
    public String getUserId() { return userId; }
    public List<Plant> getOwnedPlants() { return ownedPlants; }
    public int getWaterTickets() { return waterTickets; }
    public int getFertilizerTickets() { return fertilizerTickets; }
}
