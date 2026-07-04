/*
 * Teaching note:
 * Reconstructing nearly identical template objects over and over wastes setup
 * effort as usage grows. This problem version fails to scale because the caller
 * repeats the same initialization work for every recipient.
 */

// This problem version rebuilds the same template object from scratch for each
// use. That fails to scale when setup becomes expensive because repeated object
// creation duplicates default configuration work for every similar email.

interface EmailTemplate extends Cloneable {

    void setContent(String content);

    void send(String to);

}

class WelcomeEmail implements EmailTemplate {

    private String subject;
    private String content;

    public WelcomeEmail() {
        this.subject = "Welcome to TUF+";
        this.content = "Thanx for joining";
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void send(String to) {
        System.out.println("Sended to " + to);
    }
}

public class Problem {

    public static void main(String[] args) {
        // A new template instance is created even though most of its state matches the last one.
        //For every email have to create new mail service
        EmailTemplate w1 = new WelcomeEmail();
        w1.setContent("New Content");
        w1.send("Ashwary");

        //For every person new object this is costly
    }
}
