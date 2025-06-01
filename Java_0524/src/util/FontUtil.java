package util;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

public class FontUtil {
    
    public static Font loadFont(String path, float size) {
        try (InputStream is = FontUtil.class.getResourceAsStream(path)) {
            Font font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(size);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
            return font;
        } catch (Exception e) {
            System.err.println("⚠️ 폰트 로딩 실패: " + path);
            return new Font("SansSerif", Font.PLAIN, (int) size); // fallback
        }
    }
}
