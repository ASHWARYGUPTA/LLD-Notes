/*
 * Teaching note:
 * This problem version mixes workflow code with concrete object selection. It
 * fails to scale because every new transport type expands this method with more
 * branching, duplication, and knowledge of implementation classes.
 */

// This problem version mixes business flow with object creation decisions. It
// fails to scale because every new transport mode forces edits in the control
// class, so one class keeps growing with more branching and more responsibilities.

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
        // Creation logic is duplicated inside the workflow, so expansion means rewriting this method.
        if ("Air".equals(mode)) {
            Logistics log = new Air();
            log.send();
        }
        if ("Road".equals(mode)) {
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

    // The empty driver keeps attention on the scaling problem inside LogisticsControl.
    public static void main(String[] args) {

    }
}
