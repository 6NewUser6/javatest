import java.awt.Color;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Rays implements Obstacle {
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private Color color;
    private Timer colorTimer;

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Rays(int x1, int x2, int y1, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = Color.RED; // 默认颜色为红色
        initializeColorTimer();
    }

    private void initializeColorTimer() {
        colorTimer = new Timer(100, new ActionListener() {
            private int step = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (step <=5) {
                        color = new Color(255, 0, 0, 255); // 红色
                }
                else if(step<=7)
                    {color = new Color(255, 0, 0, 0);} // 透明

                else if(step<=11)
                {color = new Color(0, 0, 0, 255);
                }// 黑色
                else{
                        color = null;
                        colorTimer.stop();
                }
                step++;
            }
        });
        colorTimer.setInitialDelay(0);
        colorTimer.setRepeats(true);
        colorTimer.start();
    }

    @Override
    public boolean judgement(int x, int y) {
        // 计算线段的向量 (dx, dy)
        int dx = x2 - x1;
        int dy = y2 - y1;

        // 计算分母
        double denominator = Math.sqrt(dx * dx + dy * dy);

        // 计算分子
        double numerator = Math.abs(dx * (y1 - y) - dy * (x1 - x));

        // 计算点到线段的距离
        double distance = numerator / denominator;

        // 判断距离是否小于等于 20
        return distance <= 20;
    }

    public boolean isBlack() {
        return color != null && color.equals(new Color(0, 0, 0, 255));
    }

}