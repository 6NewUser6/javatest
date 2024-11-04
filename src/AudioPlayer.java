import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

    private Clip clip;
    private FloatControl gainControl;

    public AudioPlayer(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioStream);

            // 获取音量控制器
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playOnce() {
        if (clip != null) {
            new Thread(() -> {
                clip.setFramePosition(0); // 重置音频到开始位置
                clip.start();
                clip.drain(); // 阻塞直到当前音频数据播放完毕
            }).start();
        }
    }

    public void playLoop() {
        if (clip != null) {
            clip.setFramePosition(0); // 重置音频到开始位置
            clip.loop(Clip.LOOP_CONTINUOUSLY); // 循环播放
            clip.start();
        }
    }
    public void setVolume(float volume) {
        if (gainControl != null) {
            float min = gainControl.getMinimum();
            float max = gainControl.getMaximum();
            gainControl.setValue(min + (max - min) * volume);
        }
    }
}