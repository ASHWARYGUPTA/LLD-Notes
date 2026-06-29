
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
        //For every email have to create new mail service
        EmailTemplate w1 = new WelcomeEmail();
        w1.setContent("New Content");
        w1.send("Ashwary");

        //For every person new object this is costly
    }
}
