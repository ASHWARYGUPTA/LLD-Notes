/*
 * Teaching note:
 * This version exposes the playlist's internal list directly to the client.
 * That makes iteration easy in the short term, but it leaks representation
 * details and lets outside code depend on the exact storage choice. Iterator
 * fixes that by returning a traversal object instead, with the tradeoff of one
 * more interface or class to maintain.
 */

import java.util.*;

class Video {

    String title;

    public Video(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}

class YoutubePlaylist {

    List<Video> playlist;

    public YoutubePlaylist() {
        playlist = new ArrayList<>();
    }

    public void addSong(String title) {
        playlist.add(new Video(title));
    }

    public List<Video> getPlaylist() {
        // Returning the raw list leaks internal structure to callers.
        // Once clients depend on List details, changing storage later becomes harder.
        return playlist;
    }
}

public class Problem {

    public static void main(String[] args) {
        YoutubePlaylist p1 = new YoutubePlaylist();
        p1.addSong("Tum hi ho");
        p1.addSong("Jaane de");

        // The client now traverses by reaching inside the collection object.
        // That is exactly the representation leak the Iterator pattern avoids.
        //Everything is exposed to client
        //Any internal details should be hidded
        List<Video> playlist = p1.getPlaylist();
        for (Video v : playlist) {
            System.out.println(v.getTitle());
        }
    }
}
