/*
 * Teaching note:
 * This file shows the anti-pattern where the publisher manually performs every
 * notification. That feels simple at first, but the channel becomes responsible
 * for every subscriber, every delivery mechanism, and every future change in
 * notification policy. Observer fixes that by pushing notification behavior out
 * to subscriber objects, with the tradeoff of managing a subscriber list.
 */

class YoutubeChannel {

    public void uploadNewVideo(String videoTitle) {
        System.out.println("Uploading: " + videoTitle);

        // The channel now knows concrete recipients and concrete channels.
        // Each extra subscriber or notification mode means editing this method again.
        //Manually Not scalable
        System.out.println("Sending email to user1");
        System.out.println("Sending email to user2 via phone");

    }

}

public class Problem {

    public static void main(String[] args) {
        // There is no subscriber abstraction here, so even a tiny policy change
        // like "mute some users" would force edits inside YoutubeChannel itself.
        // Kept intentionally sparse: the teaching value is in showing that the
        // upload method itself is overloaded with fan-out responsibilities.
    }
}
