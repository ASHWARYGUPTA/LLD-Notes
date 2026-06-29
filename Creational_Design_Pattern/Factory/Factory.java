
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

class LogisticsFactory {

    public static Logistics getInstance(String mode) {
        if (mode == "Air") {
            return new Air();
        }
        if (mode == "Road") {
            Logistics log = new Road();
            return new Road();
        }
        return new Air();
    }

}

class LogisticsControl {

    public void send(String mode) {
        Logistics air = LogisticsFactory.getInstance(mode);
        air.send();
    }
}

public class Factory {

    public static void main(String[] args) {

    }
}
