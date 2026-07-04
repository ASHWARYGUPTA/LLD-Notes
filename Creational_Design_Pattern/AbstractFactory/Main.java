/*
 * Teaching note:
 * Abstract Factory scales when one business choice, such as region, must
 * create multiple matching objects together. The checkout code stays stable
 * because it asks one factory for a coherent family instead of branching for
 * each concrete class on its own.
 */


// This version works better because the client depends on a region-level factory
// instead of knowing every concrete gateway and invoice type. Abstract Factory
// groups related objects into one family, so switching from India to USA means
// changing the factory implementation rather than rewriting checkout logic.

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

    // One factory contract guarantees that related products are chosen together.
    PaymentGateway createPayemtGateway(String gatewayString);

    Invoice createInvoice();
}

class IndiaPayoutFactory implements RegionFactory {

    public PaymentGateway createPayemtGateway(String gatewayType) {
        if ("razorpay".equals(gatewayType)) {
            return new RazorPayGateway();
        }
        if ("PayU".equals(gatewayType)) {
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
        if ("Paypal".equals(gatewayType)) {
            return new PayPal();
        }
        if ("Stripe".equals(gatewayType)) {
            return new Stripe();
        }
        return new Stripe();
    }

    public Invoice createInvoice() {
        return new GSTInvoice();
    }
}

class CheckOutService {

    // The client stores abstractions, so the checkout flow does not care about country-specific classes.
    private PaymentGateway paymentGateway;
    private Invoice invoice;

    public CheckOutService(RegionFactory factory, String gatewayType) {
        // Swapping the factory swaps the full product family without rewriting checkout logic.
        this.paymentGateway = factory.createPayemtGateway(gatewayType);
        this.invoice = factory.createInvoice();
    }

    public void procesPayment() {

    }
}

public class Main {

    public static void main(String[] args) {
        // The caller chooses a family once, then the service receives matching products from that family.
        CheckOutService c = new CheckOutService(new IndiaPayoutFactory(), "razorpay");
    }
}
