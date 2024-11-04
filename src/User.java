import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {
    private String username;
    private final String password;
    private final int id;
    public int[] planes = new int[16]; // 记录飞机是否被购买的列表
    private int gold;
    private String plane;

    public User() {
        this("", "", 0, 0, "plane1", new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
    }

    public User(String username, String password, int uid, int gold, String plane, int[] planes) {
        Arrays.fill(this.planes, 0);
        this.planes = planes;
        this.username = username;
        this.password = password;
        this.id = uid;
        this.gold = gold;
        this.plane = plane;
    }

    public void setPlane(String plane) {
        this.plane = plane;
    }

    public String getName() {
        return username;
    }

    public void setName(String name) {
        this.username = name;
    }
    public int getId() {
        return id;
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
                if (fields.length >= 6) {
                    String username = fields[0];
                    String password = fields[1];
                    int id = Integer.parseInt(fields[2]);
                    int gold = Integer.parseInt(fields[3]);
                    String plane = "./resources/" + fields[4] + ".png";
                    int[] planes = new int[16];
                    for (int i = 0; i < 16; i++) {
                        planes[i] = Integer.parseInt(fields[5].charAt(i)+"");
                    }
                    if (username.equals(a) && password.equals(encrypt(b))) {
                        return new User(username, password, id, gold, plane, planes);
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
//                System.out.println("用户名重复了，请重新写一个用户名");
                return false;
            }

            if (!true_password(b)) {
//                System.out.println("密码里要同时有大小写字母，数字，且长度在12-18之间，不包含特殊字符");
                return false;
            }

            b = encrypt(b);
            int id = findid(filename);

            try (FileWriter writer = new FileWriter(file, true)) {
                writer.write(a + "," + b + "," + id +",0,plane1,1000000000000000" + "\n");
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

    public void gold() {
        gold += 10;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public String getPlane() {
        return plane;
    }

    public boolean hasPurchased(int planeIndex) {
        return planes[planeIndex] == 1;
    }

    public void purchasePlane(int planeIndex) {
        planes[planeIndex] = 1;
    }
    public void save() {
        String filename = "users.csv";
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : lines) {
                String[] fields = parseCSVLine(line).toArray(new String[0]);
                if (fields.length >= 3 && fields[0].equals(username) && fields[1].equals(password) && Integer.parseInt(fields[2]) == id) {
                    // 更新当前用户的数据
                    String planeIndex = plane.substring(plane.lastIndexOf('/') + 1, plane.lastIndexOf('.'));
                    String planesString = Arrays.toString(planes).replaceAll("\\[|\\]|,|\\s", "");
                    writer.write(username + "," + password + "," + id + "," + gold + "," + planeIndex + "," + planesString + "\n");
                } else {
                    writer.write(line + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}