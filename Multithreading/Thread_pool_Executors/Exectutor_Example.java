
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class EmailService {

    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void sendEmail(String recipent) {
        executor.execute(() -> {
            System.out.println(Thread.currentThread().getName() + " Sending Email to " + recipent);
            try {
                Thread.sleep((int) Math.random() * 100);
                System.out.println("Sent to " + recipent + "....");

            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    public static void main(String[] args) {
        for (int i = 0; i < 25; i++) {
            sendEmail("recipent " + i);
        }
        executor.shutdown();
    }
}

class FutureExample {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Future<Integer> future = executor.submit(() -> {
            Thread.sleep(4000);
            return 40;
        });

        System.out.println("Doing other tasks...");
        Integer result = future.get();
        System.out.println("Result : " + result);
        executor.shutdown();
    }

}

public class Exectutor_Example {

    public static void main(String[] args) {
        FutureExample.main(args);
    }
}
