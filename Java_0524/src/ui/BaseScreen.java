package ui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public abstract class BaseScreen extends JPanel {
	private Image backgroundImage;
	protected final UIManager uiManager;

	public BaseScreen(UIManager uiManager) {
	    this.uiManager = uiManager;
	    //initializeScreen();
	}
    
    public void setBackgroundImage(String relativePath) {
    	URL resource = getClass().getClassLoader().getResource(relativePath);
        if (resource != null) {
        	System.out.println("success");
            this.backgroundImage = new ImageIcon(resource).getImage();  // ← 원본 이미지만 저장
            repaint();
        } else {
            System.err.println("배경 이미지 로딩 실패: " + relativePath);
        }
    }
    
    private void initializeScreen() {
        setBackground(Color.WHITE);
        setLayout(null);             
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            System.out.println("[DEBUG] paintComponent 호출됨: " + getWidth() + "x" + getHeight());
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            System.out.println("[DEBUG] 배경 이미지가 null임");
        }
    }

    
}