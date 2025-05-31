package repository;

import data.User;
import file.UserInfoStore;
import java.util.*;

public class UserRepository {
    private final UserInfoStore fileHandler = new UserInfoStore();

    // 모든 사용자 불러오기
    public Map<String, User> loadAllUsers() {
        return fileHandler.loadAll();
    }

    // 사용자 저장 (Map 전체 저장)
    public void saveAllUsers(Map<String, User> users) {
        fileHandler.saveAll(users.values());
    }

    // 단일 사용자 추가/수정 시에도 Map으로 저장 (권장)
    public void saveUser(User user, Map<String, User> users) {
        users.put(user.getId(), user);
        saveAllUsers(users);
    }
}
