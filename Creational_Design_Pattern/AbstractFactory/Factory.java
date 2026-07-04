/*
 * Teaching note:
 * A single concrete factory removes some duplication, but this version still
 * does not scale across multiple regions because the checkout flow must know
 * which country-specific factory to call. That remaining branching is what the
 * full Abstract Factory solves.
 */


// This file demonstrates the limit of using only a simple factory. Creation is
// centralized for one region, but the checkout flow still needs region-specific
// branching to decide which factory to call. The moment related products vary
// together by country, a higher-level factory abstraction becomes necessary.

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

class IndiaPayoutFactory {

    public static PaymentGateway createPaymentGateway(String gatewayType) {
        if ("razorpay".equals(gatewayType)) {
            return new RazorPayGateway();
        }
        if ("PayU".equals(gatewayType)) {
            return new PayUGateway();
        }
        return new RazorPayGateway();
    }

    public static Invoice creatInvoice() {
        return new GSTInvoice();
    }
}

class CheckOutService { // If mutiple countries will fail

    private String gatewayType;
    private String countryCode;

    public CheckOutService(String gatewayType, String countryCode) {
        this.gatewayType = gatewayType;
        this.countryCode = countryCode;
    }

    public void checkOut() {
        // A country branch in the client still means checkout must know every region-specific factory.
        if ("India".equals(countryCode)) { // Again Fails Not Dealing with Multiple Factorys
            PaymentGateway gateway = IndiaPayoutFactory.createPaymentGateway(gatewayType);
            Invoice i1 = IndiaPayoutFactory.creatInvoice();
        }
        // else {
        //     PaymentGateway gateway = USAPayoutFactory.createPaymentGateway("razorpay");
        //     Invoice i1 = USAPayoutFactory.creatInvoice();
        // }
    }
}

// The outer class is just a holder; the scaling lesson is in the factory usage above.
public class Factory {

}
