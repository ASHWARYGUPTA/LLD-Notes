/*
 * Teaching note:
 * This problem version fails to scale because business logic constructs each
 * concrete gateway and invoice directly. Every new provider or region forces
 * edits in checkout code, so creation concerns spread through the workflow.
 */


// This version shows why object creation can become a maintenance burden when the
// checkout flow decides every concrete dependency by itself. The client is now
// responsible for choosing both the payment gateway family and the invoice
// family, so adding a new country or provider forces edits in business logic.

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
        // The checkout flow is tightly coupled to every concrete payment class it might use.
        if ("razorpay".equals(gatewayType)) {
            RazorPayGateway r1 = new RazorPayGateway();
        }
        if ("PayU".equals(gatewayType)) {
            PayUGateway r1 = new PayUGateway();
        }
        // Even related objects like invoices are still created directly in the client.
        Invoice i1 = new GSTInvoice();
        i1.generateInvoice();
    }
}

// The wrapper class is empty because the object-creation coupling above is the real lesson.
public class Problem {

}
