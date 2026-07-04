/*
 * Problem version:
 * `CheckOutService` expects the `PaymentGateway` contract, but a third-party API
 * like `RazorpayAPI` exposes a different method shape. Without an adapter, the
 * client must either rewrite checkout code or leak vendor-specific calls into code
 * that was supposed to depend only on the app's own gateway abstraction.
 */
interface PaymentGateway {

    void pay(String orderId, double amount);
}

class PayUGateway implements PaymentGateway {

    @Override
    public void pay(String orderId, double amount) {
        System.out.println("Paid from PayU");
    }
}

class RazorpayAPI {

    // This class is useful, but it does not match the interface the checkout flow expects.
    public void makePayment(String Orderid, double amount) {
        System.out.println("Paid Through Razorpay");
    }
}

class CheckOutService {

    private PaymentGateway paymentGateway;

    public CheckOutService(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public void checkout(String orderId, double amount) {
        paymentGateway.pay(orderId, amount);
    }
}

public class Problem {

    public static void main(String[] args) {
        CheckOutService c1 = new CheckOutService(new PayUGateway());
        c1.checkout("123", 240);
        // Swapping to Razorpay is not a drop-in replacement here because its API
        // shape differs from the `PaymentGateway` contract used by the client.
        //Now if we decide not to use the PayU Gateway and switch to Razorpay 
        //Now creating a Razorpay API using isn't something expected by client
    }
}
