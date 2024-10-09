public class main {
    public static void main(String[] args) {
        AudioPlayer audioPlayer=new AudioPlayer(".\\resources\\backgroundMusic.wav");
        audioPlayer.playLoop();
        mainIo.reginOrRegistration();
        String a = System.getProperty("user.dir");
        System.out.println(a);
    }
}
