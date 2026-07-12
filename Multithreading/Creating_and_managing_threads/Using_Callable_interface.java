
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

class ETACalculator implements Callable<Integer> {

    private int location;

    public ETACalculator(int location) {
        this.location = location;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("" + Thread.currentThread().getName() + " Calculating ETA " + this.location);
        new Thread().sleep(2000);
        return 200;
    }
}

public class Using_Callable_interface {

    public static void main(String[] args) {
        FutureTask etaRunnable = new FutureTask<>(new ETACalculator(0));
        Thread eta = new Thread(etaRunnable);
        eta.start();
        FutureTask etaRunnable1 = new FutureTask<>(new ETACalculator(0));
        Thread eta1 = new Thread(etaRunnable1);
        //Getting values
        try {
            Integer etaResult = (Integer) etaRunnable.get();
            Integer etaResult2 = (Integer) etaRunnable1.get();

        } catch (Exception e) {
        }
        eta1.start();
    }
}
