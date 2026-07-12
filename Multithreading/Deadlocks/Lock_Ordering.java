
import java.util.Arrays;

/*
 * ==========================================================
 *              LOCK ORDERING - DEADLOCK PREVENTION
 * ==========================================================
 *
 * Deadlock occurs when two threads acquire locks in different orders.
 *
 * Example:
 *
 * Thread-1:
 *      Lock A
 *      Waiting for B
 *
 * Thread-2:
 *      Lock B
 *      Waiting for A
 *
 * Both threads wait forever.
 *
 * ----------------------------------------------------------
 * Solution:
 *
 * Always acquire locks in a FIXED GLOBAL ORDER.
 *
 * For example:
 *
 * Smaller ID -----> Larger ID
 *
 * Regardless of the transfer direction.
 *
 * Thread-1 : A -> B
 * Thread-2 : B -> A
 *
 * Both will actually lock:
 *
 *      A first
 *      B second
 *
 * Since every thread follows the same order,
 * circular waiting is impossible, so deadlock cannot occur.
 * ==========================================================
 */
class LockOrderingSimple {

    static class Resource {

        int id;
        int value;

        public Resource(int id, int value) {
            this.id = id;
            this.value = value;
        }
    }

    public static void main(String[] args) {

        Resource r1 = new Resource(1, 100);
        Resource r2 = new Resource(2, 200);

        /*
         * Thread-1 wants:
         *      Resource-1 -> Resource-2
         */
        Runnable task1 = () -> transfer(r1, r2, 10);

        /*
         * Thread-2 wants:
         *      Resource-2 -> Resource-1
         *
         * Normally this could cause a deadlock.
         *
         * But because transfer() always locks resources
         * in ascending ID order, both threads lock:
         *
         *      Resource-1
         *      Resource-2
         *
         * Therefore deadlock never happens.
         */
        Runnable task2 = () -> transfer(r2, r1, 20);

        Thread t1 = new Thread(task1, "Thread-1");
        Thread t2 = new Thread(task2, "Thread-2");

        t1.start();
        t2.start();
    }

    public static void transfer(Resource r1, Resource r2, int amount) {

        /*
         * Put both resources into an array.
         */
        Resource[] arr = new Resource[]{r1, r2};

        /*
         * Sort them according to their IDs.
         *
         * No matter which resources are passed,
         * arr[0] always contains the smaller ID.
         *
         * Example:
         *
         * transfer(r2, r1)
         *
         * Before sorting:
         *      [2,1]
         *
         * After sorting:
         *      [1,2]
         *
         * Therefore every thread locks
         * Resource-1 before Resource-2.
         */
        Arrays.sort(arr, (x, y) -> Integer.compare(x.id, y.id));

        /*
         * Acquire the first lock.
         */
        synchronized (arr[0]) {

            System.out.println(Thread.currentThread().getName()
                    + " locked Resource " + arr[0].id);

            /*
             * Added only to make concurrent execution visible.
             * Even with this delay, deadlock cannot occur because
             * every thread follows the same locking order.
             */
            try {
                Thread.sleep(3000);
            } catch (Exception ignored) {
            }

            /*
             * Acquire the second lock.
             */
            synchronized (arr[1]) {

                System.out.println(Thread.currentThread().getName()
                        + " locked Resource " + arr[1].id);

                System.out.println(Thread.currentThread().getName()
                        + " transferred " + amount);
            }
        }
    }
}

/*
 * ==========================================================
 *      EXAMPLE THAT CAN DEADLOCK (NO LOCK ORDERING)
 * ==========================================================
 */
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
     * Locks the current account while depositing.
     */
    public synchronized void deposit(int deposit_amount) {
        balance += deposit_amount;
    }

    /*
     * Locks the current account while withdrawing.
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
         * First lock the source account.
         */
        synchronized (from) {

            System.out.println(Thread.currentThread().getName()
                    + " locked " + from.getName());

            try {
                Thread.sleep(100);
            } catch (Exception ignored) {
            }

            /*
             * Now try to lock the destination account.
             *
             * This is dangerous because another thread
             * may already hold this lock.
             */
            synchronized (to) {

                System.out.println(Thread.currentThread().getName()
                        + " locked " + to.getName());

                from.withdraw(amount);
                to.deposit(amount);
            }

            System.out.println("Transferred "
                    + amount
                    + " from "
                    + from.getName()
                    + " to "
                    + to.getName());
        }
    }
}

/*
 * ==========================================================
 *               DEADLOCK DEMONSTRATION
 * ==========================================================
 */
public class Lock_Ordering {

    public static void main(String[] args) throws InterruptedException {

        BankAccount A = new BankAccount("Account-A", 1000);
        BankAccount B = new BankAccount("Account-B", 1000);

        /*
         * Thread-1
         *
         * Lock order:
         *
         *      A
         *      ↓
         *      B
         */
        Thread t1 = new Thread(
                new TransferTask(A, B, 100),
                "Thread-1"
        );

        /*
         * Thread-2
         *
         * Lock order:
         *
         *      B
         *      ↓
         *      A
         *
         * Opposite order.
         *
         * This can cause deadlock.
         */
        Thread t2 = new Thread(
                new TransferTask(B, A, 100),
                "Thread-2"
        );

        t1.start();
        t2.start();

        /*
         * If deadlock occurs,
         * these joins never return because both
         * threads remain blocked forever.
         */
        t1.join();
        t2.join();

        System.out.println("Completed");
    }
}

/*
 * ==========================================================
 * WHY LOCK ORDERING WORKS
 * ==========================================================
 *
 * Without Lock Ordering
 * ---------------------
 *
 * Thread-1
 *      Lock A
 *      Wait B
 *
 * Thread-2
 *      Lock B
 *      Wait A
 *
 * Circular wait exists.
 *
 *                A
 *              ↑   ↓
 *          T2       T1
 *              ↓   ↑
 *                B
 *
 * Deadlock.
 *
 * ----------------------------------------------------------
 *
 * With Lock Ordering
 * ------------------
 *
 * Every thread always locks:
 *
 *      Smaller ID
 *          ↓
 *      Larger ID
 *
 * Example:
 *
 * Thread-1 wants A → B
 * Thread-2 wants B → A
 *
 * Both actually execute:
 *
 *      Lock A
 *      Lock B
 *
 * Thread-2 simply waits until Thread-1 releases A.
 *
 * There is NO circular wait.
 *
 * Therefore deadlock cannot happen.
 *
 * ==========================================================
 *
 * Lock Ordering breaks one of the four necessary conditions
 * of deadlock:
 *
 *      Circular Wait
 *
 * Once circular wait is eliminated,
 * deadlock becomes impossible.
 *
 * ==========================================================
 */
