import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainIo {
        public static void reginOrRegistration() {
            JFrame frame = new JFrame("欢迎使用我们的系统");
            frame.setSize(350, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel panel = new JPanel();
            frame.add(panel);
            placeComponents(panel);
            frame.setVisible(true);
        }

        private static void placeComponents(JPanel panel) {
            //排版
            panel.setLayout(null);
            JLabel userLabel = new JLabel("User:");
            userLabel.setBounds(10,20,80,25);
            panel.add(userLabel);
            JTextField userText = new JTextField(20);
            userText.setBounds(100,20,165,25);
            panel.add(userText);
            JLabel passwordLabel = new JLabel("Password:");
            passwordLabel.setBounds(10,50,80,25);
            panel.add(passwordLabel);
            JPasswordField passwordText = new JPasswordField(20);
            passwordText.setBounds(100,50,165,25);
            panel.add(passwordText);
            //登录按钮
            JButton loginButton = new JButton("登录");
            loginButton.setBounds(10, 80, 80, 25);
            loginButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String user = userText.getText();
                    String password = passwordText.getText();
                    User u= User.login(user, password);
                    if (u.getId() ==0) {
                        JOptionPane.showMessageDialog(null, "你的密码或账号有误");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, u.getName()+" "+u.getPassword());
                    }
                }
            });
            panel.add(loginButton);
            JButton registButton = new JButton("注册");
            registButton.setBounds(100, 80, 80, 25);
            registButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    regist();
                }
            });
            panel.add(registButton);

        }
    public static void regist() {
        JFrame registerFrame = new JFrame("Registration");
        registerFrame.setSize(350, 250);
        registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel registerPanel = new JPanel();
        registerFrame.add(registerPanel);
        placeRegisterComponents(registerPanel);

        registerFrame.setVisible(true);
    }

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

}
