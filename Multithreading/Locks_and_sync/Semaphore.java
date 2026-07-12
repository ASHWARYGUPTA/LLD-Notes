
import java.util.concurrent.Semaphore;

/*
 * ==========================================================
 *          SEMAPHORE EXAMPLE - OTT ACCOUNT LOGIN
 * ==========================================================
 *
 * Imagine a Netflix/TUF+ Premium account that allows
 * only 2 devices to be logged in simultaneously.
 *
 * Example:
 *
 * Maximum Devices = 2
 *
 * Rahul
 * Priya
 * Amit
 * Neha
 *
 * If Rahul and Priya are already logged in,
 * Amit cannot login until someone logs out.
 *
 * Semaphore is perfect for limiting access
 * to a fixed number of resources.
 *
 * In this example:
 *
 * Permit = One Available Device Slot
 *
 * Initially:
 *
 * Available Permits = 2
 *
 * After Rahul logs in:
 *
 * Available Permits = 1
 *
 * After Priya logs in:
 *
 * Available Permits = 0
 *
 * Amit tries to login:
 *
 * No permits available
 * Login denied.
 *
 * ==========================================================
 */
class OTTPremiumAccount {

    /*
     * Semaphore maintains the number of available permits.
     *
     * Here:
     *
     * Permit = One Device Slot
     *
     * new Semaphore(2)
     *
     * means only TWO users can login simultaneously.
     */
    private final Semaphore deviceSlots;

    public OTTPremiumAccount(int maxDevices) {

        deviceSlots = new Semaphore(maxDevices);
    }

    /*
     * User tries to login.
     *
     * tryAcquire()
     *
     * If permit available
     *      -> takes one permit
     *      -> login succeeds
     *
     * Otherwise
     *      -> returns false immediately
     *      -> no waiting
     */
    public boolean login(String user) {

        System.out.println(user + " is trying to login...");

        if (deviceSlots.tryAcquire()) {

            System.out.println("✅ " + user + " logged in.");

            System.out.println("Available Device Slots : "
                    + deviceSlots.availablePermits());

            return true;

        } else {

            System.out.println("❌ " + user
                    + " login denied."
                    + " Maximum devices reached.");

            return false;
        }
    }

    /*
     * Logout releases one permit.
     *
     * release()
     *
     * increases available permits by one.
     *
     * Now another user can login.
     */
    public void logout(String user) {

        System.out.println("🔒 " + user + " logged out.");

        deviceSlots.release();

        System.out.println("Available Device Slots : "
                + deviceSlots.availablePermits());
    }
}

public class Semaphore {

    public static void main(String[] args) throws Exception {

        /*
         * Maximum two devices allowed.
         */
        OTTPremiumAccount account
                = new OTTPremiumAccount(2);

        Runnable user = () -> {

            String name = Thread.currentThread().getName();

            if (account.login(name)) {

                try {

                    /*
                     * Simulate watching a movie.
                     */
                    Thread.sleep(4000);

                } catch (Exception ignored) {
                }

                account.logout(name);
            }
        };

        Thread t1 = new Thread(user, "Rahul");
        Thread t2 = new Thread(user, "Priya");
        Thread t3 = new Thread(user, "Amit");
        Thread t4 = new Thread(user, "Neha");

        t1.start();
        t2.start();

        Thread.sleep(500);

        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
    }
}
