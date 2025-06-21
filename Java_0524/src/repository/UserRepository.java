package repository;

import data.User;
import file.UserInfoStore;
import java.util.*;

public class UserRepository {
	
	//실제 파일 입출력 역할을 담당하는 UserPlantDataStore 타입의 fileHandler 멤버 변수
	private final UserInfoStore fileHandler = new UserInfoStore();
	
    // 모든 사용자 불러오기
    public Map<String, User> loadAllUsers() {
        return fileHandler.loadAll();
    }
    
    // 사용자 전체 저장
    public void saveAllUsers(Map<String, User> users) {
        fileHandler.saveAll(users.values());
    }

    // 단일 사용자 추가/수정 시에도 Map으로 저장
    public void saveUser(User user, Map<String, User> users) {
        users.put(user.getId(), user);
        saveAllUsers(users);
    }
}
