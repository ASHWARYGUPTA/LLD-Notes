
class Notifications {

    public void sendNotification() {
        System.out.println("Send Email");
    }
}

class TextNotifications extends Notifications {

    @Override
    public void sendNotification() {
        System.out.println("Send Text");
    }
}

public class Main {

    public static void main(String[] args) {
        Notifications n0 = new Notifications();
        Notifications n1 = new TextNotifications(); //-> Liskov Substitution No error
    }
}
