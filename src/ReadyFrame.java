import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ReadyFrame extends JFrame {
    public ReadyFrame(User user) {
        // 设置窗口标题
        setTitle("一起打飞机");

        // 设置窗口大小
        setSize(600, 400);

        // 设置窗口关闭时的默认操作
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 禁止改动窗口大小
        setResizable(false);
        // 将窗口居中显示
        setLocationRelativeTo(null);

        // 禁用布局管理器
        setLayout(null);
        //更改背景
        JPanel contentPane = getJPanelBackground();
        setContentPane(contentPane);

        // 左上角显示用户名
        JLabel usernameLabel = new JLabel("欢迎"+user.getName());
        usernameLabel.setForeground(Color.red);
        usernameLabel.setBounds(10, 10, 150, 20); // 设置位置和大小
        contentPane.add(usernameLabel);

        // 上半部分中间显示 "一起打飞机"
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

//        // 右上角更换背景按钮
//        JButton changeBackgroundButton = new JButton("更换背景");
//        changeBackgroundButton.setPreferredSize(new Dimension(70, 20)); // 设置按钮大小
//        changeBackgroundButton.setMargin(new Insets(2, 2, 2, 2));
//        changeBackgroundButton.setBounds(500, 10, 70, 20); // 设置位置和大小
//        contentPane.add(changeBackgroundButton);

        // 创建一个面板用于放置竖直排列的按钮
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBounds(225, 150, 200, 200); // 设置位置和大小
        contentPane.add(buttonPanel);

        // 创建四个按钮
        Dimension buttonSize = new Dimension(150, 40);
        JButton startGameButton = new JButton("开始游戏");
        JButton historyButton = new JButton("历史记录");
        JButton selectPlaneButton = new JButton("选择飞机");
        JButton newPlayerButton = new JButton("新手提示");

        startGameButton.setMaximumSize(buttonSize);
        startGameButton.setMinimumSize(buttonSize);

        historyButton.setMaximumSize(buttonSize);
        historyButton.setMinimumSize(buttonSize);

        selectPlaneButton.setMaximumSize(buttonSize);
        selectPlaneButton.setMinimumSize(buttonSize);

        newPlayerButton.setMaximumSize(buttonSize);
        newPlayerButton.setMinimumSize(buttonSize);

        // 将按钮添加到按钮面板，并在每个按钮之间添加 10 像素的间隔
        buttonPanel.add(startGameButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(historyButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(selectPlaneButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(newPlayerButton);
        startGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                // 打开新的 Main_play 窗口
                SwingUtilities.invokeLater(() -> {
                    Main_play mainPlayFrame = new Main_play(user);
                    mainPlayFrame.setVisible(true);
                });
            }
        });
        // 设置窗口可见
        setVisible(true);
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

    public static void main(String[] args) {
        // 在事件调度线程上创建并显示窗口
        SwingUtilities.invokeLater(() -> {
            new ReadyFrame(new User("游客10086","123456",-1));
        });
    }
}