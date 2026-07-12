
import java.util.concurrent.atomic.AtomicInteger;

class InstagramLikesCounterAtomic {

    /*
     * AtomicInteger is a thread-safe integer implementation provided by
     * java.util.concurrent.atomic.
     *
     * "Atomic" means an operation is performed as a single,
     * indivisible unit. No other thread can observe the operation
     * in a partially completed state.
     *
     * AtomicInteger internally uses CPU-level atomic instructions
     * (such as Compare-And-Swap, CAS) instead of using synchronized locks.
     *
     * Advantages:
     * -----------
     * ✔ Thread-safe
     * ✔ Lock-free (doesn't block other threads)
     * ✔ Faster than synchronized for simple counters
     * ✔ Provides memory visibility like volatile
     *
     * AtomicInteger internally behaves as if the value were volatile,
     * so every thread always sees the latest value.
     */
    private AtomicInteger count = new AtomicInteger(0);

    /*
     * This method manually implements increment using CAS
     * (Compare-And-Set).
     *
     * CAS is the core idea behind AtomicInteger.
     *
     * Algorithm:
     *
     * 1. Read current value.
     * 2. Calculate new value.
     * 3. Try to replace old value with new value.
     * 4. If another thread modified the value meanwhile,
     *    retry until successful.
     *
     * This is completely lock-free.
     */
    public void incrementLikes() {

        int prev, next;

        do {

            /*
             * Read the current value atomically.
             *
             * Example:
             * count = 25
             *
             * prev = 25
             */
            prev = count.get();

            /*
             * Calculate the next value.
             */
            next = prev + 1;

            /*
             * compareAndSet(expectedValue, newValue)
             *
             * It checks:
             *
             * "Is count still equal to prev?"
             *
             * YES:
             *      Replace count with next
             *      Return true
             *
             * NO:
             *      Another thread already changed it.
             *      Return false.
             *
             * If false, the loop retries with the latest value.
             *
             * Example:
             *
             * Thread-1
             * prev = 10
             *
             * Thread-2 increments count to 11
             *
             * Thread-1 now executes:
             *
             * compareAndSet(10,11)
             *
             * Current value is 11, not 10.
             *
             * CAS fails.
             *
             * Thread-1 retries:
             *
             * prev = 11
             * next = 12
             *
             * compareAndSet(11,12)
             *
             * Success.
             */
        } while (!count.compareAndSet(prev, next));

        /*
         * The loop exits only when this thread successfully
         * updates the value.
         */
    }

    /*
     * Returns the latest value.
     *
     * AtomicInteger guarantees visibility,
     * so all threads observe the newest value.
     */
    public int getCount() {
        return this.count.get();
    }
}

public class Atomic {

    public static void main(String[] args) {

        InstagramLikesCounterAtomic counter
                = new InstagramLikesCounterAtomic();

        Runnable task = () -> {

            /*
             * Both threads increment the shared counter.
             */
            for (int i = 0; i < 10000; i++) {
                counter.incrementLikes();
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();

        try {

            /*
             * Wait until both threads finish.
             */
            t1.join();
            t2.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*
         * Output:
         * Likes = 20000
         *
         * Every increment is accounted for because
         * AtomicInteger guarantees atomic updates.
         */
        System.out.println("Likes = " + counter.getCount());
    }
}
