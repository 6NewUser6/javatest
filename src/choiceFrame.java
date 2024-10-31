import javax.swing.*;
import java.awt.*;

public class choiceFrame extends JFrame {
    private String plane; // 用于存储选择的飞机图像的 URL

    public choiceFrame(User user) {
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
        int radioButtonSize = 30; // 勾选框大小

        // 创建 ButtonGroup 以确保只能选择一个 JRadioButton
        ButtonGroup buttonGroup = new ButtonGroup();
        int i = 1;
        int rowCount = 0;
        int colCount = 0;

        // 添加三排四列的图像和按钮
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                // 创建 ImageIcon 并缩放图像
                String imagePath = "./resources/plane" + (i) + ".png";
                i++;

                // 检查用户是否购买了该飞机
                if (user.hasPurchased((row * 4) + col)) {
                    ImageIcon originalIcon = new ImageIcon(imagePath);
                    Image scaledImage = originalIcon.getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);

                    // 创建 JLabel 并设置缩放后的图像
                    JLabel imageLabel = new JLabel(scaledIcon);
                    imageLabel.setBounds(x + colCount * (imageSize + gap), y + rowCount * (imageSize + gap), imageSize, imageSize);
                    add(imageLabel);

                    // 创建对应的 JRadioButton
                    JRadioButton radioButton = new JRadioButton();
                    radioButton.setBounds(x + colCount * (imageSize + gap) + (imageSize - radioButtonSize) / 2, y + rowCount * (imageSize + gap) + imageSize + 10, radioButtonSize, radioButtonSize);
                    radioButton.setActionCommand(imagePath); // 设置 ActionCommand 为图像的 URL
                    buttonGroup.add(radioButton);
                    add(radioButton);
                    if (row == 0 && col == 0) {
                        radioButton.setSelected(true);
                        plane = imagePath;
                        user.setPlane(plane);
                    }
                    colCount++;
                    if (colCount == 4) {
                        colCount = 0;
                        rowCount++;
                    }
                }
            }
        }

        // 添加 "保存" 按钮
        JButton saveButton = new JButton("保存");
        saveButton.setBounds(400, 500, 80, 30); // 设置按钮位置和大小
        saveButton.addActionListener(e -> {
            boolean flag = false;
            // 获取选中的 JRadioButton 的 ActionCommand
            for (Component component : getContentPane().getComponents()) {
                if (component instanceof JRadioButton radioButton) {
                    if (radioButton.isSelected()) {
                        plane = radioButton.getActionCommand();
                        user.setPlane(plane);
                        dispose();
                        SwingUtilities.invokeLater(() -> {
                            ReadyFrame readyFrame = new ReadyFrame(user);
                            readyFrame.setVisible(true);
                        });
                        flag = true;
                        break;
                    }
                }
            }
            if (!flag) {
                System.out.println("没有选择飞机");
            }
        });
        add(saveButton);

        // 使窗口可见
        setVisible(true);
    }

    public static void main(String[] args) {
        // 创建一个虚拟的 User 对象
        User user = new User();
        // 创建并显示购物窗口
        new choiceFrame(user);
    }
}