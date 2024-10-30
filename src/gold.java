import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class gold extends JLabel implements Obstacle {
    int x;
    int y;
    user_plane plane;
    boolean flag = false;
    private BufferedImage image;
    private Timer timer;


    public gold(user_plane plane) {
        Random random = new Random();
        int width = 1700;
        int height = 1200;
        this.x = random.nextInt(width - 200);
        this.y = random.nextInt(height - 200);
        this.setBounds(x, y, 40, 40);
        try {
            // 加载贴图
            image = ImageIO.read(new File("./resources/gold.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.plane = plane;
    }

    @Override
    public boolean judgement(int x, int y) {
        // 计算给定坐标与 help 对象中心的距离
        int dx = Math.abs(this.x - x);
        int dy = Math.abs(this.y - y);
        // 判断是否在半径为 10 的方块内
        return dx <= 20 && dy <= 20;
    }

    public void setGold() {
        Random random = new Random();
        int width = 1700;
        int height = 1200;
        this.x = random.nextInt(width - 200);
        this.y = random.nextInt(height - 200);
        this.setBounds(x, y, getWidth(), getHeight());
    }

    public void justDoIt(User user,Main_play play) {
        // 调整图像大小
        Image scaledImage = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(scaledImage));
        flag = true;
        timer = new Timer(100, new ActionListener() {
            private int step = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (step <= 30) {
                    if (judgement(plane.getX(), plane.getY())&& flag) {
                        setIcon(null);
                        System.out.println("win!");
                        user.gold();
                        System.out.println(user.getGold());
                        flag = false;
                        play.updategoldLable();
                    }
                } else {
                    setIcon(null);
                    // 停止计时器并重置 step
                    timer.stop();
                    step = 0;
                }
                step++;
            }
        });
        timer.start();
    }
}