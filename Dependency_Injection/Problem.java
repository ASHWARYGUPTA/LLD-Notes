/*
    PROBLEM:
    --------
    OrderService is creating its own dependencies.

    This means OrderService is tightly coupled to
    specific implementations.

    If tomorrow we want to:

    • Change Razorpay → Stripe
    • Send Email instead of SMS
    • Mock PaymentService while testing

    We have to MODIFY OrderService.

    This violates:
    - Dependency Inversion Principle
    - Open Closed Principle
 */

class OrderService {

    // Concrete object created inside the class.
    // Inventory dependency
    private InventoryService inventory = new InventoryService();

    // Payment dependency
    // Hardcoded to Razorpay
    private PaymentService payment = new RazorpayPayment();

    // Notification dependency
    private NotificationService notification = new NotificationService();

    public void checkout(Order order) {

        // Reserve products
        inventory.blockItems(order);

        // Charge customer
        payment.process(order);

        // Send confirmation
        notification.sendConfirmation(order);
    }
}

public class Problem {

}
