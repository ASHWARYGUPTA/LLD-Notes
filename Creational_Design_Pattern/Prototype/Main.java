/*
 * Teaching note:
 * Prototype helps when many objects begin with the same prepared state. It
 * scales because a configured template can be copied and lightly customized,
 * avoiding repeated setup work in every caller.
 */

// Prototype scales better when many objects start from the same baseline. One
// prepared template can be copied and then lightly customized, so repeated setup
// logic is reduced and clients avoid rebuilding identical defaults each time.

interface EmailTemplate {

    EmailTemplate clone(); //Deep Clone recommend

    void setContent(String s);

    void send(String s);
}

class WelcomeEmail implements EmailTemplate, Cloneable {

    private String subject;
    private String content;

    public WelcomeEmail() {
        this.subject = "Welcome to TUF+";
        this.content = "Thanx for joining";
    }

    @Override
    public EmailTemplate clone() {
        try {
            // This example is focused on the pattern idea; in production, Cloneable support must be wired correctly.
            // Cloning reuses the prepared template state instead of constructing it again from zero.
            return (WelcomeEmail) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone Failed", e);

        }
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

public class Main {

    public static void main(String[] args) {
        EmailTemplate welcomeEmail = new WelcomeEmail();
        // The second object is intended to start as a copy of the first and then diverge if needed.
        EmailTemplate wlcmEmail2 = welcomeEmail.clone(); //Deep Copy

    }
}
