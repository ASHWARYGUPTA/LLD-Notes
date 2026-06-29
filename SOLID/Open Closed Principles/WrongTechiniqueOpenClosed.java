
class TaxCalculatorWrong {

    public double AmountAfterTax(double amount, String region) {
        if (region == "IND") {
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
