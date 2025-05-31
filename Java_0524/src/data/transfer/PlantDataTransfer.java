package data.transfer;

public class PlantDataTransfer {
	private String name;
    private int growth;
    
    public PlantDataTransfer(String name, int growth) {
        this.name = name;
        this.growth = growth;
        
    }

    public String getName() {
        return name;
    }

    public int getGrowth() {
        return growth;
    }

    public void setGrowth(int growth) {
        this.growth = growth;
    }
}
