/*
 * Teaching note:
 * The constructor-based approach starts simply but fails to scale as optional
 * meal choices increase. Call sites become harder to read, overloads multiply,
 * and adding one more option can ripple through many constructor signatures.
 */


// This file captures the "telescoping constructor" problem. A constructor works
// for a tiny object, but once optional fields keep growing, the caller must pass
// many parameters in one shot and the class becomes harder to read, validate,
// and extend without breaking call sites.

//---------------
//Bun
//Patty
class BurgerMeal {

    private String Bun;
    private String Patty;

    public BurgerMeal(String bun, String patty) {
        this.Bun = bun;
        this.Patty = patty;
    }

    //When you scale -> Constructor Parameters Increases
    // public BurgerMeal(String bun, String patty, String sides, List<String> toppings) {
    //     this.Bun = bun;
    //     this.Patty = patty;
    //     this.sides = sides;
    //     this.toppings = toppings;
    // }
}

public class Problem {

    public static void main(String[] args) {
        // This call site is readable only while the object stays tiny; more options would quickly make it noisy.
        BurgerMeal burgerMeal = new BurgerMeal("wheat", "veg");
    }
}
