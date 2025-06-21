package file;

import data.transfer.UserPlantDataTransfer;
import plant.Plant;
import plant.PlantFactory;

import java.io.*;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;

public class UserPlantDataStore {

    private static final String FILE_PATH = "user_plant_data.txt"; // 저장될 파일의 이름을 정의 

    // DTO를 통해 텍스트 파일에 저장
    public void saveAll(Collection<UserPlantDataTransfer> dtoList) {
    	// 인자 타입을 컬렉션으로 지정해 List, Set, ArrayList로도 받을 수 있음.
    	// try-catch로 감싸 예외처리, 리소스 관리 용이성 높임 (명시적인 종료 하지 않아도 됨)
    	// 덮어쓰기 모드로 염, 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            //File file = new File(FILE_PATH); // 디버깅 출력을 위한 File 변수 선언.
            //System.out.println("store path: " + file.getAbsolutePath());
            //System.out.println("number of data to save: " + dtoList.size());
            for (UserPlantDataTransfer dto : dtoList) {
                StringBuilder sb = new StringBuilder(); // 문자열 조작에 있어서 + 대신 스트링빌더를 사용하면 더 편리함

                sb.append(dto.getUserId()).append("|"); // 저장 정보를 구분하기 위한 | 삽입
                sb.append(dto.getWaterTickets()).append("|");
                sb.append(dto.getFertilizerTickets()).append("|");

                // Plant 객체 리스트를 이름:성장도 문자열로 변환하여 저장
                List<String> plants = dto.getOwnedPlants().stream()
                    .map(p -> p.getName() + ":" + p.getGrowth())
                    .toList();

                String line = sb.append(String.join(",", plants)).toString(); // 이름: 성장도 문자열로 변환하여 저장된 요소들을 구분하기 위한 , 삽입.
                writer.write(line);
                writer.newLine();

                //System.out.printf("저장 완료: %s%n", line); // line에 대한 디버깅 출력
            }

            //System.out.println("저장 완료"); // 전체에 대한 디버깅 출력
        } catch (IOException e) {
            //System.err.println("저장 실패: " + e.getMessage()); // 예외 발생 디버깅 출력
        }
    }

    // user_plant_data.txt에서 데이터 긁어오기
    public List<UserPlantDataTransfer> loadAll() {
        List<UserPlantDataTransfer> list = new ArrayList<>();
        File file = new File(FILE_PATH);

        //System.out.println("file path: " + file.getAbsolutePath());
        //System.out.println("existence : " + file.exists());
        if (!file.exists()) return list;
        //System.out.println("starting to read file contents");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            
        	String line;
            
            while ((line = reader.readLine()) != null) {
                System.out.println("read line : [" + line + "]");

                String[] parts = line.split("\\|", -1);  // 빈 필드 포함

                if (parts.length < 4) {
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

                UserPlantDataTransfer dto = new UserPlantDataTransfer(userId, owned, water, fertilizer);
                list.add(dto);
            }
        } 
        catch (IOException | NumberFormatException e) {
            System.err.println("failed: " + e.getMessage());
        }

        return list;
    }
}
