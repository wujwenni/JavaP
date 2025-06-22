package data;

public class User {
	private final String id;
    private final String password;
    
    
    // 초기 구성 생성자.
    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }
    
    // getter
    public String getId() {
        return id;
    }
    
    public String getPassword() {
    	return password;
    }
    
    
    public boolean checkPassword(String input) {
        return password.equals(input);
    }
    
}
