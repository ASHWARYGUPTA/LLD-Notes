//BookMyShow

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
        System.out.println("Booking confirmation by " + userEmail);
    }
}

class LoyaltyPoints {

    public void addPoints(String accountId, int points) {
        System.out.println(points + " loyalty added to " + accountId);
    }
}

class TicketService {

    public void generateTicket(String movieId, String seatNumber) {
        System.out.println("Ticket Generated for " + movieId + " on seat " + seatNumber);
    }
}

public class Problem {

    public static void main(String[] args) {
        PaymentService p = new PaymentService();
        p.makePayment("user123", 200);

        SeatReservationService s = new SeatReservationService();
        s.reserveSeat("123", "123");

        NotificationService ns = new NotificationService();
        ns.sendBookingConfirmation("user123@gmail.com");
        LoyaltyPoints lp = new LoyaltyPoints();
        lp.addPoints("user123", 10);
        TicketService tp = new TicketService();
        tp.generateTicket("123", "123");
    }
}
