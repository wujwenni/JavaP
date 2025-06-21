package manager;

import data.User;
import repository.UserRepository;
import java.util.HashMap;
import java.util.Map;

// 사용자 인증 상태를 메모리에 유지하고, 로그인, 회원가입 로직과 연결된 저장 처리를 담당함
// 자세한 과정: 프로그램 시작 시, 모든 유저의 정보를 repository 클래스의 load 메서드를 통해 텍스트 파일에서 읽어 온 유저 정보를 바탕으로 메모리에 띄움. 
// 유저 정보의 수정과 변경은 userMap을 수정하는 것.
public class UserManager {
    private final UserRepository repository = new UserRepository(); // 전체 사용자 정보를 파일로 저장, 불러오기를 담당함.
    private final Map<String, User> userMap; // 모든 사용자 정보를 Map에 프로그램 초기 실행 시 메모리 상에 보관.
    private User currentUser; 

    public UserManager() {
        this.userMap = new HashMap<>(repository.loadAllUsers()); // HashMap으로 구현, 인자로 repository 클래스의 정보 로드 메서드.
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

    // 사용자 정보 변경 시 저장
    public void saveCurrentUser() {
        if (currentUser != null) {
            userMap.put(currentUser.getId(), currentUser);
            repository.saveAllUsers(userMap);
        }
        else System.out.println("null");
    }
}
