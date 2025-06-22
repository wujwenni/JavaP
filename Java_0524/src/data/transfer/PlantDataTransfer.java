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
    	// 성장도에 한해 예외적으로 DTO에도 setter 메서드 정의 및 구현.
        this.growth = growth;
    }
}
