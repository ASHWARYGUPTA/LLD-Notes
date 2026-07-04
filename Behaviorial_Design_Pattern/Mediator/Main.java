import java.util.ArrayList;
import java.util.List;

/*
==============================================================================
                    MEDIATOR PATTERN

Problem:
--------

Suppose multiple users are collaboratively editing a document.

Without Mediator:

Alice -------> Bob
Alice -------> Charlie
Bob ---------> Alice
Bob ---------> Charlie
Charlie -----> Alice
Charlie -----> Bob

Every user knows every other user.

As users increase, communication becomes a huge mesh.

With Mediator:

             CollaborativeDocument
                    (Mediator)
               /        |        \
           Alice      Bob     Charlie

Users never communicate directly.

They only communicate with the Mediator.

Mediator decides who receives the message.

==============================================================================
*/


/*==============================================================================
    MEDIATOR

    This is the contract.

    Every mediator should support

        1. join(User)

        2. broadcastChange(change, sender)

==============================================================================*/
interface DocumentSessionMediator {

    /*
     * Sender tells mediator:
     *
     * "I made a change."
     *
     * Mediator decides who should receive it.
     */
    void broadcastChange(String change, User sender);

    /*
     * Register a user.
     *
     * Instead of users registering with every other user,
     * they register only with the mediator.
     */
    void join(User user);
}


/*==============================================================================
    CONCRETE MEDIATOR

    This class manages ALL communication.

    This is the biggest advantage of Mediator Pattern.

    Every routing rule is centralized here.

==============================================================================*/
class CollaborativeDocument implements DocumentSessionMediator {

    /*
     * Mediator knows every participant.
     *
     * Users DO NOT know each other.
     */
    private List<User> users = new ArrayList<>();


    /*
     * User joins the collaborative document.
     *
     * Only ONE registration is needed.
     *
     * Contrast this with the old approach where every user
     * had to manually add every other user.
     */
    @Override
    public void join(User user) {

        users.add(user);

        System.out.println(user.getName() + " joined document.");
    }


    /*
     * Entire communication logic lives here.
     *
     * Tomorrow if business says:
     *
     * ✔ Notify only editors
     * ✔ Notify online users
     * ✔ Notify same department
     * ✔ Ignore blocked users
     * ✔ Send asynchronously
     *
     * Only THIS method changes.
     *
     * User class remains untouched.
     */
    @Override
    public void broadcastChange(String change, User sender) {

        System.out.println("\nMediator broadcasting change...\n");

        for (User user : users) {

            /*
             * Sender doesn't need to receive
             * his own update.
             */
            if (user != sender) {

                user.receiveChange(change, sender);
            }
        }
    }
}


/*==============================================================================
    COLLEAGUE

    Notice what User contains.

    It has ONLY ONE dependency.

        DocumentSessionMediator

    It DOES NOT know

        Alice
        Bob
        Charlie

==============================================================================*/
class User {

    private String name;

    /*
     * User communicates ONLY through mediator.
     *
     * This creates loose coupling.
     */
    private DocumentSessionMediator mediator;

    public User(String name,
                DocumentSessionMediator mediator) {

        this.name = name;
        this.mediator = mediator;
    }

    public String getName() {
        return name;
    }


    /*
     * User performs business work.

     * Notice:

     * User DOES NOT loop through users.

     * User DOES NOT know receivers.

     * User simply informs the mediator.
     */
    public void makeChange(String change) {

        System.out.println(name
                + " made change : "
                + change);

        /*
         * Instead of

         * for(User u : users)

         * the sender delegates the responsibility
         * to the mediator.
         */
        mediator.broadcastChange(change, this);
    }


    /*
     * Called by mediator.

     * User doesn't know WHO else exists.

     * It simply receives notifications.
     */
    public void receiveChange(String change,
                              User sender) {

        System.out.println(
                name
                        + " received \""
                        + change
                        + "\" from "
                        + sender.getName());
    }
}


/*==============================================================================
    CLIENT

    Creates mediator.

    Creates users.

    Registers users.

==============================================================================*/
public class Main {

    public static void main(String[] args) {

        /*
         * Create ONE mediator.
         */
        DocumentSessionMediator mediator =
                new CollaborativeDocument();


        /*
         * Every user shares the SAME mediator.
         */
        User alice = new User("Alice", mediator);

        User bob = new User("Bob", mediator);

        User charlie = new User("Charlie", mediator);


        /*
         * Register users.
         *
         * Users never register with each other.
         */
        mediator.join(alice);

        mediator.join(bob);

        mediator.join(charlie);


        System.out.println("\n----------------------------\n");


        /*
         * Alice edits document.
         */
        alice.makeChange("Added Chapter 1");


        System.out.println("\n----------------------------\n");


        /*
         * Bob edits document.
         */
        bob.makeChange("Fixed spelling mistakes");
    }
}


/*
==============================================================================
                        OUTPUT FLOW
==============================================================================

Alice made change : Added Chapter 1

↓

Alice tells mediator

↓

Mediator checks all users

↓

Mediator sends update

↓

Bob receives

Charlie receives

Alice is ignored.

==============================================================================



WITHOUT MEDIATOR

Alice

↓

Bob.receive()

Charlie.receive()

David.receive()

Emma.receive()

...

Alice must know everyone.

==============================================================================



WITH MEDIATOR

Alice

↓

Mediator.broadcast()

↓

Mediator decides

↓

Bob.receive()

Charlie.receive()

==============================================================================



WHY IS THIS SO GOOD?

1. LOOSE COUPLING

Before

User

↓

User

↓

User

↓

User

Everyone depended on everyone.

Now

User

↓

Mediator

Only ONE dependency.

==============================================================================



2. SINGLE RESPONSIBILITY

User is responsible only for

✔ Editing document

✔ Receiving updates

NOT

✘ Routing messages

✘ Maintaining user list

✘ Notification logic

Mediator handles all communication.

==============================================================================



3. OPEN CLOSED PRINCIPLE

Suppose tomorrow business says

Notify only online users.

Old Design

Modify User class.

New Design

Modify only

CollaborativeDocument.broadcastChange()

Users remain untouched.

==============================================================================



4. CENTRALIZED COMMUNICATION

Instead of communication logic scattered inside every user,

everything is located in one place.

Easy to debug.

Easy to maintain.

Easy to extend.

==============================================================================



5. EASY TO ADD RULES

Tomorrow we can add

✔ User permissions

✔ Admin notifications

✔ User blocking

✔ Offline buffering

✔ Logging

✔ Analytics

Only Mediator changes.

==============================================================================



6. REDUCES DEPENDENCIES

Without Mediator

N users

Each user knows N-1 users.

Approximately

O(N²)

relationships.

With Mediator

Every user knows only ONE object.

Mediator.

Approximately

O(N)

relationships.

Huge reduction in coupling.

==============================================================================



REAL WORLD EXAMPLES

✔ Air Traffic Control Tower

Planes never communicate directly.

All communication goes through ATC.

----------------------------------

✔ Chat Room

Users send messages to Server.

Server distributes messages.

----------------------------------

✔ WhatsApp Group

User

↓

WhatsApp Server

↓

All Group Members

----------------------------------

✔ Google Docs Collaborative Editing

Editor

↓

Collaboration Server

↓

Other Editors

----------------------------------

✔ GUI Components (Java Swing)

Buttons

TextFields

Checkboxes

communicate through a Dialog (Mediator).

==============================================================================

WHEN TO USE

✔ Many objects communicate frequently.

✔ Communication logic is becoming complicated.

✔ Objects are tightly coupled.

✔ You want centralized coordination.

==============================================================================

TRADEOFF

As more communication rules are added,

the Mediator can become very large
("God Object").

So keep the mediator focused on one responsibility
or split it into multiple mediators if needed.

==============================================================================
*/