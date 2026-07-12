
class OrderService {

    public static void main(String[] args) {
        System.out.println("Placing order");
        new Thread(new sendSMS()).start();
        System.out.println("Sending SMS");
        new Thread(new sendEmail()).start();
        System.out.println("Sending Email");
        callJoin();
        // new calculateETA().run();
    }

    public static void callJoin() {
        Thread t1 = new Thread(new sendSMS());
        Thread t2 = new Thread(new sendEmail());
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

class sendSMS implements Runnable {

    public void run() {
        try {
            Thread.sleep(3000);
            System.out.println("SMS Done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class sendEmail implements Runnable {

    public void run() {
        try {
            Thread.sleep(2000);
            System.out.println("Email Done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class Using_Runnable_interface {

    public static void main(String[] args) {
        OrderService.callJoin();
    }
}
