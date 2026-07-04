/*
 * Teaching note:
 * This example is small, but it demonstrates the idea behind Liskov
 * Substitution: code using the base type should still work when a subtype is
 * provided. `TextNotifications` keeps the same general contract of "a thing
 * that can send a notification," so substitution stays safe. The tradeoff is
 * that inheritance only works when subclasses truly honor parent expectations.
 */

class Notifications {

    public void sendNotification() {
        System.out.println("Send Email");
    }
}

class TextNotifications extends Notifications {

    @Override
    public void sendNotification() {
        // The subtype changes the implementation detail while preserving the
        // behavior shape expected by callers of Notifications.
        System.out.println("Send Text");
    }
}

public class Main {

    public static void main(String[] args) {
        Notifications n0 = new Notifications();
        Notifications n1 = new TextNotifications(); //-> Liskov Substitution No error
    }
}
