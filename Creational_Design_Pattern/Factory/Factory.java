/*
 * Teaching note:
 * Factory centralizes the decision of which concrete logistics object to build.
 * That scales better than the problem version because callers use the
 * abstraction and only one creation point changes when a new transport mode is
 * introduced.
 */

// This refactored version pushes creation into a dedicated factory. That scales
// better than the problem version because the caller works with the `Logistics`
// abstraction and the branching for construction is centralized in one place.

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
        // New transport options are added here instead of spreading creation logic across callers.
        if ("Air".equals(mode)) {
            return new Air();
        }
        if ("Road".equals(mode)) {
            Logistics log = new Road();
            return new Road();
        }
        return new Air();
    }

}

class LogisticsControl {

    public void send(String mode) {
        // The control flow now focuses on using a ready object instead of deciding how to build it.
        Logistics air = LogisticsFactory.getInstance(mode);
        air.send();
    }
}

public class Factory {

    // The example leaves main minimal because the factory method above is the teaching focus.
    public static void main(String[] args) {

    }
}
