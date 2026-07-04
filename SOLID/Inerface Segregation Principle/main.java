/*
 * Teaching note:
 * This file illustrates Interface Segregation by splitting rider behavior from
 * driver behavior instead of forcing one huge "Uber" contract on everyone.
 * That keeps clients from depending on methods they do not need. The tradeoff
 * is more interfaces, but each one is easier to implement correctly.
 */

//To many things in one single Interface now if rider implements this he will have to implement everything unneccessary
// interface Uber {
//     void bookRide();
//     void acceptRide();
//     void drive();
//     void endRide();
//     void payRide();
// };
interface RiderInterface {

    // Rider-specific actions stay together so rider implementations do not
    // inherit irrelevant driver responsibilities.
    void bookRide();

    void payRide();

}

interface DriverInterface {

    // Driver-specific responsibilities are isolated in their own contract,
    // which is the heart of Interface Segregation.
    void acceptRide();

    void drive();

    void endRide();
}

class Rider implements RiderInterface {

    public void bookRide() {
        System.out.println("Book Ride");
        return;
    }

    public void payRide() {
        System.out.println("Pay Ride");
        return;
    }

}

class Driver implements DriverInterface {

    // This example is intentionally left inconsistent/incomplete:
    // the class does not fully match the interface contract yet.
    // The comments explain the design goal without silently correcting the code.
    public void acceptRide() {

    }

    public void bookRide() {

    }

    public void drive() {

    }

    public void endRide() {

    }
}

public class main {

    public static void main(String[] args) {

    }
}
