/*
 * Teaching note:
 * This file intentionally keeps the remote tightly coupled to concrete
 * receivers. That works for a couple of devices, but every new appliance or
 * feature forces the invoker to grow more methods and more condition handling.
 * Command solves this by packaging each request as an object, with the tradeoff
 * of introducing more types up front.
 */

//RECIEVER
class Light {

    public void ON() {
        System.out.println("LIGHT IS ON");
    }

    public void OFF() {
        System.out.println("LIGHT IS OFF");
    }
}

class AC {

    public void ON() {
        System.out.println("AC IS ON");
    }

    public void OFF() {
        System.out.println("AC IS OFF");
    }
}

//MIDDLEWARE
//Very tightly coupling
class NiaveRemoteControl {

    // The remote knows every concrete device directly, so it cannot be reused
    // without editing this class whenever a new receiver type is introduced.
    private Light light;
    private AC ac;
    public String lastAction = "";

    public NiaveRemoteControl(Light light, AC ac) {
        this.light = light;
        this.ac = ac;
    }

    // Each new appliance forces more device-specific methods into the same
    // remote class, so reuse gets worse as the product surface grows.
    public void turnOnLight() {
        this.light.ON();
        lastAction = "LIGHT_ON";
    }

    public void turnOffLight() {
        this.light.OFF();
        lastAction = "LIGHT_OFF";

    }

    public void turnOnAC() {
        this.ac.ON();
        lastAction = "AC_ON";

    }

    public void turnOffAc() {
        this.ac.OFF();
        lastAction = "AC_OFF";
    }

    public void undo() {
        // Undo is tracked with raw strings instead of command objects.
        // That makes the feature brittle: typos, branching mistakes, or richer
        // multi-step actions quickly become hard to represent and maintain.
        switch (lastAction) {
            case "LIGHT_OFF":
                this.light.OFF();
                break;
            case "LIGHT_ON":
                this.light.ON();
                break;
            case "AC_ON":
                this.ac.ON();
                break;
            case "AC_OFF":
                this.ac.OFF();
                break;
        }

    }
}

public class Problem {

    public static void main(String[] args) {
        // The sample stays minimal here, but the real issue is structural:
        // control logic lives in the remote instead of in independent commands.
        Light light = new Light();
        light.ON();
        light.OFF();
    }
}
