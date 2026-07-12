
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * ==========================================================
 *         READ WRITE LOCK - STOCK PRICE SERVICE
 * ==========================================================
 *
 * Imagine a Stock Market application like:
 *
 *      NSE
 *      BSE
 *      Yahoo Finance
 *      Groww
 *      Zerodha
 *
 * Thousands of users are reading stock prices every second.
 *
 * Only a few updates happen when the market price changes.
 *
 * Example:
 *
 * Users Reading:
 *      User1 -> Reads TCS Price
 *      User2 -> Reads TCS Price
 *      User3 -> Reads TCS Price
 *      User4 -> Reads TCS Price
 *
 * Stock Exchange:
 *      Updates TCS Price occasionally.
 *
 * ----------------------------------------------------------
 * Problem with synchronized
 * ----------------------------------------------------------
 *
 * synchronized allows only ONE thread at a time.
 *
 * Even if 100 users only want to READ,
 * they must wait one after another.
 *
 * This wastes performance because multiple reads are safe.
 *
 * ----------------------------------------------------------
 * ReadWriteLock Solution
 * ----------------------------------------------------------
 *
 * Read Lock
 * ---------
 * ✔ Multiple readers can access simultaneously.
 *
 * Write Lock
 * ----------
 * ✔ Only one writer at a time.
 * ✔ No reader can read while writing.
 *
 * ==========================================================
 */
class Stock {

    private String company;
    private double price;

    /*
     * ReadWriteLock internally maintains:
     *
     * 1. Read Lock
     * 2. Write Lock
     *
     * Multiple threads may acquire the Read Lock.
     *
     * Only one thread may acquire the Write Lock.
     */
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public Stock(String company, double price) {
        this.company = company;
        this.price = price;
    }

    /*
     * READ OPERATION
     *
     * Multiple users can execute this method
     * simultaneously.
     *
     * Example:
     *
     * User-1 ----\
     * User-2 -----\
     * User-3 -------> Reading together
     * User-4 -----/
     *
     * No blocking occurs because reading does not modify data.
     */
    public double getPrice() {

        /*
         * Acquire Read Lock.
         */
        lock.readLock().lock();

        try {

            System.out.println(Thread.currentThread().getName()
                    + " is reading price...");

            Thread.sleep(1000);

            return price;

        } catch (Exception e) {
            return price;

        } finally {

            /*
             * Always release the read lock.
             */
            lock.readLock().unlock();
        }
    }

    /*
     * WRITE OPERATION
     *
     * Only ONE thread may execute this method.
     *
     * While writing:
     *
     * ✔ No other writer allowed.
     * ✔ No readers allowed.
     *
     * This prevents inconsistent reads.
     */
    public void updatePrice(double newPrice) {

        /*
         * Acquire Write Lock.
         */
        lock.writeLock().lock();

        try {

            System.out.println(Thread.currentThread().getName()
                    + " is updating stock price...");

            Thread.sleep(3000);

            this.price = newPrice;

            System.out.println("New Price = " + newPrice);

        } catch (Exception ignored) {

        } finally {

            /*
             * Release Write Lock.
             */
            lock.writeLock().unlock();
        }
    }
}

public class ReadWrite {

    public static void main(String[] args) {

        Stock tcs = new Stock("TCS", 4100);

        /*
         * Reader Threads
         *
         * These simulate users checking
         * stock prices.
         */
        Runnable reader = () -> {

            double p = tcs.getPrice();

            System.out.println(Thread.currentThread().getName()
                    + " read price = " + p);
        };

        /*
         * Writer Thread
         *
         * Simulates NSE updating the stock price.
         */
        Runnable writer = () -> {

            tcs.updatePrice(4250);
        };

        Thread r1 = new Thread(reader, "Reader-1");
        Thread r2 = new Thread(reader, "Reader-2");
        Thread r3 = new Thread(reader, "Reader-3");

        Thread w1 = new Thread(writer, "Writer");

        r1.start();
        r2.start();
        r3.start();

        /*
         * Start writer after readers.
         */
        try {
            Thread.sleep(500);
        } catch (Exception ignored) {
        }

        w1.start();
    }
}

/*
 * ==========================================================
 *             EXECUTION FLOW
 * ==========================================================
 *
 * Initially
 *
 * Stock Price = 4100
 *
 * ----------------------------------------------------------
 *
 * Reader-1 acquires READ LOCK
 *
 * Reader-2 also acquires READ LOCK
 *
 * Reader-3 also acquires READ LOCK
 *
 * All three execute simultaneously.
 *
 *
 *            READ LOCK
 *
 * Reader-1
 * Reader-2
 * Reader-3
 *
 * All are allowed together.
 *
 * ----------------------------------------------------------
 *
 * Writer arrives.
 *
 * Writer requests WRITE LOCK.
 *
 * But readers are still active.
 *
 * Writer waits.
 *
 * ==========================================================
 *
 * After all readers finish:
 *
 * Reader-1 releases lock
 * Reader-2 releases lock
 * Reader-3 releases lock
 *
 * Now Writer gets WRITE LOCK.
 *
 * ==========================================================
 *
 * While Writer is updating:
 *
 * Reader-X wants to read.
 *
 * Reader-X must WAIT.
 *
 * Why?
 *
 * Because reading while updating may produce
 * inconsistent data.
 *
 * ==========================================================
 *
 * After Writer finishes:
 *
 * Price becomes
 *
 *      4250
 *
 * Write Lock released.
 *
 * Readers are allowed again.
 *
 * ==========================================================
 *
 * Summary
 * ==========================================================
 *
 * READ LOCK
 *
 * ✔ Multiple readers allowed.
 *
 * Reader-1
 * Reader-2
 * Reader-3
 *
 * ==========================================================
 *
 * WRITE LOCK
 *
 * Only ONE writer allowed.
 *
 * Writer
 *
 * Readers blocked.
 * Other writers blocked.
 *
 * ==========================================================
 *
 * synchronized vs ReadWriteLock
 * ==========================================================
 *
 * synchronized
 *
 * Reader-1
 *    ↓
 * Reader-2
 *    ↓
 * Reader-3
 *    ↓
 * Writer
 *
 * One thread at a time.
 *
 * ==========================================================
 *
 * ReadWriteLock
 *
 * Reader-1
 * Reader-2
 * Reader-3
 *      │
 *      │
 * (Execute Together)
 *      │
 *      ▼
 *    Writer
 *
 * Much better performance when
 * reads are frequent and writes are rare.
 *
 * ==========================================================
 */
