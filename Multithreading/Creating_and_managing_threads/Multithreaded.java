
class OrderService {

    public static void main(String[] args) {
        System.out.println("Placing order");
        new sendSMS().start();
        System.out.println("Sending SMS");
        new sendEmail().start();
        System.out.println("Sending Email");
        callJoin();
        // new calculateETA().run();
    }

    public static void callJoin() {
        Thread t1 = new sendEmail();
        Thread t2 = new sendSMS();
        System.err.println("Join Function Starting");
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
            System.out.println("Done Joining");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class sendSMS extends Thread {

    public void run() {
        try {
            Thread.sleep(3000);
            System.out.println("SMS Done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class sendEmail extends Thread {

    public void run() {
        try {
            Thread.sleep(2000);
            System.out.println("Email Done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class Multithreaded {

    public static void main(String[] args) {
        OrderService.main(args);
    }
}
