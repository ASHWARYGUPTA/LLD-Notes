/*
 * Teaching note:
 * This file demonstrates Template Method by locking down the shared send
 * workflow while letting subclasses customize only the variable steps. That
 * removes duplication and preserves process order. The tradeoff is that
 * subclasses must fit the parent algorithm instead of freely rearranging steps.
 */

abstract class Notification {

    // The algorithm is fixed here so every notification type follows the same
    // lifecycle: validate, transform, send, and record side effects in order.
    // Template Method (final so subclasses can't change the algorithm)
    public final void send(String to, String message) {

        rateLimit(to);
        validateRecipient(to);

        String formatted = message.trim();

        log(to, formatted);

        String composedMessage = composeMessage(formatted);

        sendNotification(to, composedMessage);

        analytics(to);
    }

    // Common steps
    private void rateLimit(String to) {
        System.out.println("Rate limiting done for " + to);
    }

    private void log(String to, String message) {
        System.out.println("Logging before send \"" + message + "\" to " + to);
    }

    private void analytics(String to) {
        System.out.println("Analytics updated for: " + to);
    }

    // These hooks keep variation explicit. If a subclass needs to change the
    // overall algorithm order, Template Method may become too restrictive.
    // Only these hook points vary between notification channels, which keeps
    // shared workflow changes in one place instead of duplicating them per class.
    // Steps that vary
    protected abstract void validateRecipient(String to);

    protected abstract String composeMessage(String message);

    protected abstract void sendNotification(String to, String message);
}

// ================= Email =================
class EmailNotification extends Notification {

    @Override
    protected void validateRecipient(String to) {
        System.out.println("Validated Email Recipient");
    }

    @Override
    protected String composeMessage(String message) {
        return "<html><p>" + message + "</p></html>";
    }

    @Override
    protected void sendNotification(String to, String message) {
        System.out.println("Sending Email to " + to);
        System.out.println("Content: " + message);
    }
}

// ================= SMS =================
class SMSNotification extends Notification {

    @Override
    protected void validateRecipient(String to) {
        System.out.println("Validated Phone Recipient");
    }

    @Override
    protected String composeMessage(String message) {
        return "[" + message + "]";
    }

    @Override
    protected void sendNotification(String to, String message) {
        System.out.println("Sending SMS to " + to);
        System.out.println("Content: " + message);
    }
}

// ================= Client =================
public class Main {

    public static void main(String[] args) {

        Notification email = new EmailNotification();
        Notification sms = new SMSNotification();

        System.out.println("===== Email =====");
        email.send("abc@gmail.com", "  Welcome to our platform!  ");

        System.out.println();

        System.out.println("===== SMS =====");
        sms.send("9876543210", "  Your OTP is 123456  ");
    }
}
