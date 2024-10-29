import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ReadyFrame extends JFrame {
    public String plane = "./resources/plane1.png";
    private User user;

    public ReadyFrame(User user) {
        this.user = user;

        // 设置窗口标题
        plane = user.getPlane();
        setTitle("一起打飞机");

        // 设置窗口大小
        setSize(600, 400);

        // 设置窗口关闭时的默认操作
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 修改默认关闭操作
        // 禁止改动窗口大小
        setResizable(false);
        // 将窗口居中显示
        setLocationRelativeTo(null);

        // 禁用布局管理器
        setLayout(null);
        // 更改背景
        JPanel contentPane = getJPanelBackground();
        setContentPane(contentPane);

        // 左上角显示用户名
        JLabel usernameLabel = new JLabel("欢迎" + user.getName());
        usernameLabel.setForeground(Color.red);
        usernameLabel.setBounds(10, 10, 150, 20); // 设置位置和大小
        contentPane.add(usernameLabel);

        try {
            // 读取原始图片
            BufferedImage originalImage = ImageIO.read(new File("./resources/title.png"));

            // 计算目标大小，保持原始比例
            int targetWidth = 220;
            int targetHeight = 106;
            int originalWidth = originalImage.getWidth();
            int originalHeight = originalImage.getHeight();

            // 计算缩放比例
            double widthRatio = (double) targetWidth / originalWidth;
            double heightRatio = (double) targetHeight / originalHeight;
            double ratio = Math.min(widthRatio, heightRatio);

            // 计算缩放后的尺寸
            int scaledWidth = (int) (originalWidth * ratio);
            int scaledHeight = (int) (originalHeight * ratio);

            // 创建缩放后的图片
            Image scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

            // 创建 ImageIcon 并设置到 JLabel
            ImageIcon titleIcon = new ImageIcon(scaledImage);
            JLabel titleLabel = new JLabel(titleIcon);
            titleLabel.setBounds(200, 30, scaledWidth, scaledHeight); // 设置位置和大小
            contentPane.add(titleLabel);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // 创建一个面板用于放置竖直排列的按钮
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBounds(225, 150, 200, 200); // 设置位置和大小
        contentPane.add(buttonPanel);

        // 创建四个按钮
        Dimension buttonSize = new Dimension(150, 40);
        JButton startGameButton = new JButton("开始游戏");
        JButton choiceButton = new JButton("选择飞机");
        JButton shoppingButton = new JButton("开心商城");
        JButton newPlayerButton = new JButton("新手提示");

        startGameButton.setMaximumSize(buttonSize);
        startGameButton.setMinimumSize(buttonSize);

        choiceButton.setMaximumSize(buttonSize);
        choiceButton.setMinimumSize(buttonSize);

        shoppingButton.setMaximumSize(buttonSize);
        shoppingButton.setMinimumSize(buttonSize);

        newPlayerButton.setMaximumSize(buttonSize);
        newPlayerButton.setMinimumSize(buttonSize);

        // 将按钮添加到按钮面板，并在每个按钮之间添加 10 像素的间隔
        buttonPanel.add(startGameButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(choiceButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(shoppingButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(newPlayerButton);

        choiceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> {
                    choiceFrame choiceFrame = new choiceFrame(user);
                    choiceFrame.setVisible(true);
                });
            }
        });

        startGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                // 打开新的 Main_play 窗口
                SwingUtilities.invokeLater(() -> {
                    Main_play mainPlayFrame = new Main_play(user, plane);
                    mainPlayFrame.setVisible(true);
                });
            }
        });

        shoppingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                // 打开新的 Main_play 窗口
                SwingUtilities.invokeLater(() -> {
                    shoppingFrame shoppingFrame = new shoppingFrame(user);
                    shoppingFrame.setVisible(true);
                });
            }
        });

        // 添加窗口关闭监听器
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onWindowClosing();
            }
        });

        // 设置窗口可见
        setVisible(true);
    }

    private void onWindowClosing() {
        System.out.println("窗口即将关闭");
        user.save();
        // 在这里添加你想要在窗口关闭时执行的操作
    }

    private static JPanel getJPanelBackground() {
        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 设置背景颜色
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, getWidth(), getHeight());

                // 设置背景图片
                ImageIcon backgroundImage = new ImageIcon("./resources/background.png");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };

        // 创建内容面板
        contentPane.setLayout(null); // 禁用布局管理器
        return contentPane;
    }


}