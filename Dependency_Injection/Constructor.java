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
public class Constructor {

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
