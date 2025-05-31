package file;

import data.transfer.UserPlantDataTransfer;
import plant.Plant;
import plant.PlantFactory;

import java.io.*;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;

public class UserPlantDataStore {

    private static final String FILE_PATH = "user_plant_data.txt";

    // DTO → 파일 저장
    public void saveAll(List<UserPlantDataTransfer> dtoList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            File file = new File(FILE_PATH);
            System.out.println("📁 [Store] 저장 경로: " + file.getAbsolutePath());
            System.out.println("📄 [Store] 저장할 데이터 수: " + dtoList.size());

            for (UserPlantDataTransfer dto : dtoList) {
                StringBuilder sb = new StringBuilder();

                sb.append(dto.getUserId()).append("|");
                sb.append(dto.getWaterTickets()).append("|");
                sb.append(dto.getFertilizerTickets()).append("|");

                // Plant 객체 리스트 → 이름:성장도 문자열로 변환
                List<String> plants = dto.getOwnedPlants().stream()
                    .map(p -> p.getName() + ":" + p.getGrowth())
                    .toList();

                String line = sb.append(String.join(",", plants)).toString();
                writer.write(line);
                writer.newLine();

                System.out.printf("✅ [Store] 저장 완료 → %s%n", line);
            }

            System.out.println("🎉 [Store] 저장 완료");
        } catch (IOException e) {
            System.err.println("🌱 [Store] 저장 실패: " + e.getMessage());
        }
    }

    // 파일 → DTO 불러오기
    public List<UserPlantDataTransfer> loadAll() {
        List<UserPlantDataTransfer> list = new ArrayList<>();
        File file = new File(FILE_PATH);

        System.out.println("📁 절대 경로: " + file.getAbsolutePath());
        System.out.println("📄 존재 여부: " + file.exists());
        if (!file.exists()) return list;
        System.out.println("📄 파일 내용 읽기 시작");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("[DEBUG] 읽은 라인: [" + line + "]");

                String[] parts = line.split("\\|", -1);  // 빈 필드 포함
                System.out.println("[DEBUG] 필드 수: " + parts.length);

                if (parts.length < 4) {
                    System.out.println("❌ 필드 수 부족으로 무시됨: " + line);
                    continue;
                }

                String userId = parts[0];
                int water = Integer.parseInt(parts[1]);
                int fertilizer = Integer.parseInt(parts[2]);
                List<Plant> owned = new ArrayList<>();
                if (!parts[3].isEmpty()) {
                    String[] tokens = parts[3].split(",");
                    for (String token : tokens) {
                        String[] pair = token.split(":");
                        if (pair.length == 2) {
                            String name = pair[0];
                            int growth = Integer.parseInt(pair[1]);
                            owned.add(PlantFactory.create(name, growth));
                        }
                    }
                }

                UserPlantDataTransfer dto = new UserPlantDataTransfer(
                    userId, owned, water, fertilizer
                );
                list.add(dto);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("🌱 불러오기 실패: " + e.getMessage());
        }

        return list;
    }
}
