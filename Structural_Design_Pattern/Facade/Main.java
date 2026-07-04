/*
 * Facade solution:
 * `MovieBookingService` gives the client one high-level entry point for a complex
 * booking workflow. The subsystem classes still do their individual jobs, but the
 * caller no longer needs to understand ordering, coordination, or how many
 * services are involved behind the scenes.
 */
// BookMyShow - Facade Design Pattern

class PaymentService {

    public void makePayment(String accountID, double amount) {
        System.out.println("Payment of " + amount + " successful by " + accountID);
    }
}

class SeatReservationService {

    public void reserveSeat(String movieId, String seatNumber) {
        System.out.println("Seat " + seatNumber + " reserved for movie " + movieId);
    }
}

class NotificationService {

    public void sendBookingConfirmation(String userEmail) {
        System.out.println("Booking confirmation sent to " + userEmail);
    }
}

class LoyaltyPoints {

    public void addPoints(String accountId, int points) {
        System.out.println(points + " loyalty points added to " + accountId);
    }
}

class TicketService {

    public void generateTicket(String movieId, String seatNumber) {
        System.out.println("Ticket Generated for movie " + movieId + " on seat " + seatNumber);
    }
}

class MovieBookingService {

    private PaymentService paymentService;
    private SeatReservationService seatReservationService;
    private NotificationService notificationService;
    private LoyaltyPoints loyaltyPoints;
    private TicketService ticketService;

    public MovieBookingService() {
        // The facade owns subsystem wiring so client code can stay small and focused.
        paymentService = new PaymentService();
        seatReservationService = new SeatReservationService();
        notificationService = new NotificationService();
        loyaltyPoints = new LoyaltyPoints();
        ticketService = new TicketService();
    }

    // Facade Method
    // Expand this using builder patterns
    public void bookMovieTicket(String accountId,
            String movieId,
            String seatNumber,
            String userEmail) {

        // One facade call preserves the business sequence in a single place.
        paymentService.makePayment(accountId, 350.0);

        seatReservationService.reserveSeat(movieId, seatNumber);

        ticketService.generateTicket(movieId, seatNumber);

        notificationService.sendBookingConfirmation(userEmail);

        loyaltyPoints.addPoints(accountId, 35);

        System.out.println("Movie Ticket Booked Successfully!");
    }
}

public class Main {

    public static void main(String[] args) {

        MovieBookingService bookingService = new MovieBookingService();

        bookingService.bookMovieTicket(
                "ACC101",
                "MOV123",
                "A10",
                "abc@gmail.com"
        );
    }
}
