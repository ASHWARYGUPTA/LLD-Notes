/*
 * Problem version:
 * This class mixes the core download responsibility with cross-cutting concerns
 * like caching, filtering, or access checks. Once those features are added here,
 * the downloader becomes harder to extend and every caller pays the network cost
 * again unless this class itself keeps growing in unrelated directions.
 */
class VideoDownloader {

    public String downloadVideo(String videoURL) {
        //Caching should be there but adding here will break SRP
        //For Filtering also will break SRP
        //Access
        System.out.println("Downloading video from URL " + videoURL);
        return "Content : " + videoURL;
    }
}

public class Problem {

    public static void main(String[] args) {
        // These are two independent downloader objects, so repeated requests for the
        // same URL still trigger duplicate work instead of being intercepted once.
        VideoDownloader v1 = new VideoDownloader();
        VideoDownloader v2 = new VideoDownloader();
        v1.downloadVideo("123");
        v2.downloadVideo("123"); // Same URL -> 
    }
}
