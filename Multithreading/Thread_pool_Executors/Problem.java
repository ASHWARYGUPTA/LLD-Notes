
//At scale it fucks only 8 cores and 1000's of threads
class RideMatching {

    public void requestRide(String riderId) {
        Thread matchThread = new Thread(() -> {
            System.out.println("Matching rider");
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        matchThread.start();
    }
}

public class Problem {

    public static void main(String[] args) {

    }
}
