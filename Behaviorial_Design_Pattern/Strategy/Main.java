/*
 * Teaching note:
 * This file demonstrates Strategy by isolating ride-matching algorithms behind
 * one interface. The service can swap matching behavior at runtime without
 * growing conditionals or knowing algorithm details. The tradeoff is that the
 * caller must choose and supply a strategy object deliberately.
 */

interface Strategy {

    void match(String riderLocation);
}

class NearestStrategy implements Strategy {

    @Override
    public void match(String riderLocation) {
        // Logic is intentionally omitted in this teaching example.
        // The key design idea is that "nearest" can evolve independently of
        // other matching rules because it is isolated behind the Strategy contract.
    }
}

class SurgePriorityStrategy implements Strategy {

    @Override
    public void match(String riderLocation) {
        // A surge-aware algorithm can be plugged in without editing
        // RideMatchingService itself, which is why the design stays open for extension.
    }
}

class AirportStrategy implements Strategy {

    @Override
    public void match(String riderLocation) {
        // Different business contexts can use specialized selection logic while
        // still exposing the same method to the high-level service.
    }
}

class RideMatchingService {

    // The high-level service depends on the abstraction, not on concrete
    // matching classes, which keeps policy selection separate from execution.
    private Strategy matchingService;

    public RideMatchingService(Strategy st) {
        this.matchingService = st;
    }

    public void setStrategy(Strategy st) {
        // Runtime swapping is one of the biggest practical benefits of Strategy,
        // especially when business rules vary by market, time, or experiment.
        this.matchingService = st;
    }

    public void matchRider(String riderLocation) {
        this.matchingService.match(riderLocation);
    }
}

public class Main {

    public static void main(String[] args) {
        // The client chooses the policy object once and can swap it later
        // without forcing RideMatchingService to grow new branches.
        RideMatchingService rideMatchingService = new RideMatchingService(new AirportStrategy());
        rideMatchingService.matchRider("1234");
        //Changing can be done during runtime
    }
}
