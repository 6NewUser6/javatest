import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class Main_play extends JFrame {
    private final user_plane plane;
    private final RayPanel rayPanel;
    private final Timer timer;
    private int a = 400;
    private final JLabel lifeLabel;
    private final JLabel scoreLabel;
    private final JLabel mygoldLabel;
    private final help helpLabel;
    private final gold goldLabel;
    private long lastAddNewRayTime = 0;
    private long lastFindPlaneTime = 0;
    private long lastLifeTime = System.currentTimeMillis() + 120000;
    private long lastCheckCollisionsTime = 0;
    private long lastMouseMoveTime = 0;
    private long helpTime = 0;
    private long goldTime = System.currentTimeMillis() + 1000;
    private int mouseX = 0;
    private int mouseY = 0;
    private User user;
    public Main_play(User user, String file) {
        // 初始化窗口
        this.user = user;
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
        plane = new user_plane(file);
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
        rayPanel = new RayPanel(plane, this, user);
        rayPanel.setBounds(0, 0, getWidth(), getHeight()); // 设置 rayPanel 的边界
        rayPanel.setVisible(true); // 确保 rayPanel 可见
        layeredPane.add(rayPanel, Integer.valueOf(1));
        // 将 trackingLabel 添加到 JFrame 的内容面板
        layeredPane.add(plane,Integer.valueOf(5));

        // 创建并添加生命值标签
        lifeLabel = new JLabel("生命:5");
        lifeLabel.setBounds(10, 10, 100, 20); // 设置标签的位置和大小
        lifeLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        layeredPane.add(lifeLabel, Integer.valueOf(2));

        scoreLabel = new JLabel("当前分数:0");
        scoreLabel.setBounds(1500, 10, 150, 20); // 设置标签的位置和大小
        scoreLabel.setFont(new Font("宋体", Font.PLAIN, 16)); // 设置字体
        layeredPane.add(scoreLabel, Integer.valueOf(2));

        mygoldLabel = new JLabel("此次获得金币:0");
        mygoldLabel.setBounds(1500, 50, 150, 20); // 设置标签的位置和大小
        mygoldLabel.setFont(new Font("宋体", Font.PLAIN, 16)); // 设置字体
        layeredPane.add(mygoldLabel,Integer.valueOf(2));
        ImageIcon backgroundImageIcon = new ImageIcon("./resources/mainBackground.jpg");
        Image backgroundImage = backgroundImageIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundImageIcon = new ImageIcon(backgroundImage);
        JLabel backgroundLabel = new JLabel(scaledBackgroundImageIcon);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // 设置窗口可见
        setVisible(true);
        helpLabel = new help(plane);
        layeredPane.add(helpLabel,Integer.valueOf(2));
        helpLabel.setVisible(false);
        goldLabel = new gold(plane);
        layeredPane.add(goldLabel, Integer.valueOf(2));
        goldLabel.setVisible(false);
        // 初始化 Timer，每秒增加一条射线
        timer = new Timer(10, e -> {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastAddNewRayTime >= a) {
                addNewRay();
                a=(a>200?(int)Math.round(a*0.99):200);
                lastAddNewRayTime = currentTime;
            }

            if (currentTime - lastFindPlaneTime >= 1000) {
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
                }
                lastCheckCollisionsTime = currentTime;
                rayPanel.died();
            }

            if (currentTime - lastMouseMoveTime >= 10) {
                updatePlanePosition(mouseX, mouseY);
                lastMouseMoveTime = currentTime;
            }
            if (currentTime - helpTime >= 10000 && currentTime - lastLifeTime >= 300) {
                addHelp();
                helpTime = currentTime;
                helpLabel.justDoIt();
            }
            if (currentTime - goldTime >= 10000) {
                addGold();
                goldTime = currentTime;
                goldLabel.justDoIt(user,this);
                System.out.println("现在的金币"+user.getGold());
            }
        });
        timer.start();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onWindowClosing();
            }
        });
    }

    public void updategoldLable() {
        if (mygoldLabel != null) {
            mygoldLabel.setText("当前金币：" + user.getGold());
        }
    }

    private void onWindowClosing() {
        System.out.println("窗口即将关闭");
        user.save();
        stopTimers(); // 停止所有计时器
    }

    private void addHelp() {
        helpLabel.setHelp();
        helpLabel.setVisible(true);
    }

    private void addGold() {
        goldLabel.setGold();
        goldLabel.setVisible(true);
    }



    // 增加一条射线的方法
    public void addNewRay() {
        if (rayPanel != null) {
            rayPanel.setAddNewRay();
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
            rayPanel.repaint(); // 手动重绘 rayPanel
        }
    }
}