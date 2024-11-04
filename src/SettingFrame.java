import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingFrame extends JFrame {

    private JCheckBox musicCheckBox;
    private JCheckBox soundEffectCheckBox;
    private JButton saveButton;

    public SettingFrame() {
        // 设置窗口标题
        setTitle("设置");

        // 设置窗口大小
        setSize(300, 150);

        // 设置窗口关闭操作
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 设置边距

        // 创建音乐复选框
        musicCheckBox = new JCheckBox("音乐");
        musicCheckBox.setSelected(true); // 默认勾选
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(musicCheckBox, gbc);

        // 创建音效复选框
        soundEffectCheckBox = new JCheckBox("音效");
        soundEffectCheckBox.setSelected(true); // 默认勾选
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(soundEffectCheckBox, gbc);

        // 创建保存按钮
        saveButton = new JButton("保存");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSettings();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER; // 按钮居中
        panel.add(saveButton, gbc);

        // 将面板添加到窗口
        add(panel);

        // 设置窗口在屏幕中间显示
        setLocationRelativeTo(null);

        // 设置窗口可见
        setVisible(true);
    }

    private void saveSettings() {
        // 获取音乐和音效的设置状态
        boolean musicEnabled = musicCheckBox.isSelected();
        boolean soundEffectEnabled = soundEffectCheckBox.isSelected();

        // 这里可以添加保存设置的逻辑
        // 例如，将设置保存到文件或数据库中

        // 显示保存成功的消息
        JOptionPane.showMessageDialog(this, "音乐设置: " + musicEnabled + "\n音效设置: " + soundEffectEnabled, "保存成功", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        // 创建并显示设置窗口
        new SettingFrame();
    }
}