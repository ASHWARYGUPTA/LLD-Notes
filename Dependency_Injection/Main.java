/*
    COMPLETE EXAMPLE OF DEPENDENCY INJECTION (DI)

    Scenario:
    ---------
    Amazon/Flipkart Order Checkout

    OrderService needs 3 services:

    1. InventoryService  -> Reserve Products
    2. PaymentService    -> Charge Customer
    3. NotificationService -> Send Confirmation

    BAD DESIGN:
    -----------
    OrderService creates these objects itself using new.

    GOOD DESIGN:
    ------------
    Someone else creates these objects and injects them into
    OrderService.

    This is called Dependency Injection.

    Benefits:
    ---------
    ✔ Loose Coupling
    ✔ Easy Unit Testing
    ✔ Easy to switch implementations
    ✔ Follows Dependency Inversion Principle
    ✔ Follows Open Closed Principle
 */

//--------------------------------------------------------
// Order Entity
//--------------------------------------------------------
class Order {

    private int orderId;
    private double amount;

    public Order(int orderId, double amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    public int getOrderId() {
        return orderId;
    }

    public double getAmount() {
        return amount;
    }
}

//--------------------------------------------------------
// PAYMENT ABSTRACTION
//--------------------------------------------------------

/*
    Instead of depending on a specific payment gateway,
    OrderService depends on this interface.

    Tomorrow we can create:

    StripePayment
    PayPalPayment
    PhonePePayment
    GooglePayPayment

    without changing OrderService.
 */
interface PaymentService {

    void process(Order order);
}

//--------------------------------------------------------
// Razorpay Implementation
//--------------------------------------------------------
class RazorpayPayment implements PaymentService {

    @Override
    public void process(Order order) {

        System.out.println("Processing payment using Razorpay...");
        System.out.println("Amount : ₹" + order.getAmount());

        // API call to Razorpay
        System.out.println("Payment Successful.\n");
    }
}

//--------------------------------------------------------
// Stripe Implementation
//--------------------------------------------------------
class StripePayment implements PaymentService {

    @Override
    public void process(Order order) {

        System.out.println("Processing payment using Stripe...");
        System.out.println("Amount : ₹" + order.getAmount());

        // API call to Stripe
        System.out.println("Payment Successful.\n");
    }
}

//--------------------------------------------------------
// Inventory Service
//--------------------------------------------------------
class InventoryService {

    public void blockItems(Order order) {

        System.out.println("Checking Inventory...");

        System.out.println("Inventory Reserved for Order "
                + order.getOrderId() + "\n");
    }
}

//--------------------------------------------------------
// Notification Service
//--------------------------------------------------------
class NotificationService {

    public void sendConfirmation(Order order) {

        System.out.println("Sending Email/SMS Confirmation...");

        System.out.println("Confirmation Sent for Order "
                + order.getOrderId() + "\n");
    }
}

//--------------------------------------------------------
// Order Service
//--------------------------------------------------------

/*
    Notice carefully.

    There is NO new keyword inside OrderService.

    OrderService is NOT responsible for creating objects.

    It only USES them.

    Someone else provides them.

    This is Dependency Injection.
 */
class OrderService {

    private InventoryService inventoryService;

    private PaymentService paymentService;

    private NotificationService notificationService;

    /*
        Constructor Injection

        All dependencies are provided from outside.

        Spring Boot also prefers constructor injection.
     */
    public OrderService(
            InventoryService inventoryService,
            PaymentService paymentService,
            NotificationService notificationService
    ) {

        this.inventoryService = inventoryService;
        this.paymentService = paymentService;
        this.notificationService = notificationService;
    }


    /*
        Checkout Workflow

        1. Reserve Inventory
        2. Charge Customer
        3. Send Confirmation
     */
    public void checkout(Order order) {

        System.out.println("-----------------------------------");
        System.out.println("Starting Checkout...");
        System.out.println("-----------------------------------\n");

        inventoryService.blockItems(order);

        paymentService.process(order);

        notificationService.sendConfirmation(order);

        System.out.println("Order Completed Successfully.");
    }

}

//--------------------------------------------------------
// Driver Class
//--------------------------------------------------------
public class Main {

    public static void main(String[] args) {

        /*
            STEP 1

            Create all dependencies.

            In Spring Boot,
            Spring Container creates these automatically.

            Here we are doing it manually.
         */
        InventoryService inventory
                = new InventoryService();


        /*
            Choose payment gateway.

            Today -> Razorpay

            Tomorrow ->
            PaymentService payment =
            new StripePayment();

            Notice OrderService doesn't change.
         */
        PaymentService payment
                = new RazorpayPayment();

        NotificationService notification
                = new NotificationService();


        /*
            STEP 2

            Inject dependencies into OrderService.

            This is Constructor Injection.
         */
        OrderService orderService
                = new OrderService(
                        inventory,
                        payment,
                        notification
                );


        /*
            STEP 3

            Create Order
         */
        Order order
                = new Order(101, 1499);


        /*
            STEP 4

            Checkout
         */
        orderService.checkout(order);
    }
}


/*
    DEPENDENCY INJECTION (Constructor Injection)

    Scenario:
    ---------
    Whenever a new user registers,
    we want to send them a notification.

    Instead of UserService creating EmailNotificationService itself,
    we inject (provide) the dependency from outside.

    UserService depends on the interface,
    not on a specific implementation.
 */
//----------------------------------------------------
// Step 1 : Create an Interface (Abstraction)
//----------------------------------------------------

/*
    UserService only knows that it needs something
    capable of sending notifications.

    It DOES NOT care whether it is Email, SMS,
    Push Notification, WhatsApp, etc.
 */
interface NotificationService {

    void send(String message);
}

//----------------------------------------------------
// Step 2 : Email Implementation
//----------------------------------------------------

/*
    One implementation of NotificationService.

    Tomorrow we can create:
    - SMSNotificationService
    - PushNotificationService
    - WhatsAppNotificationService

    UserService won't change.
 */
class EmailNotificationService implements NotificationService {

    @Override
    public void send(String message) {

        System.out.println("📧 Email Sent : " + message);
    }
}

//----------------------------------------------------
// Another Implementation
//----------------------------------------------------
class SMSNotificationService implements NotificationService {

    @Override
    public void send(String message) {

        System.out.println("📱 SMS Sent : " + message);
    }
}

//----------------------------------------------------
// Client Class
//----------------------------------------------------

/*
    UserService NEEDS NotificationService.

    Notice:

    There is NO

        new EmailNotificationService()

    inside this class.

    Therefore UserService is NOT tightly coupled
    to EmailNotificationService.
 */
class UserService {

    // Dependency
    private NotificationService notificationService;


    /*
        Constructor Injection

        Whoever creates UserService
        must provide a NotificationService.

        Spring Boot automatically calls this constructor.
     */
    public UserService(NotificationService notificationService) {

        this.notificationService = notificationService;
    }


    /*
        Business Logic

        Register user
        Send notification
     */
    public void register(String user) {

        System.out.println("User Registered : " + user);

        notificationService.send("Welcome " + user);
    }

}

//----------------------------------------------------
// Driver Class
//----------------------------------------------------
public class Main {

    public static void main(String[] args) {

        /*
            Create dependency.

            Today we choose Email.

            Tomorrow we can simply write

            NotificationService notification =
                    new SMSNotificationService();

            No change inside UserService.
         */
        NotificationService notification
                = new EmailNotificationService();


        /*
            Inject dependency using constructor.

            UserService is not creating EmailNotificationService.

            We are giving it from outside.

            This is Dependency Injection.
         */
        UserService service
                = new UserService(notification);


        /*
            Execute business logic.
         */
        service.register("Ashwary");
    }
}
