/*
 * Bridge solution:
 * `VideoPlayer` represents the platform side, and `VideoQuality` represents the
 * streaming-quality side. Because they are connected by composition instead of a
 * giant inheritance tree, either side can evolve without creating every possible
 * platform-quality subclass combination.
 */
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

    // The abstraction delegates the variable quality behavior to another hierarchy.
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
        // Platform-specific flow stays here, while quality-specific work is bridged out.
        videoQuality.load(title);
    }
}

class MobilePlatform extends VideoPlayer {

    public MobilePlatform(VideoQuality videoQuality) {
        super(videoQuality);
    }

    public void play(String title) {
        System.out.println("Mobile Platform : " + title);
        // Reusing the same quality objects avoids duplicating platform x quality classes.
        videoQuality.load(title);
    }
}

public class Main {

    public static void main(String[] args) {

        // These objects are assembled at runtime instead of being baked into separate
        // concrete subclasses like `WebHDPlayer`, `MobileHDPlayer`, and so on.
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
