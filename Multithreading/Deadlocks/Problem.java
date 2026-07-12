
class BankAccount {

    private final String name;
    private int balance;

    public BankAccount(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return this.name;
    }

    /*
     * synchronized locks the current BankAccount object.
     *
     * Only one thread can deposit into this account at a time.
     */
    public synchronized void deposit(int deposit_amount) {
        balance += deposit_amount;
    }

    /*
     * synchronized locks the current BankAccount object.
     *
     * Only one thread can withdraw from this account at a time.
     */
    public synchronized void withdraw(int withdraw_amount) {
        balance -= withdraw_amount;
    }

    public int getBalance() {
        return this.balance;
    }
}

class TransferTask implements Runnable {

    private final BankAccount from;
    private final BankAccount to;
    private final int amount;

    public TransferTask(BankAccount from, BankAccount to, int amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    @Override
    public void run() {

        /*
         * Lock the source account first.
         *
         * Thread-1:
         *      locks Account A
         *
         * Thread-2:
         *      locks Account B
         *
         * At this point both threads own different locks.
         */
        synchronized (from) {

            System.out.println(Thread.currentThread().getName()
                    + " locked " + from.getName());

            /*
             * Sleep is added only to make the deadlock easier
             * to reproduce.
             *
             * While Thread-1 is sleeping after locking A,
             * Thread-2 gets enough time to lock B.
             */
            try {
                Thread.sleep(100);
            } catch (Exception ignored) {
            }

            /*
             * Now each thread tries to acquire the second lock.
             *
             * Thread-1:
             *      Already owns A
             *      Waiting for B
             *
             * Thread-2:
             *      Already owns B
             *      Waiting for A
             *
             * Neither thread can continue because each is waiting
             * for the other to release its lock.
             *
             * This is a CLASSIC DEADLOCK.
             */
            synchronized (to) {

                System.out.println(Thread.currentThread().getName()
                        + " locked " + to.getName());

                from.withdraw(amount);
                to.deposit(amount);
            }

            System.out.println("Transferred " + amount + " from "
                    + from.getName() + " to " + to.getName());
        }
    }
}

public class Problem {

    public static void main(String[] args) {

        BankAccount A = new BankAccount("A", 1000);
        BankAccount B = new BankAccount("B", 1000);

        /*
         * Thread-1 transfers money:
         *      A ---> B
         */
        Thread t1 = new Thread(new TransferTask(A, B, 100), "Thread-1");

        /*
         * Thread-2 transfers money:
         *      B ---> A
         */
        Thread t2 = new Thread(new TransferTask(B, A, 100), "Thread-2");

        t1.start();
        t2.start();

        try {

            /*
             * If a deadlock occurs,
             * both threads remain blocked forever.
             *
             * Therefore join() also waits forever,
             * and "Completed" is never printed.
             */
            t1.join();
            t2.join();

            System.out.println("Completed");

        } catch (Exception ignored) {
        }
    }
}

/*
 * ==========================================================
 *                DEADLOCK EXPLANATION
 * ==========================================================
 *
 * Initial State:
 *
 *      Account A        Account B
 *          |                |
 *       unlocked        unlocked
 *
 * ----------------------------------------------------------
 *
 * Step 1
 *
 * Thread-1 locks Account A
 *
 *      Thread-1 ---> A (LOCKED)
 *
 * Thread-2 locks Account B
 *
 *      Thread-2 ---> B (LOCKED)
 *
 * ----------------------------------------------------------
 *
 * Step 2
 *
 * Thread-1 now wants Account B
 *
 *      Thread-1
 *          A ✓
 *          B (waiting...)
 *
 * But Thread-2 already owns B.
 *
 * ----------------------------------------------------------
 *
 * Step 3
 *
 * Thread-2 now wants Account A
 *
 *      Thread-2
 *          B ✓
 *          A (waiting...)
 *
 * But Thread-1 already owns A.
 *
 * ----------------------------------------------------------
 *
 * Final Situation
 *
 * Thread-1 is waiting for Thread-2.
 *
 * Thread-2 is waiting for Thread-1.
 *
 * Neither thread can continue.
 *
 * Neither thread releases its lock.
 *
 * Program becomes permanently stuck.
 *
 * This is called DEADLOCK.
 *
 * ==========================================================
 * Four Necessary Conditions for Deadlock
 * ==========================================================
 *
 * 1. Mutual Exclusion
 *    Locks can only be owned by one thread.
 *
 * 2. Hold and Wait
 *    Each thread holds one lock while waiting for another.
 *
 * 3. No Preemption
 *    JVM cannot forcibly take a lock away.
 *
 * 4. Circular Wait
 *
 *      Thread-1
 *          ↓
 *      Account A
 *          ↓
 *      Account B
 *          ↑
 *      Thread-2
 *
 * A circular dependency exists, so neither thread can proceed.
 *
 * ==========================================================
 */
