/*
 * Adapter solution:
 * `CheckOutService` continues to depend on the app's `PaymentGateway` interface,
 * while `RazorpayAdapter` translates that interface into the third-party
 * `RazorpayAPI`. This works because the mismatch is isolated in one wrapper
 * instead of spreading vendor-specific details through client code.
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

    public void makePayment(String Orderid, double amount) {
        System.out.println("Paid Through Razorpay");
    }
}

//Adapter Class
class RazorpayAdapter implements PaymentGateway {

    private RazorpayAPI r1;

    public RazorpayAdapter() {
        this.r1 = new RazorpayAPI();
    }

    @Override
    public void pay(String orderId, double amount) {
        // The adapter converts the call expected by the client into the call shape
        // offered by the incompatible third-party API.
        r1.makePayment(orderId, amount);
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

public class Main {

    public static void main(String[] args) {
        CheckOutService c1 = new CheckOutService(new PayUGateway());
        c1.checkout("123", 240);
        // The checkout service could switch to `new RazorpayAdapter()` here without
        // learning anything about `RazorpayAPI` itself.
        //Now if we decide not to use the PayU Gateway and switch to Razorpay 
    }
}
