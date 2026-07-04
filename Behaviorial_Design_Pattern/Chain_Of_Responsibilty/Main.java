/*
 * Teaching note:
 * This version demonstrates why Chain of Responsibility works better than a
 * single support class with a giant conditional tree. Each handler owns one
 * decision and either handles the request or forwards it. The main tradeoff is
 * that chain order matters, and an unhandled request can still fall through to
 * the end if the chain is assembled poorly.
 */

// Base Handler
// ---------------------------
// This abstract class defines the common structure for all handlers.
// Each handler knows only about the next handler in the chain, not the
// entire chain. This creates loose coupling.

abstract class SupportHandler {

    protected SupportHandler nextHandler;

    // Sets the next handler in the chain.
    public void setNextHandler(SupportHandler next) {
        this.nextHandler = next;
    }

    // Every concrete handler must decide:
    // 1. Can I handle the request?
    // 2. If not, forward it to the next handler.
    public abstract void handleRequest(String requestType);
}

// ---------------------------
// Technical Support Handler
// ---------------------------
class TechnicalSupportHandler extends SupportHandler {

    @Override
    public void handleRequest(String requestType) {

        // ✅ This handler is responsible ONLY for technical requests.
        // It follows the Single Responsibility Principle.
        if (requestType.equalsIgnoreCase("technical")) {
            System.out.println("Handled by Technical Team");
        } // ✅ If it cannot handle the request,
        // it forwards it to the next handler instead of rejecting it.
        // This is the core idea of the Chain of Responsibility pattern.
        else if (nextHandler != null) {
            nextHandler.handleRequest(requestType);
        } // ✅ End of chain.
        else {
            System.out.println("No handler available");
        }
    }
}

// ---------------------------
// Delivery Support Handler
// ---------------------------
class DeliverySupportHandler extends SupportHandler {

    @Override
    public void handleRequest(String requestType) {

        // ✅ Responsible only for delivery-related requests.
        // It does not know anything about billing or technical support.
        if (requestType.equalsIgnoreCase("delivery")) {
            System.out.println("Handled by Delivery Team");
        } // ✅ If unable to process, delegate to the next handler.
        else if (nextHandler != null) {
            nextHandler.handleRequest(requestType);
        } // ✅ No handler exists after this.
        else {
            System.out.println("No handler available");
        }
    }
}

// ---------------------------
// Refund (Billing) Handler
// ---------------------------
class RefundSupportHandler extends SupportHandler {

    @Override
    public void handleRequest(String requestType) {

        // ✅ Handles only refund requests.
        // Each handler encapsulates its own business logic.
        if (requestType.equalsIgnoreCase("refund")) {
            System.out.println("Handled by Billing Team");
        } // ✅ Forward the request if it doesn't belong here.
        else if (nextHandler != null) {
            nextHandler.handleRequest(requestType);
        } // ✅ Nobody else can handle it.
        else {
            System.out.println("No handler available");
        }
    }
}

// ---------------------------
// General Support Handler
// ---------------------------
class GeneralSupportHandler extends SupportHandler {

    @Override
    public void handleRequest(String requestType) {

        // ✅ Handles only general queries.
        if (requestType.equalsIgnoreCase("general")) {
            System.out.println("Handled by General Support");
        } // ✅ Forward if another handler may be able to process it.
        else if (nextHandler != null) {
            nextHandler.handleRequest(requestType);
        } // ✅ Request reached the end of the chain.
        else {
            System.out.println("No handler available");
        }
    }
}

// ---------------------------
// Client
// ---------------------------
public class Main {

    public static void main(String[] args) {

        // Create handlers
        SupportHandler technical = new TechnicalSupportHandler();
        SupportHandler delivery = new DeliverySupportHandler();
        SupportHandler refund = new RefundSupportHandler();
        SupportHandler general = new GeneralSupportHandler();

        // Build the chain
        // Client decides the order of handlers.
        // The handlers themselves remain unchanged.
        // That assembly step is the main tradeoff: behavior is flexible, but a
        // bad chain order can route requests in surprising ways.
        technical.setNextHandler(delivery);
        delivery.setNextHandler(refund);
        refund.setNextHandler(general);

        // Test requests
        technical.handleRequest("technical");
        technical.handleRequest("delivery");
        technical.handleRequest("refund");
        technical.handleRequest("general");
        technical.handleRequest("payment");
    }
}

/*
================== Why this is a correct Chain of Responsibility ==================

✅ Each handler has only one responsibility.
   - TechnicalHandler handles only technical requests.
   - DeliveryHandler handles only delivery requests.
   - RefundHandler handles only refund requests.
   - GeneralHandler handles only general requests.

✅ Each handler knows only about the next handler.
   It is loosely coupled with the rest of the system.

✅ Requests are forwarded automatically.
   If one handler cannot process a request,
   it delegates it to the next handler.

✅ No long if-else chain in one class.
   Logic is distributed across multiple handler classes.

✅ Easy to extend.
   Adding a PaymentHandler requires:
       1. Create PaymentHandler.
       2. Insert it into the chain.
   Existing handlers remain unchanged (Open/Closed Principle).

✅ Easy to reorder.
   Changing the processing order only requires changing:
       setNextHandler(...)
   No handler implementation changes.

✅ Uses polymorphism.
   The client calls:
       handler.handleRequest(...)
   without knowing which handler will eventually process the request.

=====================================================================
 */
