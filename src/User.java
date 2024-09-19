import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {
    private static String username;
    private static String password;
    private static int id;
    public User() {
        this("", "", 0);
    }

    public User(String username, String password, int uid) {
        User.username = username;
        User.password = password;
        id = uid;
    }
    public String getName() {
        return username;
    }
    public void setName(String name) {
        User.username = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        User.password = password;
    }
    public int getId() {
        return id;
    }
    public static User findUser(int id) {
        User user = new User();
        String filename = "users.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            // 跳过第一行
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                List<String> fields = parseCSVLine(line);
                if (fields.size() >= 4) {
                    int userId = Integer.parseInt(fields.get(3));
                    if (userId == id) {
                        String username = fields.get(0);
                        String password = fields.get(1);
                        return new User(username, password, id);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("无法打开文件: " + filename);
        }
        return user;
    }

    public static List<String> parseCSVLine(String line) {
        String[] items = line.split(",");
        return new ArrayList<>(Arrays.asList(items));
    }

    public static User login(String a, String b) {
        String filename = "users.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            // 跳过第一行
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] fields = parseCSVLine(line).toArray(new String[0]);
                if (fields.length >= 3) {
                    String username = fields[0];
                    String password = fields[1];
                    int id = Integer.parseInt(fields[2]);
                    if (username.equals(a) && password.equals(encrypt(b))) {
                        return new User(username, password, id);
                    }
                }
            }
        } catch (IOException e) {
            return new User();
        }
        return new User();
    }

    public static boolean registration(String a, String b) {
        String filename = "users.csv";
        try {
            File file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("0,\n");
                }
            }

            if (!repeat(filename, a)) {
                System.out.println("用户名重复了，请重新写一个用户名");
                return false;
            }

            if (!true_password(b)) {
                System.out.println("密码里要同时有大小写字母，数字，且长度在12-18之间，不包含特殊字符");
                return false;
            }

            b = encrypt(b);
            int id = findid(filename);

            try (FileWriter writer = new FileWriter(file, true)) {
                writer.write(a + "," + b + "," + id + "\n");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean true_password(String password1) {
        return true;
    }

    private static boolean repeat(String filename, String str) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            // 忽略第一行
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] fields = parseCSVLine(line).toArray(new String[0]);
                if (fields.length > 0 && fields[0].equals(str)) {
                    return false;
                }
            }
        } catch (IOException e) {
            System.err.println("无法打开文件: " + filename);
        }
        return true;
    }

    private static int findid(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            if (line != null) {
                String[] fields = parseCSVLine(line).toArray(new String[0]);
                if (fields.length > 0) {
                    int id = Integer.parseInt(fields[0]);
                    id += 1;

                    try (FileWriter writer = new FileWriter(filename)) {
                        writer.write(id + "," + line.substring(line.indexOf(',') + 1) + "\n");
                        String nextLine;
                        while ((nextLine = reader.readLine()) != null) {
                            writer.write(nextLine + "\n");
                        }
                    }
                    return id;
                }
            }
        } catch (IOException e) {
            System.err.println("无法打开文件: " + filename);
        }
        return -1;
    }

    private static String encrypt(String input) {
        return input;
    }
}
