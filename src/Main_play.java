import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main_play extends JFrame {
    private CirclePanel trackingPanel;

    public Main_play() {
        // 初始化窗口
        setTitle("飞机？boom！");
        setSize(4000, 4000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // 创建一个自定义的 JPanel 并设置其大小为一个圆
        trackingPanel = new CirclePanel();
        trackingPanel.setBounds(0,0,50,50);
        setCursor(getToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(),
                "invisibleCursor"));
        // 添加鼠标移动监听器
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                // 更新 JPanel 的位置
                trackingPanel.setLocation(e.getX() , e.getY() );
            }
        });

        // 将 JPanel 添加到窗口
        add(trackingPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main_play frame = new Main_play();
            frame.setVisible(true);
        });
    }

    // 自定义的 JPanel 类，用于绘制圆形
    static class CirclePanel extends JPanel {
        private BufferedImage image;

        public CirclePanel() {
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
    }
}