/*
 * Teaching note:
 * This file follows the Open/Closed Principle by letting each tax rule live in
 * its own implementation behind one interface. New regions are added by
 * extension rather than by editing existing calculation code. The tradeoff is
 * a few more small classes, but behavior changes become much safer.
 */

interface TaxCalculatorRightWay {

    // High-level callers can depend on this contract without caring which
    // country's tax logic is currently plugged in.
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
