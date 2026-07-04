/*
 * Teaching note:
 * This file shows Iterator hiding the playlist's internal storage while still
 * letting clients traverse items one by one. That keeps traversal logic
 * separate from collection logic and makes storage changes safer. The tradeoff
 * is introducing another abstraction layer for iteration.
 */

/*
 * Teaching note:
 * Iterator hides playlist storage details behind a traversal object. Clients
 * can walk the collection without depending on its internal representation,
 * which keeps encapsulation stronger than exposing the raw list. The tradeoff
 * is one more abstraction layer for very small collections.
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

interface PlaylistIterator {

    boolean hasNext();

    Video next();
}

class YoutubePlaylistIterator implements PlaylistIterator {

    private List<Video> videos;
    private int position;

    public YoutubePlaylistIterator(List<Video> videos) {
        this.videos = videos;
        this.position = 0;
    }

    @Override
    public boolean hasNext() {
        return this.position < this.videos.size();
    }

    @Override
    public Video next() {
        // Advancing through a dedicated iterator means traversal state lives
        // here instead of leaking into client code.
        return videos.get(this.position++);
    }
}

interface Playlist {

    // Clients ask for an iterator instead of grabbing the raw list, which is
    // why traversal stays stable even if the collection implementation changes.
    YoutubePlaylistIterator createIterator();
}

class YoutubePlaylist implements Playlist {

    List<Video> playlist;

    public YoutubePlaylist() {
        playlist = new ArrayList<>();
    }

    public void addSong(String title) {
        playlist.add(new Video(title));
    }

    public YoutubePlaylistIterator createIterator() {
        // The playlist decides how iteration starts, so callers do not need to
        // know whether storage is a list, array, database page, or something else.
        return new YoutubePlaylistIterator(this.playlist);
    }

}

public class Main {

    public static void main(String[] args) {
        YoutubePlaylist p1 = new YoutubePlaylist();
        p1.addSong("Tum hi ho");
        p1.addSong("Aware");

        YoutubePlaylistIterator it = p1.createIterator();
        // The client only depends on the iteration protocol, not on how the
        // playlist stores songs internally.
        while (it.hasNext()) {
            System.out.println(it.next().getTitle());
        }

    }
}
