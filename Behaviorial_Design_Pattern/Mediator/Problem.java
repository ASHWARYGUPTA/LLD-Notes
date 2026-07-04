/*
 * Teaching note:
 * This file intentionally shows the pre-Mediator design where collaborators
 * talk to each other directly. That works for a tiny demo, but it scales
 * poorly because every participant now owns both domain behavior and message
 * routing. A Mediator fixes that by centralizing coordination, with the
 * tradeoff that the mediator itself becomes an important dependency to design carefully.
 */

import java.util.*;

class User {

    private String name;

    // This single field is the main smell: collaborator management is mixed
    // into the domain object instead of being centralized elsewhere.
    /*
     * Every user stores references to every other collaborator.
     *
     * Problem:
     * This creates tight coupling because each User directly depends
     * on all the other User objects.
     *
     * In Mediator Pattern, users should not know about each other.
     * They should only know about the Mediator.
     */
    private List<User> others;

    public User(String name) {
        this.name = name;
        this.others = new ArrayList<>();
    }

    /*
     * Every time a new collaborator joins,
     * all existing users must manually add that collaborator.
     *
     * Problem:
     * Registration is decentralized and error-prone.
     *
     * In Mediator Pattern,
     * users register only once with the Mediator,
     * and the Mediator manages all participants.
     */
    public void addCollaborater(User user) {
        others.add(user);
    }

    /*
     * User is responsible for broadcasting changes.
     *
     * Problem:
     * Sender knows exactly who should receive the message.
     *
     * This violates the principle of loose coupling.
     *
     * In Mediator Pattern,
     * sender simply tells the Mediator:
     *
     * mediator.notify(this, change);
     *
     * The Mediator decides who receives it.
     */
    public void makeChanges(String change) {

        System.out.println(name + " make changes " + change);

        /*
         * Explicitly iterating through every collaborator.
         *
         * Problem:
         * Communication logic is inside User.
         *
         * If tomorrow business says:
         *
         * - notify only online users
         * - notify users of same project
         * - notify admins only
         * - notify except sender
         *
         * this method must change.
         *
         * In Mediator Pattern,
         * all routing logic belongs to the Mediator.
         */
        for (User u : others) {
            u.recieveChanges(change, this);
        }
    }

    /*
     * Receives a message directly from another User.
     *
     * Problem:
     * Receiver knows sender directly.
     *
     * Communication is peer-to-peer.
     *
     * In Mediator Pattern,
     * communication always flows through the Mediator.
     */
    public void recieveChanges(String change, User user) {

        System.out.println(
                name
                + " recieved : \""
                + change
                + "\" from "
                + user.name);
    }
}

public class Problem {

    /*
     * ========================= Problems =========================
     *
     * 1. Tight Coupling
     *    Every User knows every other User.
     *
     * 2. Communication is Direct
     *    Sender directly calls receiver methods.
     *
     * 3. Business Logic is Distributed
     *    Message routing logic exists inside every User.
     *
     * 4. Hard to Maintain
     *    If notification rules change,
     *    every User class may need modification.
     *
     * 5. Poor Scalability
     *    With N users,
     *    every user may store references to N-1 users.
     *
     * 6. Violates Single Responsibility Principle
     *    User is responsible for:
     *      - editing document
     *      - maintaining collaborators
     *      - routing messages
     *      - notifying others
     *
     *    It has multiple responsibilities.
     *
     * 7. Difficult to Reuse
     *    User cannot easily be reused in another application
     *    because communication logic is embedded inside it.
     *
     * ========================= Mediator Solution =========================
     *
     * Instead of:
     *
     * User -------> User
     * User -------> User
     * User -------> User
     *
     * Communication becomes:
     *
     *             Mediator
     *            /    |    \
     *         User  User  User
     *
     * Every User only knows the Mediator.
     *
     * Sender:
     *      mediator.notify(this, change);
     *
     * Mediator:
     *      decides who should receive the message.
     *
     * Users never communicate directly with each other,
     * resulting in loose coupling and centralized communication logic.
     */
}
