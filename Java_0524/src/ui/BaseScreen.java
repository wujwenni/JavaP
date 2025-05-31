package ui;

import javax.swing.*;
import java.awt.*;

public abstract class BaseScreen extends JPanel {
	private Image backgroundImage;
	protected final UIManager uiManager;

	public BaseScreen(UIManager uiManager) {
	    this.uiManager = uiManager;
	    initializeScreen();
	}
    

    public void setBackgroundImage(ImageIcon imageIcon) {
        this.backgroundImage = imageIcon.getImage();
        repaint();
    }
    
    private void initializeScreen() {
        setBackground(Color.WHITE);
        setLayout(null);             
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // 패널 크기에 맞게 그림
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
    
}