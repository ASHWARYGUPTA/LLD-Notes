
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
        if (gatewayType == "razorpay") {
            return new RazorPayGateway();
        }
        if (gatewayType == "PayU") {
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
        if (countryCode == "India") { // Again Fails Not Dealing with Multiple Factorys
            PaymentGateway gateway = IndiaPayoutFactory.createPaymentGateway("razorpay");
            Invoice i1 = IndiaPayoutFactory.creatInvoice();
        }
        // else {
        //     PaymentGateway gateway = USAPayoutFactory.createPaymentGateway("razorpay");
        //     Invoice i1 = USAPayoutFactory.creatInvoice();
        // }
    }
}

public class Factory {

}
