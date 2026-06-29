
interface EmailTemplate {

    EmailTemplate clone(); //Deep Clone recommend

    void setContent(String s);

    void send(String s);
}

class WelcomeEmail implements EmailTemplate {

    private String subject;
    private String content;

    public WelcomeEmail() {
        this.subject = "Welcome to TUF+";
        this.content = "Thanx for joining";
    }

    @Override
    public EmailTemplate clone() {
        try {
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
        EmailTemplate wlcmEmail2 = welcomeEmail.clone(); //Deep Copy

    }
}
