
class PurchaseCounter {

    // Shared variable accessed by multiple threads.
    // Since it is not protected by synchronization or atomic operations,
    // multiple threads can modify it at the same time.
    private int count = 0;

    public void increment() {

        /*
         * This looks like a single statement (count++) but it is NOT atomic.
         *
         * Internally it performs three steps:
         * 1. Read current value of count
         * 2. Add 1
         * 3. Write updated value back
         *
         * If two threads execute these steps simultaneously,
         * one update can overwrite the other, causing a LOST UPDATE.
         *
         * Example:
         * count = 10
         *
         * Thread-1 reads 10
         * Thread-2 reads 10
         * Thread-1 writes 11
         * Thread-2 writes 11
         *
         * Expected value = 12
         * Actual value   = 11
         *
         * This is the classic Race Condition.
         */
        this.count++;
    }

    public int getCount() {
        return this.count;
    }
}

public class Race {

    public static void main(String[] args) {

        PurchaseCounter purchaseCounter = new PurchaseCounter();

        Runnable task = () -> {

            // Both threads execute this loop concurrently.
            // Each thread tries to increment the same shared variable
            // 100 times.
            for (int i = 0; i < 10000; i++) {
                purchaseCounter.increment();
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        // Starts both threads.
        // Their execution order is decided by the JVM scheduler
        // and is completely unpredictable.
        t1.start();
        t2.start();

        /*
         * PROBLEM 1:
         * The main thread does NOT wait for t1 and t2 to finish.
         *
         * Since there is no join(), this print statement may execute
         * before either thread has completed.
         *
         * Possible outputs:
         * Count : 0
         * Count : 57
         * Count : 143
         * Count : 200
         */
        try {
            t1.join();
            t2.join();
            System.out.println("Count : " + purchaseCounter.getCount());

        } catch (Exception e) {
        }

        /*
         * PROBLEM 2:
         * Even if we add:
         *
         * t1.join();
         * t2.join();
         *
         * the answer is STILL NOT guaranteed to be 200.
         *
         * Why?
         * Because count++ itself is not thread-safe.
         * Multiple threads may overwrite each other's updates,
         * resulting in values like:
         *
         * Count : 186
         * Count : 193
         * Count : 198
         * Count : 200 (sometimes)
         *
         * The exact value depends on thread scheduling.
         */

 /*
         * This program has TWO independent concurrency problems:
         *
         * 1. No join()  -> main thread may print before workers finish.
         *
         * 2. Race Condition -> count++ is not atomic, causing lost updates.
         *
         * Proper fixes:
         *  - Wait for threads using join().
         *  - Protect count using synchronized, AtomicInteger,
         *    or another thread-safe mechanism.
         */
    }
}
