package data.transfer;

public class UserDataTransfer {
	private String id;
    private String password;
    private int waterTickets;
    private int fertilizerTickets;

    public UserDataTransfer(String id, String password, int watertickets, int fertilizertickets) {
        this.id = id;
        this.password = password;
        this.waterTickets = watertickets;
        this.fertilizerTickets = fertilizertickets;
    }
    
    public UserDataTransfer(String id, String password) {
    	this.id = id;
        this.password = password;
        this.waterTickets = 100;
        this.fertilizerTickets = 100;
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
