/*
 * Teaching note:
 * This file keeps all support-routing decisions inside one service, which is
 * exactly the design Chain of Responsibility tries to avoid. It becomes hard
 * to extend, easy to break with string mistakes, and awkward to test because
 * every request type is coupled to one growing method. The pattern-friendly
 * alternative distributes that decision-making across handlers, at the cost of
 * creating more small classes.
 */

class SupportService {

    public void handleRequest(String type) {

        // ❌ Problem 1: Uses a long if-else chain.
        // As more request types are added, this method keeps growing and
        // becomes difficult to read and maintain.
        if (type.equals("general")) {

            // ❌ Problem 2: Routing logic and business logic are mixed together.
            // This class decides WHO should handle the request and also performs
            // the handling.
            System.out.println("Handled by general support");

        } else if (type.equals("refund")) {

            // ❌ Problem 3: SupportService knows about the Billing Team.
            // It is tightly coupled to every department.
            System.out.println("Handled by Billing Team");

        } else if (type.equals("technical")) {

            // ❌ Problem 4: Every new request type requires modifying this class.
            // This violates the Open/Closed Principle.
            System.out.println("Handled by Technical Team");

        } else if (type.equals("deilvary")) {

            // ❌ Problem 5: Typo in the request type ("deilvary" instead of "delivery").
            // Using raw strings is error-prone.
            System.out.println("Handled by Delivary Team");

        } else {

            // ❌ Problem 6: There is no request forwarding.
            // In Chain of Responsibility, if one handler cannot process
            // the request, it forwards it to the next handler.
            // Here, the request simply ends.
            System.out.println("No handler");
        }

        // ❌ Problem 7: All request-handling decisions are centralized
        // in one class instead of being distributed among independent handlers.
    }
}

public class Problem {

    public static void main(String[] args) {

        SupportService service = new SupportService();

        service.handleRequest("general");
        service.handleRequest("refund");
        service.handleRequest("technical");
        // This last call highlights a real maintenance risk: one typo in a
        // centralized string-based router silently sends valid requests down
        // the wrong path at runtime.
        service.handleRequest("delivery"); // ❌ Prints "No handler" because of the typo.
    }
}

/*
==================== Overall Problems (Chain of Responsibility Context) ====================

1. ❌ Violates Open/Closed Principle
   - Adding a new request type requires modifying SupportService.

2. ❌ Violates Single Responsibility Principle
   - The class performs routing and contains handling logic for every department.

3. ❌ Tight coupling
   - SupportService knows about General, Billing, Technical, and Delivery teams.

4. ❌ Long if-else chain
   - Poor scalability as request types increase.

5. ❌ No polymorphism
   - Behavior is selected using string comparisons instead of handler objects.

6. ❌ No Chain of Responsibility
   - Requests are not forwarded from one handler to another.

7. ❌ Hard to reorder handlers
   - Changing processing order requires editing the if-else chain.

8. ❌ Difficult to add/remove handlers dynamically
   - Every change requires modifying existing code.

9. ❌ Hard to test
   - Individual department logic cannot be tested independently.

10. ❌ Uses magic strings
    - Typos like "deilvary" can silently break the program.

============================================================================================
 */
