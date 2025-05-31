package data;
import plant.Plant;
import java.util.*;

public class UserPlantData {
	private final String userId;
    private final List<Plant> ownedPlants;
    private int waterTickets;
    private int fertilizerTickets;
    
    
    public UserPlantData(String userId, int waterTickets, int fertilizerTickets) {
        this.userId = userId;
        this.waterTickets = waterTickets;
        this.fertilizerTickets = fertilizerTickets;
        this.ownedPlants = new ArrayList<>();
    }

    // 최초 생성 시 User 객체 기반
    public UserPlantData(User user) {
        this(user.getId(), user.getWaterTickets(), user.getFertilizerTickets());
    }

    public String getUserId() {
        return userId;
    }

    public List<Plant> getOwnedPlants() {
        return Collections.unmodifiableList(ownedPlants);
    }

    public void addOwnedPlant(Plant plant) {
        ownedPlants.add(plant);
    }

    public int getWaterTickets() {
        return waterTickets;
    }

    public int getFertilizerTickets() {
        return fertilizerTickets;
    }

    public boolean useWaterTicket() {
        if (waterTickets > 0) {
            waterTickets--;
            return true;
        }
        return false;
    }

    public boolean useFertilizerTicket() {
        if (fertilizerTickets > 0) {
            fertilizerTickets--;
            return true;
        }
        return false;
    }

    public void addWaterTickets(int amount) {
        if (amount > 0) {
            waterTickets += amount;
        }
    }

    public void addFertilizerTickets(int amount) {
        if (amount > 0) {
            fertilizerTickets += amount;
        }
    }
}
