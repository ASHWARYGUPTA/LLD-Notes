
interface TaxCalculatorRightWay {

    double amountAfterTax(double amount);
}

class IndianTaxService implements TaxCalculatorRightWay {

    public double amountAfterTax(double amount) {
        return amount + amount * 0.18;
    }
}

class USTax implements TaxCalculatorRightWay {

    public double amountAfterTax(double amount) {
        return amount + amount * 0.18;
    }
}

public class RightWay {

}
