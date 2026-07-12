
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/*
 * ===============================================================
 *           BOOKMYSHOW TICKET BOOKING USING REENTRANTLOCK
 * ===============================================================
 *
 * Why not synchronized?
 * ---------------------
 * Suppose two users try booking the SAME seat.
 *
 * synchronized:
 *      - Second thread waits indefinitely.
 *      - Cannot specify a timeout.
 *      - Cannot know whether lock was acquired.
 *
 * ReentrantLock provides:
 * -----------------------
 * 1. Explicit locking (lock()/unlock()).
 * 2. tryLock() -> Don't wait if lock unavailable.
 * 3. tryLock(timeout) -> Wait only for a limited time.
 * 4. Fair locking (optional).
 * 5. interruptible locking.
 *
 * BookMyShow uses similar concepts because:
 * - Thousands of users may try booking the same seat.
 * - Users shouldn't wait forever.
 * - If booking takes too long, show
 *      "Seat is currently being booked by another user."
 * ===============================================================
 */
class Seat {

    private final String seatNumber;
    private boolean booked = false;

    /*
     * Every seat has its own lock.
     *
     * Multiple seats can be booked simultaneously.
     *
     * Example:
     *
     * User-1 books A1
     * User-2 books B7
     *
     * Since they are different locks,
     * both bookings proceed in parallel.
     */
    private final ReentrantLock lock = new ReentrantLock();

    public Seat(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void book(String customer) {

        try {

            /*
             * Wait for at most 2 seconds.
             *
             * If another thread is already booking
             * this seat for more than 2 seconds,
             * booking fails immediately.
             *
             * This prevents users from waiting forever.
             */
            if (lock.tryLock(2, TimeUnit.SECONDS)) {

                try {

                    System.out.println(customer
                            + " acquired lock for seat "
                            + seatNumber);

                    /*
                     * Simulate payment gateway delay.
                     */
                    Thread.sleep(3000);

                    if (!booked) {

                        booked = true;

                        System.out.println(customer
                                + " successfully booked "
                                + seatNumber);

                    } else {

                        System.out.println(customer
                                + " booking failed."
                                + " Seat already booked.");
                    }

                } finally {

                    /*
                     * VERY IMPORTANT.
                     *
                     * Always release the lock inside finally.
                     *
                     * Even if payment throws an exception,
                     * the lock is released.
                     */
                    lock.unlock();

                    System.out.println(customer
                            + " released lock.");
                }

            } else {

                /*
                 * Could not acquire lock within 2 seconds.
                 *
                 * Instead of making the customer wait forever,
                 * BookMyShow would display:
                 *
                 * "Seat is currently being booked.
                 * Please try again."
                 */
                System.out.println(customer
                        + " couldn't acquire lock."
                        + " Timeout occurred.");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class UsingLocks {

    public static void main(String[] args) {

        Seat seat = new Seat("A10");

        /*
         * Customer-1 starts booking.
         */
        Thread customer1 = new Thread(() -> {
            seat.book("Rahul");
        });

        /*
         * Customer-2 starts shortly after.
         *
         * He wants the SAME seat.
         */
        Thread customer2 = new Thread(() -> {
            seat.book("Priya");
        });

        customer1.start();

        try {
            Thread.sleep(100);
        } catch (Exception ignored) {
        }

        customer2.start();
    }
}
