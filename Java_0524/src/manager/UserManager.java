package manager;

import data.User;
import repository.UserRepository;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private final UserRepository repository = new UserRepository();
    private final Map<String, User> userMap;
    private User currentUser;

    public UserManager() {
        this.userMap = new HashMap<>(repository.loadAllUsers()); // 저장소에서 초기 사용자 목록 로드
    }

    // 로그인 처리
    public boolean login(User user) {
        User saved = userMap.get(user.getId());
        if (saved != null && saved.checkPassword(user.getPassword())) {
            currentUser = saved;
            return true;
        }
        return false;
    }

    // 회원가입 처리
    public boolean register(User user) {
        if (userMap.containsKey(user.getId())) {
            return false; // 이미 존재하는 ID
        }
        userMap.put(user.getId(), user);
        repository.saveAllUsers(userMap); // 전체 Map 저장
        return true;
    }

    // 현재 로그인된 사용자 반환
    public User getCurrentUser() {
        return currentUser;
    }

    // 사용자 정보 변경 시 저장 (예: 티켓 사용 후)
    public void saveCurrentUser() {
        if (currentUser != null) {
            userMap.put(currentUser.getId(), currentUser);
            repository.saveAllUsers(userMap); // 전체 Map 저장
        }
        else System.out.println("null");
    }
}
