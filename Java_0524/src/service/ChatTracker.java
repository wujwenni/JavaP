package service;
import java.util.*;

public class ChatTracker {
	private static final Set<String> KEYWORDS = Set.of(
	        "응원해", "사랑해", "잘자", "예쁘다", "행복해", "괜찮아", "힘내", "멋지다", "대단해", "고마워"
	    );
	
	private int matchCount = 0;
    private static boolean growthReached = false; // 성장도 반영 받을 수 있는 지 여부
    
    public void processUserInput(String input) {
    	if (input == null || input.isBlank()) return;
        // 키워드가 포함된 경우 매칭
        for (String keyword : KEYWORDS) {
            if (input.contains(keyword)) {
                matchCount++;
                //System.out.println("키워드 매칭: \"" + keyword + "\" (누적 " + matchCount + "회)");

                if (matchCount >= 6 && matchCount <= 10) {
                	growthReached = true;
                }

                // 더 이상 성장 누적 안 함
                if (matchCount > 10) {
                    growthReached = true;
                }

                break; // 한 문장에 여러 키워드 있어도 1회만 증가
            }
        }
    }

    public static boolean getAccumulatedGrowth() {
        return growthReached;
    }

    public void reset() {
        matchCount = 0;
        growthReached = false;
    }
}
