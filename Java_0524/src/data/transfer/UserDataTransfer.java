package data.transfer;

public class UserDataTransfer {
	private String id;
    private String password;
    private int waterTickets;
    private int fertilizerTickets;

    // 기본 사용자 식물 정보 DTO 생성자
    public UserDataTransfer(String id, String password, int watertickets, int fertilizertickets) {
        this.id = id;
        this.password = password;
        this.waterTickets = watertickets;
        this.fertilizerTickets = fertilizertickets;
    }
    
    
    // 초기 사용자 식물 정보 DTO 생성자
    public UserDataTransfer(String id, String password) {
    	this.id = id;
        this.password = password;
        this.waterTickets = 0;
        this.fertilizerTickets = 0;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
    
    public int getWaterTickets() {
    	return waterTickets;
    }
    
    public int getFertilizerTickets() {
    	return fertilizerTickets;
    }
}
