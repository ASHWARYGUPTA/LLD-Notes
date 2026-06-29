
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

class Stripe implements PaymentGateway {

    @Override
    public void processPayment() {
        System.out.println("PayU Gateway");

    }
}

class PayPal implements PaymentGateway {

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

//Abstract For a Factory now
interface RegionFactory {

    PaymentGateway createPayemtGateway(String gatewayString);

    Invoice createInvoice();
}

class IndiaPayoutFactory implements RegionFactory {

    public PaymentGateway createPayemtGateway(String gatewayType) {
        if (gatewayType == "razorpay") {
            return new RazorPayGateway();
        }
        if (gatewayType == "PayU") {
            return new PayUGateway();
        }
        return new RazorPayGateway();
    }

    public Invoice createInvoice() {
        return new GSTInvoice();
    }
}

class USAPayoutFactory implements RegionFactory {

    public PaymentGateway createPayemtGateway(String gatewayType) {
        if (gatewayType == "Paypal") {
            return new PayPal();
        }
        if (gatewayType == "Stripe") {
            return new Stripe();
        }
        return new Stripe();
    }

    public Invoice createInvoice() {
        return new GSTInvoice();
    }
}

class CheckOutService {

    private PaymentGateway paymentGateway;
    private Invoice invoice;

    public CheckOutService(RegionFactory factory, String gatewayType) {
        this.paymentGateway = factory.createPayemtGateway(gatewayType);
        this.invoice = factory.createInvoice();
    }

    public void procesPayment() {

    }
}

public class Main {

    public static void main(String[] args) {
        CheckOutService c = new CheckOutService(new IndiaPayoutFactory(), "razorpay");
    }
}
