package ui.effect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class GrowEffect {
    
    public static void updateAndPlay(JLabel label, int panelW, int potTopY, int fullW, int fullH, int stage, 
    		String plantName, int durationMs, int steps) {
        
    	//stage0: null image
        if (stage <= 0) {
            label.setIcon(null);
            return;
        }

        // 리소스 경로 결정
        String resourcePath;
        if (stage <= 3) {
            resourcePath = "plants/stage" + stage + ".png";
        } else {
            resourcePath = "plants/" + plantName + "_stage4.png";
        }

        // 크기 계산
        int targetH = fullH;
        int targetW = fullW;
        int x       = (panelW - targetW) / 2;

        ImageIcon icon = resizeIcon(resourcePath, targetW, targetH);
        label.setIcon(icon);

        // 수직 정렬, 초기 크기 설정
        label.setVerticalAlignment(SwingConstants.BOTTOM);
        label.setBounds(x, potTopY, targetW, 0);

        // 애니메이션 타이머 설정
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

    private static ImageIcon resizeIcon(String resourcePath, int w, int h) {
        URL url = GrowEffect.class.getClassLoader().getResource(resourcePath);
        if (url == null) {
            //System.err.println("실패 : " + resourcePath);
            return null;
        }
        Image raw = new ImageIcon(url).getImage();
        Image scaled = raw.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}
