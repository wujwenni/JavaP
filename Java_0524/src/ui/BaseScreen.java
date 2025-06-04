package ui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public abstract class BaseScreen extends JPanel {
	private Image backgroundImage;
	protected final UIManager uiManager;

	public BaseScreen(UIManager uiManager) {
	    this.uiManager = uiManager;
	    
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
    
    protected abstract void initialize();
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        
    }

}