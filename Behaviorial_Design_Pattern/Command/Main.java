/*
 * Teaching note:
 * This file shows the Command pattern separating "what button was pressed"
 * from "how a device performs the work". That indirection makes undo, replay,
 * remapping, and queued execution possible without hard-coding device logic
 * into the remote. The tradeoff is extra command classes and a slightly more
 * abstract control flow.
 */

import java.util.*;

// =======================
// Receivers
// =======================
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

// =======================
// Command Interface
// =======================
// Each command wraps one request so the invoker can stay unaware of concrete
// device APIs and still support extension points like undo or remapping.
interface Command {

    void execute();

    void undo();
}

// =======================
// Concrete Commands
// =======================
class LightONCommand implements Command {

    private Light light;

    public LightONCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.ON();
    }

    @Override
    public void undo() {
        light.OFF();
    }
}

class LightOFFCommand implements Command {

    private Light light;

    public LightOFFCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.OFF();
    }

    @Override
    public void undo() {
        light.ON();
    }
}

class ACONCommand implements Command {

    private AC ac;

    public ACONCommand(AC ac) {
        this.ac = ac;
    }

    @Override
    public void execute() {
        ac.ON();
    }

    @Override
    public void undo() {
        ac.OFF();
    }
}

class ACOFFCommand implements Command {

    private AC ac;

    public ACOFFCommand(AC ac) {
        this.ac = ac;
    }

    @Override
    public void execute() {
        ac.OFF();
    }

    @Override
    public void undo() {
        ac.ON();
    }
}

// =======================
// Invoker
// =======================
class RemoteControl {

    // The invoker stores abstractions, so new devices fit in as long as they
    // provide a Command object instead of forcing RemoteControl to learn device APIs.
    private Command[] buttons;
    private Stack<Command> undoStack;

    public RemoteControl(int numberOfButtons) {
        buttons = new Command[numberOfButtons];
        undoStack = new Stack<>();
    }

    public void setCommand(int slot, Command command) {
        if (slot >= 0 && slot < buttons.length) {
            buttons[slot] = command;
        }
    }

    // Pressing a button does not branch on device type. The invoker simply
    // executes the command it was configured with, which is why the design
    // remains open for extension.
    public void pressButton(int slot) {
        if (slot >= 0 && slot < buttons.length && buttons[slot] != null) {
            buttons[slot].execute();
            undoStack.push(buttons[slot]);
        } else {
            System.out.println("No command assigned to slot " + slot);
        }
    }

    // Undo works because each command carries the knowledge needed to reverse
    // its own action. The tradeoff is that commands must define a meaningful
    // inverse operation, which is not always trivial in real systems.
    public void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo");
            return;
        }

        Command command = undoStack.pop();
        command.undo();
    }
}

// =======================
// Client
// =======================
public class Main {

    public static void main(String[] args) {

        // Receivers
        Light light = new Light();
        AC ac = new AC();

        // Commands
        Command lightOn = new LightONCommand(light);
        Command lightOff = new LightOFFCommand(light);
        Command acOn = new ACONCommand(ac);
        Command acOff = new ACOFFCommand(ac);

        // Invoker
        RemoteControl remote = new RemoteControl(4);

        remote.setCommand(0, lightOn);
        remote.setCommand(1, lightOff);
        remote.setCommand(2, acOn);
        remote.setCommand(3, acOff);

        System.out.println("Executing Commands:");
        remote.pressButton(0); // Light ON
        remote.pressButton(2); // AC ON
        remote.pressButton(1); // Light OFF
        remote.pressButton(3); // AC OFF

        System.out.println("\nUndo Operations:");
        remote.undo(); // AC ON
        remote.undo(); // Light ON
        remote.undo(); // AC OFF
        remote.undo(); // Light OFF
        remote.undo(); // Nothing to undo
    }
}
