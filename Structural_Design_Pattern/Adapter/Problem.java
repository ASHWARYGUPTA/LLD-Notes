
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
        //Now if we decide not to use the PayU Gateway and switch to Razorpay 
        //Now creating a Razorpay API using isn't something expected by client
    }
}
