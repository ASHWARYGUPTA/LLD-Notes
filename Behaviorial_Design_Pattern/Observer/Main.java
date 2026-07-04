/*
 * Teaching note:
 * This version demonstrates why Observer is useful for one-to-many updates.
 * The channel only knows the subscriber abstraction, so new subscriber types
 * can join without changing publishing code. The tradeoff is that notification
 * timing and failure handling become important design decisions once delivery
 * moves beyond a tiny synchronous demo.
 */

import java.util.*;

interface Subscriber {

    void update(String title);
}

// Concrete Observer 1
class EmailSubscriber implements Subscriber {

    private String email;

    public EmailSubscriber(String email) {
        this.email = email;
    }

    @Override
    public void update(String title) {
        System.out.println("Email sent to " + email + " : New video -> " + title);
    }
}

// Concrete Observer 2
class MobileAppSubscriber implements Subscriber {

    private String username;

    public MobileAppSubscriber(String username) {
        this.username = username;
    }

    @Override
    public void update(String title) {
        System.out.println("In-app notification for " + username + " : New video -> " + title);
    }
}

// Subject
interface Channel {

    void subscribe(Subscriber subscriber);

    void unsubscribe(Subscriber subscriber);

    void notifySubscribers(String videoTitle);

    void uploadVideo(String title);
}

// Concrete Subject
class YoutubeChannel implements Channel {

    private List<Subscriber> subscribers;
    private String channelName;

    public YoutubeChannel(String channelName) {
        this.channelName = channelName;
        this.subscribers = new ArrayList<>();
    }

    @Override
    public void subscribe(Subscriber subscriber) {
        // The subject grows by composition rather than by new hard-coded send
        // calls, which is why adding subscribers does not change upload logic.
        subscribers.add(subscriber);
        System.out.println("New subscriber added.");
    }

    @Override
    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
        System.out.println("Subscriber removed.");
    }

    @Override
    public void notifySubscribers(String videoTitle) {
        // Broadcasting through the interface keeps the subject decoupled from
        // email, mobile, or any future notification mechanism.
        for (Subscriber subscriber : subscribers) {
            subscriber.update(videoTitle);
        }
    }

    @Override
    public void uploadVideo(String title) {
        // Uploading triggers a generic event instead of hard-coding each
        // receiver, which is the core reason this scales better than the
        // problem version.
        System.out.println("\n" + channelName + " uploaded: " + title);
        notifySubscribers(title);
    }
}

public class Main {

    public static void main(String[] args) {

        YoutubeChannel channel = new YoutubeChannel("CodeWithAsh");

        Subscriber s1 = new EmailSubscriber("alice@gmail.com");
        Subscriber s2 = new MobileAppSubscriber("bob123");
        Subscriber s3 = new EmailSubscriber("charlie@gmail.com");

        // Subscribe
        channel.subscribe(s1);
        channel.subscribe(s2);
        channel.subscribe(s3);

        // Upload first video
        channel.uploadVideo("Observer Pattern in Java");

        // Unsubscribe one user
        channel.unsubscribe(s2);

        // Upload another video
        channel.uploadVideo("Strategy Pattern Explained");
    }
}
