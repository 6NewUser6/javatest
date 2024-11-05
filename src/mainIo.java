import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainIo extends JFrame {

    private JTextField userText;
    private JPasswordField passwordText;
    private JPasswordField confirmPasswordText;

    public mainIo() {
        // 设置窗口标题
        setTitle("欢迎使用我们的系统");

        // 设置窗口大小
        setSize(350, 200);

        // 设置窗口关闭操作
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // 用户名标签和文本框
        JLabel userLabel = new JLabel("User:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        // 密码标签和密码框
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        // 登录按钮
        JButton loginButton = new JButton("登录");
        loginButton.setBounds(10, 80, 80, 25);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = userText.getText();
                String password = new String(passwordText.getPassword());
                User u = User.login(user, password);
                if (u.getId() == 0) {
                    JOptionPane.showMessageDialog(null, "你的密码或账号有误");
                } else {
                    // 进入游戏
                    ((JFrame) panel.getTopLevelAncestor()).dispose();
                    SwingUtilities.invokeLater(() -> {
                        ReadyFrame.audioPlayer.playLoop();
                        ReadyFrame frame = new ReadyFrame(u);
                        frame.setVisible(true);
                    });
                }
            }
        });
        panel.add(loginButton);

        // 注册按钮
        JButton registButton = new JButton("注册");
        registButton.setBounds(100, 80, 80, 25);
        registButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                regist();
            }
        });
        panel.add(registButton);

        // 将面板添加到窗口
        add(panel);

        // 设置窗口在屏幕中间显示
        setLocationRelativeTo(null);

        // 设置窗口可见
        setVisible(true);
    }

    // 登录页面
    public static void regist() {
        JFrame registerFrame = new JFrame("Registration");
        registerFrame.setSize(350, 250);
        registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel registerPanel = new JPanel();
        registerFrame.add(registerPanel);
        placeRegisterComponents(registerPanel);

        // 设置注册窗口在屏幕中间显示
        registerFrame.setLocationRelativeTo(null);

        registerFrame.setVisible(true);
    }

    // 注册页面
    private static void placeRegisterComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("用户名");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("请输入你的密码");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        JLabel confirmPasswordLabel = new JLabel("请再次输入你的密码");
        confirmPasswordLabel.setBounds(10, 80, 120, 25);
        panel.add(confirmPasswordLabel);

        JPasswordField confirmPasswordText = new JPasswordField(20);
        confirmPasswordText.setBounds(120, 80, 145, 25);
        panel.add(confirmPasswordText);

        JButton registButton = new JButton("注册");
        registButton.setBounds(10, 110, 80, 25);
        registButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = userText.getText();
                String password = new String(passwordText.getPassword());
                String confirmPassword = new String(confirmPasswordText.getPassword());

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "两次输入的密码不一致");
                } else {
                    if (User.registration(user, password)) {
                        JOptionPane.showMessageDialog(null, "注册成功，你现在可以登录了");
                        ((JFrame) panel.getTopLevelAncestor()).dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "注册失败，可能你的账号和他人重复了");
                    }
                }
            }
        });
        panel.add(registButton);
    }

    public static void main(String[] args) {
        // 创建并显示主页面
        new mainIo();
    }
}