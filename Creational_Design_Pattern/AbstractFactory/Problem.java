
interface PaymentGateway {

    void processPayment();
}

class RazorPayGateway implements PaymentGateway {

    @Override
    public void processPayment() {
        System.out.println("Razorpay Gateway");
    }
}

class PayUGateway implements PaymentGateway {

    @Override
    public void processPayment() {
        System.out.println("PayU Gateway");

    }
}

interface Invoice {

    void generateInvoice();
}

class GSTInvoice implements Invoice {

    @Override
    public void generateInvoice() {
        System.out.println("Generated Invoice");
    }
}

class CheckOutService { // Violates SRP 

    private String gatewayType;

    public CheckOutService(String gatewayStirng) {
        this.gatewayType = gatewayStirng;
    }

    public void checkOut() {
        if (gatewayType == "razorpay") {
            RazorPayGateway r1 = new RazorPayGateway();
        }
        if (gatewayType == "PayU") {
            PayUGateway r1 = new PayUGateway();
        }
        Invoice i1 = new GSTInvoice();
        i1.generateInvoice();
    }
}

public class Problem {

}
