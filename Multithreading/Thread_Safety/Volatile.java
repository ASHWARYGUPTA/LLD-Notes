
class PurchaseCounterSync {

    /*
     * volatile guarantees VISIBILITY of changes across threads.
     *
     * Without volatile:
     * -----------------
     * Each thread is allowed to cache the value of 'count' in its
     * own CPU cache or working memory. A thread may continue reading
     * an old (stale) value even after another thread has updated it.
     *
     * With volatile:
     * --------------
     * - Every write to 'count' is immediately flushed to main memory.
     * - Every read of 'count' is performed from main memory.
     * - All threads always observe the latest value.
     *
     * In other words:
     *      volatile ==> Visibility Guarantee
     *
     * IMPORTANT:
     * volatile DOES NOT make operations atomic.
     *
     * Example:
     * count++;
     *
     * is actually
     *      1. Read count
     *      2. Add 1
     *      3. Write back
     *
     * Another thread can interleave between these three steps,
     * so volatile alone CANNOT prevent race conditions.
     *
     * Therefore, the following is STILL WRONG:
     *
     *      volatile int count;
     *      count++;
     *
     * because two threads may still overwrite each other's updates.
     */
    private volatile int count = 0;

    /*
     * synchronized provides MUTUAL EXCLUSION.
     *
     * Only ONE thread can execute this method at a time.
     *
     * If Thread-1 enters increment(),
     * Thread-2 must wait until Thread-1 exits.
     *
     * This makes count++ effectively atomic because no other
     * thread can perform the read-modify-write sequence simultaneously.
     *
     * synchronized also provides memory visibility.
     * Entering and leaving a synchronized block establishes
     * a "happens-before" relationship in the Java Memory Model.
     *
     * Because of this, volatile is actually REDUNDANT here.
     * The synchronized keyword already guarantees:
     *
     * 1. Mutual exclusion
     * 2. Visibility
     * 3. Memory consistency
     *
     * So removing 'volatile' from this program will still produce
     * the correct answer.
     */
    public synchronized void increment() {
        this.count++;
    }

    /*
     * Since 'count' is volatile, every call to getCount()
     * observes the latest value written by any thread.
     *
     * Even without volatile, synchronized increment()
     * already publishes the latest value correctly after the
     * worker threads finish and join().
     */
    public int getCount() {
        return this.count;
    }
}

public class Volatile {

    public static void main(String[] args) {

        PurchaseCounterSync purchaseCounterSync = new PurchaseCounterSync();

        Runnable r = () -> {

            // Each thread increments the shared counter
            // 10,000 times.
            for (int i = 0; i < 10000; i++) {
                purchaseCounterSync.increment();
            }
        };

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);

        try {

            // Start both worker threads.
            t1.start();
            t2.start();

            /*
             * join() blocks the main thread until the worker
             * thread finishes execution.
             *
             * After both joins complete, all 20,000 increments
             * have finished.
             */
            t1.join();
            t2.join();

            /*
             * Since increment() is synchronized,
             * every increment is protected.
             *
             * Expected output:
             *
             * Count is : 20000
             */
            System.out.println("Count is : " + purchaseCounterSync.getCount());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
