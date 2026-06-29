
interface VideoQuality {

    void load(String title);
}

class SDQuality implements VideoQuality {

    public void load(String title) {
        System.out.println("Streaming in SD : " + title);
    }
}

class HDQuality implements VideoQuality {

    public void load(String title) {
        System.out.println("Streaming in HD : " + title);
    }
}

class UltaHSQuality implements VideoQuality {

    public void load(String title) {
        System.out.println("Streaming in UltaHD : " + title);

    }
}

abstract class VideoPlayer {

    protected VideoQuality videoQuality;

    public VideoPlayer(VideoQuality videoQuality) {
        this.videoQuality = videoQuality;
    }

    public abstract void play(String title);
}

class WebPlayer extends VideoPlayer {

    public WebPlayer(VideoQuality videoQuality) {
        super(videoQuality);
    }

    public void play(String title) {
        System.out.println("Web Platform : " + title);
        videoQuality.load(title);
    }
}

class MobilePlatform extends VideoPlayer {

    public MobilePlatform(VideoQuality videoQuality) {
        super(videoQuality);
    }

    public void play(String title) {
        System.out.println("Mobile Platform : " + title);
        videoQuality.load(title);
    }
}

public class Main {

    public static void main(String[] args) {

        VideoPlayer webSD = new WebPlayer(new SDQuality());
        VideoPlayer webHD = new WebPlayer(new HDQuality());
        VideoPlayer webUltraHD = new WebPlayer(new UltaHSQuality());

        VideoPlayer mobileSD = new MobilePlatform(new SDQuality());
        VideoPlayer mobileHD = new MobilePlatform(new HDQuality());
        VideoPlayer mobileUltraHD = new MobilePlatform(new UltaHSQuality());

        webSD.play("Avengers");
        System.out.println();

        webHD.play("Interstellar");
        System.out.println();

        webUltraHD.play("Inception");
        System.out.println();

        mobileSD.play("Friends");
        System.out.println();

        mobileHD.play("Breaking Bad");
        System.out.println();

        mobileUltraHD.play("The Batman");
    }
}
