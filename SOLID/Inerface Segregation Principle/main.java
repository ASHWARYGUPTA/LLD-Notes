
//To many things in one single Interface now if rider implements this he will have to implement everything unneccessary
// interface Uber {
//     void bookRide();
//     void acceptRide();
//     void drive();
//     void endRide();
//     void payRide();
// };
interface RiderInterface {

    void bookRide();

    void payRide();

}

interface DriverInterface {

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
