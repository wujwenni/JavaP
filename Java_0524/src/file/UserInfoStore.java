package file;

import data.User;
import java.io.*;
import java.util.*;

public class UserInfoStore {
    private static final String FILE_PATH = "users.txt";

    // User 정보를 파일에 저장 (기존 내용 덮어쓰기)
    public void saveAll(Collection<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (User user : users) {
                writer.write(String.format("%s,%s,%d,%d",
                    user.getId(),
                    user.getPassword(),
                    user.getWaterTickets(),
                    user.getFertilizerTickets()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 파일에서 모든 User 정보 불러오기
    public Map<String, User> loadAll() {
        Map<String, User> userMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length == 4) {
                    String id = tokens[0];
                    String pw = tokens[1];
                    int waterTickets = Integer.parseInt(tokens[2]);
                    int fertilizerTickets = Integer.parseInt(tokens[3]);
                    userMap.put(id, new User(id, pw, waterTickets, fertilizerTickets));
                }
            }
        } catch (IOException e) {
            // 파일이 없을 수 있음
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return userMap;
    }
}
