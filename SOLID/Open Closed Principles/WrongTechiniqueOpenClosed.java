/*
 * Teaching note:
 * This file violates the Open/Closed Principle because one method keeps
 * accumulating region-specific branches. Every new tax rule requires editing
 * tested code in the same class, which raises regression risk. A polymorphic
 * design avoids that, with the tradeoff of introducing dedicated strategy-like classes.
 */

class TaxCalculatorWrong {

    public double AmountAfterTax(double amount, String region) {
        // Both rule selection and rule execution live in one method.
        // That means expansion happens by editing conditionals instead of by extension.
        if ("IND".equals(region)) {
            return amount + amount * 0.18;

        }
        return 0.0;
    }
}

public class WrongTechiniqueOpenClosed {

    public static void main(String[] args) {
        TaxCalculatorWrong t = new TaxCalculatorWrong();
        //t.AmountAfterTax(10001); //-> Fucks Up
    }
}
