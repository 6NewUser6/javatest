import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Scanner;

public class Main_play extends JFrame {
    private final user_plane plane;
    private final RayPanel rayPanel;
    private final Timer timer;
    private int a = 400;
    private final JLabel lifeLabel;
    private final JLabel scoreLabel;
    private final help helpLabel;
    private long lastAddNewRayTime = 0;
    private long lastFindPlaneTime = 0;
    private long lastLifeTime = System.currentTimeMillis()+120000;
    private long lastCheckCollisionsTime = 0;
    private long lastMouseMoveTime = 0;
    private long helpTime=0;
    private int mouseX = 0;
    private int mouseY = 0;
    public Main_play(User user) {
        // 初始化窗口
        setTitle("飞机？boom！");
        setSize(1700, 1200);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // 创建一个 JLayeredPane 来管理组件的层次结构
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1700, 1200));
        setContentPane(layeredPane);

        // 创建一个自定义的 JLabel 并设置其大小为一个圆
        plane = new user_plane();
        plane.setBounds(0, 0, 50, 50); // 设置为与窗口同大小
        setCursor(getToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(),
                "invisibleCursor"));
        // 添加鼠标移动监听器
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                // 更新鼠标位置
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        // 创建并添加 rayPanel
        rayPanel = new RayPanel(plane, this,user);
        rayPanel.setBounds(0, 0, getWidth(), getHeight()); // 设置 rayPanel 的边界
        layeredPane.add(rayPanel, JLayeredPane.DEFAULT_LAYER);
        // 将 trackingLabel 添加到 JFrame 的内容面板
        layeredPane.add(plane, JLayeredPane.DRAG_LAYER);

        // 创建并添加生命值标签
        lifeLabel = new JLabel("生命:5");
        lifeLabel.setBounds(10, 10, 100, 20); // 设置标签的位置和大小
        lifeLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        layeredPane.add(lifeLabel, JLayeredPane.PALETTE_LAYER);

        scoreLabel = new JLabel("当前分数:0");
        scoreLabel.setBounds(1500, 10, 150, 20); // 设置标签的位置和大小
        scoreLabel.setFont(new Font("宋体", Font.PLAIN, 16)); // 设置字体
        layeredPane.add(scoreLabel, JLayeredPane.PALETTE_LAYER);
        // 设置窗口可见
        setVisible(true);
        helpLabel = new help(plane);
        layeredPane.add(helpLabel, JLayeredPane.PALETTE_LAYER);
        helpLabel.setVisible(false);

        // 初始化 Timer，每秒增加一条射线
        timer = new Timer(1, e -> {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastAddNewRayTime >= a) {
                addNewRay();
                lastAddNewRayTime = currentTime;
            }

            if (currentTime - lastFindPlaneTime >= 400) {
                findPlane();
                lastFindPlaneTime = currentTime;
            }

            if (currentTime - lastLifeTime >= 2000) {
                plane.life();
                updateLifeLabel();
            }

            if (currentTime - lastCheckCollisionsTime >= 100) {
                if (!rayPanel.checkCollisions()) {
                    lastLifeTime = currentTime;
                };
                lastCheckCollisionsTime = currentTime;
                rayPanel.died();

            }

            if (currentTime - lastMouseMoveTime >= 2) {
                updatePlanePosition(mouseX, mouseY);
                lastMouseMoveTime = currentTime;
            }
            if (currentTime - helpTime >= 10000 &&currentTime-lastLifeTime>=200) {
                addHelp();
                helpTime=currentTime;
                helpLabel.justDoIt();
                System.out.println("justduit");
            }
        });
        timer.start();
    }

    private void addHelp() {
        helpLabel.setHelp();
        helpLabel.setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main_play frame = new Main_play(new User("游客10086","123456",-1));
            frame.setVisible(true);
        });
    }

    // 增加一条射线的方法
    public void addNewRay() {
        if (rayPanel != null) {
            rayPanel.setAddNewRay(true);
            rayPanel.repaint();
        }
    }

    // 每 400ms 触发一次的方法，选择一个边并向飞机方向发出一条射线
    public void findPlane() {
        if (rayPanel != null) {
            rayPanel.findPlane();
        }
    }

    // 停止所有计时器的方法
    public void stopTimers() {
        if (timer != null) {
            timer.stop();
        }
    }

    // 更新生命值标签的方法
    public void updateLifeLabel() {
        if (lifeLabel != null) {
            lifeLabel.setText("生命:" + plane.getLives());
        }
    }

    public void updateScoreLabel(int score) {
        if (scoreLabel != null) {
            scoreLabel.setText("当前分数：" + score);
        }
    }

    // 更新飞机位置的方法
    public void updatePlanePosition(int x, int y) {
        if (plane != null) {
            if (x > 1650) {
                x = 1650;
            }
            if (y > 1000) {
                y = 1000;
            }
            plane.setLocation(x, y);
            rayPanel.setAddNewRay(false); // 不增加新射线
            rayPanel.repaint(); // 手动重绘 rayPanel
        }
    }
}