package file;

import data.User;
import java.io.*;
import java.util.*;

public class UserInfoStore {
    private static final String FILE_PATH = "users.txt";

    // User 정보를 파일에 저장 or 덮어쓰기.
    public void saveAll(Collection<User> users) {
    	// 인자 타입을 컬렉션으로 지정해 List, Set, ArrayList로도 받을 수 있음.
    	// try-catch로 감싸 예외처리, 리소스 관리 용이성 높임 (명시적인 종료 하지 않아도 됨)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (User user : users) {
                writer.write(String.format("%s,%s",
                    user.getId(),
                    user.getPassword()
                    ));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(); // 오류 발생 시 콘솔 창에 호출 경로 출력.
        }
    }

    // 파일에서 모든 User 정보 불러오기
    public Map<String, User> loadAll() {
        Map<String, User> userMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length == 2) {
                    String id = tokens[0];
                    String pw = tokens[1];
                    userMap.put(id, new User(id, pw));
                }
            }
        } catch (IOException e) {
            // 예외 처리
        	e.printStackTrace();
        } catch (NumberFormatException e) {
        	e.printStackTrace();
        }
        return userMap;
    }
}
