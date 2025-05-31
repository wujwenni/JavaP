package plant;

public class PlantFactory {
	
	public static Plant create(String name, int growth) {
        switch (name) {
            case "장미":
                return new Rose(growth);
            case "튤립":
                return new Tulip(growth);
            case "해바라기":
                return new Sunflower(growth);
            default:
                throw new IllegalArgumentException("알 수 없는 식물 이름: " + name);
        }
        
	}
	
}
