import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class shoppingFrame extends JFrame {
    public shoppingFrame(User user) {

        // 设置窗口标题
        setTitle("一起打飞机");

        // 设置窗口大小
        setSize(600, 600);

        // 设置窗口关闭时的默认操作
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 禁止改动窗口大小
        setResizable(false);
        // 将窗口居中显示
        setLocationRelativeTo(null);

        // 禁用布局管理器
        setLayout(null);

        // 定义图像和按钮的初始位置
        int x = 50;
        int y = 50;
        int imageSize = 100; // 图像显示大小
        int gap = 20; // 图像和按钮之间的间隔
        int labelWidth = 70; // 调整后的勾选框宽度
        int labelHeight = 20; // 调整后的勾选框高度

        int i = 1;
        // 添加三排四列的图像和按钮
        JLabel goldLabel = new JLabel("金币: " + user.getGold());
        goldLabel.setBounds(500, 30, 80, 30); // 设置按钮位置和大小
        goldLabel.setFont(new Font("宋体", Font.PLAIN, 16)); // 设置字体
        add(goldLabel);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                // 创建 ImageIcon 并缩放图像
                String imagePath = "./resources/plane" + (i) + ".png";
                i++;
                ImageIcon originalIcon = new ImageIcon(imagePath);
                Image scaledImage = originalIcon.getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);

                // 创建 JLabel 并设置缩放后的图像
                JLabel imageLabel = new JLabel(scaledIcon);
                imageLabel.setBounds(x + col * (imageSize + gap), y + row * (imageSize + gap), imageSize, imageSize);
                add(imageLabel);

                // 创建对应的 JLabel 用于显示购买状态
                JLabel buyLabel = new JLabel();
                buyLabel.setBounds(x + col * (imageSize + gap) + (imageSize - labelWidth) / 2, y + row * (imageSize + gap) + imageSize + 10, labelWidth, labelHeight);
                int planeIndex = (row * 4) + col;
                if (user.hasPurchased(planeIndex)) {
                    buyLabel.setIcon(createScaledIcon("./resources/get.png", labelWidth, labelHeight));
                } else {
                    buyLabel.setIcon(createScaledIcon("./resources/buy.png", labelWidth, labelHeight));
                    buyLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (user.getGold() >= 25) { // 假设购买飞机需要25个金币
                                user.purchasePlane(planeIndex);
                                user.setGold(user.getGold() - 25);
                                buyLabel.setIcon(createScaledIcon("./resources/get.png", labelWidth, labelHeight));
                                goldLabel.setText("金币: " + user.getGold()); // 更新金币显示
                                System.out.println("购买飞机: " + imagePath);
                            } else {
                                JOptionPane.showMessageDialog(shoppingFrame.this, "金币不足，无法购买飞机", "金币不足", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    });
                }
                add(buyLabel);
            }
        }

        // 添加 "保存" 按钮
        JButton saveButton = new JButton("退出");
        saveButton.setBounds(400, 500, 80, 30); // 设置按钮位置和大小
        saveButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                ReadyFrame readyFrame = new ReadyFrame(user);
                readyFrame.setVisible(true);
            });
        });
        add(saveButton);

        // 使窗口可见
        setVisible(true);
    }

    private ImageIcon createScaledIcon(String path, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(path);
        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public static void main(String[] args) {
        // 创建一个虚拟的 User 对象
        User user = new User();
        // 创建并显示购物窗口
        new shoppingFrame(user);
    }
}