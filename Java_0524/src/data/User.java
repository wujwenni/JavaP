package data;

public class User {
	private final String id;
    private final String password;
    private int waterTickets;
    private int fertilizerTickets;
    
    // 티켓 가지고 있는 유저에 대한 생성자.
    public User(String id, String password, int watertickets, int fertilizertickets) {
        this.id = id;
        this.password = password;
        this.waterTickets = watertickets;
        this.fertilizerTickets = fertilizertickets;
    }
    
    // 초기 구성 생성자.
    public User(String id, String password) {
        this.id = id;
        this.password = password;
        this.fertilizerTickets = 0;
        this.waterTickets = 0;
   
    }
    
    // getter
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

    public boolean useWaterTicket() {
        if (waterTickets > 0) {
            waterTickets--;
            //System.out.println("use" + waterTickets);
            return true;
        }
        return false;
    }

    public boolean useFertilizerTicket() {
        if (fertilizerTickets > 0) {
            fertilizerTickets--;
            //System.out.println("use" + fertilizerTickets);
            return true;
        }
        return false;
    }
    
    public boolean checkPassword(String input) {
        return password.equals(input);
    }
    
}
