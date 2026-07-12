// Producer Consumer Problem using wait() and notify()
// Example: Coffee Machine
//
// Producer  -> CoffeeMachine.makeCoffee()
// Consumer  -> CoffeeMachine.drinkCoffee()
//
// Only ONE cup of coffee can exist at a time.
//
// Producer should wait if coffee is already prepared.
// Consumer should wait if coffee is not ready.

class CoffeeMachine {

    // false -> No coffee available
    // true  -> Coffee is ready
    private boolean isCoffeeReady = false;

    // ---------------------- PRODUCER ----------------------
    // Coffee machine prepares coffee
    public synchronized void makeCoffee() throws InterruptedException {

        /*
         * Why while and not if?
         *
         * 1. Spurious Wakeup
         *    Java threads can wake up from wait()
         *    without notify() being called.
         *
         * 2. Multiple Producers
         *    Another producer might already prepare coffee
         *    before this producer gets CPU again.
         *
         * Therefore always recheck the condition.
         */
        while (isCoffeeReady) {

            System.out.println(Thread.currentThread().getName()
                    + " : Coffee already exists. Waiting...");

            // Releases monitor(lock)
            // Goes into WAITING state
            wait();

            // When awakened, lock is reacquired
            // Then while condition is checked again.
        }

        // Critical Section
        System.out.println(Thread.currentThread().getName()
                + " : Preparing coffee...");

        // Simulate time required
        Thread.sleep(1000);

        isCoffeeReady = true;

        System.out.println(Thread.currentThread().getName()
                + " : Coffee is Ready ☕");

        /*
         * Wake one waiting thread.
         *
         * Consumer will wake up,
         * but cannot continue until this synchronized
         * method exits and releases the lock.
         */
        notifyAll();
    }

    // ---------------------- CONSUMER ----------------------
    // Customer drinks coffee
    public synchronized void drinkCoffee() throws InterruptedException {

        /*
         * If coffee isn't ready,
         * consumer should wait.
         */
        while (!isCoffeeReady) {

            System.out.println(Thread.currentThread().getName()
                    + " : Waiting for coffee...");

            wait();
        }

        // Critical Section
        System.out.println(Thread.currentThread().getName()
                + " : Drinking Coffee ☕");

        Thread.sleep(1000);

        isCoffeeReady = false;

        System.out.println(Thread.currentThread().getName()
                + " : Cup Empty. Ready for next coffee.");

        /*
         * Notify producer
         * that new coffee can be prepared.
         */
        notifyAll();
    }
}

// ---------------------- Producer Thread ----------------------
class Producer extends Thread {

    private CoffeeMachine machine;

    Producer(CoffeeMachine machine) {
        this.machine = machine;
    }

    @Override
    public void run() {

        try {

            // Prepare 5 cups
            for (int i = 1; i <= 5; i++) {

                machine.makeCoffee();

                // Simulate doing other work
                Thread.sleep(500);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// ---------------------- Consumer Thread ----------------------
class Consumer extends Thread {

    private CoffeeMachine machine;

    Consumer(CoffeeMachine machine) {
        this.machine = machine;
    }

    @Override
    public void run() {

        try {

            // Drink 5 cups
            for (int i = 1; i <= 5; i++) {

                machine.drinkCoffee();

                Thread.sleep(700);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// ---------------------- Driver ----------------------
public class Main {

    public static void main(String[] args) {

        CoffeeMachine machine = new CoffeeMachine();

        Producer producer = new Producer(machine);
        Consumer consumer = new Consumer(machine);

        producer.setName("Producer");
        consumer.setName("Consumer");

        producer.start();
        consumer.start();
    }
}
