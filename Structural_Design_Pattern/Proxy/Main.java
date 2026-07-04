/*
 * Proxy solution:
 * The client still talks to the same `VideoDownloader` interface, but the proxy
 * stands in front of the real downloader to add caching without changing the
 * caller or bloating the real download class with extra policy code.
 */
import java.util.HashMap;
import java.util.Map;

interface VideoDownloader {

    String downloadVideo(String videoURL);
}

// Real Object
class RealVideoDownloader implements VideoDownloader {

    @Override
    public String downloadVideo(String videoURL) {
        System.out.println("Downloading video from URL: " + videoURL);
        return "Content: " + videoURL;
    }
}

// Proxy Object
class CachedVideoDownloader implements VideoDownloader {

    // The proxy keeps the real worker hidden behind the same interface.
    private final VideoDownloader realDownloader;
    // Extra proxy-only state lives here instead of polluting the real object.
    private final Map<String, String> cache;

    public CachedVideoDownloader() {
        this.realDownloader = new RealVideoDownloader();
        this.cache = new HashMap<>();
    }

    @Override
    public String downloadVideo(String videoURL) {

        // The proxy can short-circuit repeated calls before the expensive object runs.
        if (cache.containsKey(videoURL)) {
            System.out.println("Returning cached video for: " + videoURL);
            return cache.get(videoURL);
        }

        String content = realDownloader.downloadVideo(videoURL);
        cache.put(videoURL, content);

        return content;
    }
}

public class Main {

    public static void main(String[] args) {

        VideoDownloader downloader = new CachedVideoDownloader();

        System.out.println(downloader.downloadVideo("youtube.com/video1"));
        System.out.println();

        // Served from cache
        System.out.println(downloader.downloadVideo("youtube.com/video1"));
        System.out.println();

        // New download
        System.out.println(downloader.downloadVideo("youtube.com/video2"));
        System.out.println();

        // Served from cache
        System.out.println(downloader.downloadVideo("youtube.com/video2"));
    }
}
