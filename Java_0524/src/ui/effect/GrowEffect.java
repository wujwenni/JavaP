package ui.effect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * GrowEffect
 *
 * 아래→위로 자라는 애니메이션과 함께 스테이지별 이미지를 세팅해 주는 유틸리티 클래스입니다.
 */
public class GrowEffect {
    /**
     * 스테이지(stage0~4)에 맞춰 JLabel의 아이콘·크기·위치를 세팅하고,
     * 아래→위로 자라는 애니메이션을 실행합니다.
     *
     * @param label       성장 애니메이션을 적용할 JLabel
     * @param panelW      패널 전체 너비 (가로 중앙 정렬용)
     * @param potTopY     화분 꼭대기 Y 좌표 (이미지 바닥 앵커)
     * @param fullW       만개 이미지 원본 너비
     * @param fullH       만개 이미지 원본 높이
     * @param stage       성장 단계 (0: 숨김, 1~3: 스프라우트, 4: 풀블룸)
     * @param plantName   "rose", "tulip", "sunflower" 중 하나 (stage4에서만 사용)
     * @param durationMs  애니메이션 전체 시간(밀리초)
     * @param steps       애니메이션 프레임 수
     */
    public static void updateAndPlay(JLabel label,
                                     int panelW,
                                     int potTopY,
                                     int fullW,
                                     int fullH,
                                     int stage,
                                     String plantName,
                                     int durationMs,
                                     int steps) {
        // 1) stage0: 숨김 처리
        if (stage <= 0) {
            label.setIcon(null);
            return;
        }

        // 2) 리소스 경로 결정
        String resourcePath;
        if (stage <= 3) {
            resourcePath = "plants/stage" + stage + ".png";
        } else {
            resourcePath = "plants/" + plantName + "_stage4.png";
        }

        // 3) 목표 크기 계산
        int targetH = fullH;
        int targetW = fullW;
        int x       = (panelW - targetW) / 2;

        // 4) 아이콘 세팅 (미리 전체 크기로 로드)
        ImageIcon icon = resizeIcon(resourcePath, targetW, targetH);
        label.setIcon(icon);

        // 5) 수직 정렬(BOTTOM) & 초기 bounds (높이 0)
        label.setVerticalAlignment(SwingConstants.BOTTOM);
        label.setBounds(x, potTopY, targetW, 0);

        // 6) 애니메이션 타이머 설정
        int delay  = durationMs / steps;
        int deltaH = Math.max(1, targetH / steps);

        Timer timer = new Timer(delay, null);
        timer.addActionListener(new ActionListener() {
            int currentH = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                currentH = Math.min(targetH, currentH + deltaH);
                int y = potTopY - currentH;
                label.setBounds(x, y, targetW, currentH);
                if (currentH >= targetH) {
                    ((Timer) e.getSource()).stop();
                }
            }
        });

        timer.start();
    }

    /**
     * 리소스 경로에서 이미지를 로드해 주어진 크기로 스무스 리사이징한 ImageIcon을 반환합니다.
     *
     * @param resourcePath 클래스패스 내 이미지 경로
     * @param w            출력 너비
     * @param h            출력 높이
     * @return 리사이즈된 ImageIcon, 로드 실패 시 null
     */
    private static ImageIcon resizeIcon(String resourcePath, int w, int h) {
        URL url = GrowEffect.class.getClassLoader().getResource(resourcePath);
        if (url == null) {
            System.err.println("GrowEffect: 리소스 로드 실패 → " + resourcePath);
            return null;
        }
        Image raw = new ImageIcon(url).getImage();
        Image scaled = raw.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}
