
class PurchaseCounterSync {

    /*
     * Shared resource accessed by multiple threads.
     *
     * This variable does NOT need to be volatile because all updates
     * happen inside a synchronized method.
     *
     * synchronized already guarantees:
     * 1. Mutual Exclusion (only one thread can modify count at a time)
     * 2. Visibility (changes made by one thread become visible to others)
     */
    private int count = 0;

    /*
     * synchronized locks the current object (this).
     *
     * Think of it as:
     *
     * Thread-1 ---------> acquires object lock
     * Thread-2 ---------> waits outside
     *
     * Only after Thread-1 finishes and releases the lock
     * can Thread-2 enter this method.
     *
     * ------------------------------------------------------
     * Why is synchronization needed?
     *
     * count++ is NOT a single operation.
     * Internally it performs:
     *
     * 1. Read count
     * 2. Increment value
     * 3. Write updated value
     *
     * Without synchronized:
     *
     * count = 10
     *
     * Thread-1 reads 10
     * Thread-2 reads 10
     * Thread-1 writes 11
     * Thread-2 writes 11
     *
     * Expected : 12
     * Actual   : 11
     *
     * This is called a LOST UPDATE (Race Condition).
     *
     * ------------------------------------------------------
     * With synchronized:
     *
     * Thread-1 enters increment()
     * Thread-2 waits
     *
     * Thread-1:
     * Read -> Increment -> Write
     *
     * Thread-1 exits
     *
     * Thread-2 enters
     * Read -> Increment -> Write
     *
     * Every increment happens one after another,
     * so no updates are lost.
     *
     * synchronized therefore makes the entire
     * read-modify-write sequence atomic.
     *
     * ------------------------------------------------------
     * synchronized also provides memory visibility.
     *
     * When a thread exits this method,
     * all its changes are flushed to main memory.
     *
     * When another thread enters,
     * it reads the latest value from main memory.
     *
     * This behavior is defined by the Java Memory Model
     * using the "happens-before" relationship.
     */
    public synchronized void increment() {
        this.count++;
    }

    /*
     * Returns the current value.
     *
     * Although this method is not synchronized,
     * it is completely safe in this program because:
     *
     * 1. main() calls getCount() only AFTER
     *    both worker threads have finished.
     *
     * 2. join() establishes a happens-before relationship,
     *    ensuring the main thread sees all completed updates.
     *
     * If getCount() were called while worker threads were still
     * updating count, it should also be synchronized (or count
     * should be volatile/AtomicInteger depending on the design).
     */
    public int getCount() {
        return this.count;
    }
}

public class Sync {

    public static void main(String[] args) {

        PurchaseCounterSync purchaseCounterSync = new PurchaseCounterSync();

        /*
         * Both threads execute the same task.
         *
         * Each thread increments the shared counter
         * 10,000 times.
         */
        Runnable r = () -> {
            for (int i = 0; i < 10000; i++) {
                purchaseCounterSync.increment();
            }
        };

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);

        try {

            // Start both worker threads.
            // The JVM scheduler decides which thread executes first.
            t1.start();
            t2.start();

            /*
             * join() waits until the thread completes.
             *
             * Without join():
             * ----------------
             * main thread may print before both threads finish,
             * producing values like:
             *
             * Count is : 0
             * Count is : 4532
             * Count is : 17890
             *
             * With join():
             * ------------
             * main waits for both worker threads,
             * ensuring all increments are completed.
             */
            t1.join();
            t2.join();

            /*
             * Since:
             * 1. increment() is synchronized
             * 2. main waits using join()
             *
             * Every increment is accounted for.
             *
             * Final Output:
             *
             * Count is : 20000
             */
            System.out.println("Count is : " + purchaseCounterSync.getCount());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
