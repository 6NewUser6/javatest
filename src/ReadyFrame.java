import javax.swing.*;
import java.awt.*;

public class ReadyFrame extends JFrame {
    public ReadyFrame(String username) {
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
        JLabel usernameLabel = new JLabel("欢迎"+username);
        usernameLabel.setBounds(10, 10, 150, 20); // 设置位置和大小
        contentPane.add(usernameLabel);

        // 上半部分中间显示 "一起打飞机"
        JLabel titleLabel = new JLabel("一起打飞机");
        titleLabel.setFont(titleLabel.getFont().deriveFont(52f));
        titleLabel.setBounds(150, 30, 300, 80); // 设置位置和大小
        contentPane.add(titleLabel);

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
        buttonPanel.setBounds(225, 150, 300, 200); // 设置位置和大小
        contentPane.add(buttonPanel);

        // 创建四个按钮
        Dimension buttonSize = new Dimension(150, 40);
        JButton startGameButton = new JButton("开始游戏");
        JButton historyButton = new JButton("历史记录");
        JButton selectPlaneButton = new JButton("选择飞机");
        JButton newPlayerButton = new JButton("新手提示");

        // 设置按钮的首选大小、最大大小和最小大小
        startGameButton.setPreferredSize(buttonSize);
        startGameButton.setMaximumSize(buttonSize);
        startGameButton.setMinimumSize(buttonSize);

        historyButton.setPreferredSize(buttonSize);
        historyButton.setMaximumSize(buttonSize);
        historyButton.setMinimumSize(buttonSize);

        selectPlaneButton.setPreferredSize(buttonSize);
        selectPlaneButton.setMaximumSize(buttonSize);
        selectPlaneButton.setMinimumSize(buttonSize);

        newPlayerButton.setPreferredSize(buttonSize);
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
                ImageIcon backgroundImage = new ImageIcon("background.jpg");
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
            new ReadyFrame("游客10086");
        });
    }
}