
interface Logistics {

    void send();
}

class Air implements Logistics {

    @Override
    public void send() {
        System.out.println("Sent by Air");
    }
}

class Road implements Logistics {

    @Override
    public void send() {
        System.err.println("Sent by Road");
    }
}

class LogisticsControl { // Breaks SRP -> 1) Decides what kind of object it is  2)Calling the methods 

    public void send(String mode) {
        if (mode == "Air") {
            Logistics log = new Air();
            log.send();
        }
        if (mode == "Road") {
            Logistics log = new Road();
            log.send();
        }
        //If Tommorow Water is addee
        // if (mode == "Water") {
        //     Logistics log = new Road();
        //     log.send();
        // }
    }
}

public class Problem {

    public static void main(String[] args) {

    }
}
