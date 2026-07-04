/*
 * Teaching note:
 * This version hard-codes algorithm selection with string checks inside the
 * service itself. That means every new matching rule changes existing code and
 * keeps unrelated business logic tangled together. Strategy fixes that by
 * moving each algorithm into its own class, with the tradeoff of managing more objects.
 */

//Uber App

class RideMatchingService {

    public void matchRider(String riderLocation, String matchType) {
        // The service is doing two jobs at once:
        // choosing an algorithm and executing that algorithm.
        // That is the coupling the Strategy pattern removes.
        // Each new branch also raises the testing burden because one class now
        // owns every matching policy instead of delegating to focused objects.
        if (matchType.equals("NEAREST")) {
            //Logic
        } else if (matchType.equals("SURGE_PRIORITY")) {
            //Logic
        } else if (matchType.equals("AIRPORT_QUEUE")) {
            //Logic
        }
        //If 10 more ways 10 more if else
        //Which kind of strategy to choose
    }
}

public class Problem {

}
