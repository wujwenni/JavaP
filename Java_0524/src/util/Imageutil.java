package util;
import javax.swing.*;


import java.awt.*;
import java.net.URL;

public class Imageutil {
	public static ImageIcon loadIcon(String relativePath, int width, int height) {
        URL resource = Imageutil.class.getClassLoader().getResource(relativePath);
        if (resource == null) {
            System.err.println("이미지 로딩 실패: " + relativePath);
            return null;
        }
        
        ImageIcon icon = new ImageIcon(resource);
        Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}
