
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
        VideoDownloader v1 = new VideoDownloader();
        VideoDownloader v2 = new VideoDownloader();
        v1.downloadVideo("123");
        v2.downloadVideo("123"); // Same URL -> 
    }
}
