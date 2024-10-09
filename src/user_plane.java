import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class user_plane extends JLabel {
    private BufferedImage image;
    private boolean life = true; // 生命状态
    private int lives =5;
    public int getLives(){
        return lives;
    }
    public user_plane() {
        try {
            // 加载贴图
            image = ImageIO.read(new File("./resources/blueplane.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            // 绘制贴图
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        } else {
            // 如果贴图加载失败，使用红色填充
            g.setColor(Color.RED);
            g.fillOval(0, 0, getWidth(), getHeight());
        }
    }

    // 设置生命状态为 true
    public void life() {
        life = true;
    }
    public void help() {lives+=1;}
    // 获取生命状态
    public boolean isLife() {
        return life;
    }
    public void killed(){
        lives--;
        life = false;
    }
}