/*
 * Teaching note:
 * Builder works well when an object has a few required inputs and many optional
 * ones. It scales because callers add options by name and in steps, instead of
 * relying on long constructors whose parameter lists keep growing.
 */

// The Builder pattern keeps required data upfront and lets optional choices grow
// without exploding constructor signatures. That scales better because callers
// can assemble readable configurations step by step instead of memorizing order.

import java.util.*;

class BurgerMeal {

    //Required
    private final String bun;
    private final String patty;

    //Optional
    private final boolean withCheese;
    private final String side;
    private final String drink;
    private final List<String> toppings;

    public BurgerMeal(BurgerBuilder builder) {
        this.bun = builder.bun;
        this.drink = builder.drink;
        this.side = builder.side;
        this.toppings = builder.toppings;
        this.patty = builder.patty;
        this.withCheese = builder.withCheese;
    }

    public static class BurgerBuilder {

        //Required
        private final String bun;
        private final String patty;

        //Optional
        private boolean withCheese;
        private String side;
        private String drink;
        private List<String> toppings;

        public BurgerBuilder(String bun, String patty) {
            this.bun = bun;
            this.patty = patty;
        }

        // Each fluent method adds one optional detail without forcing every caller to pass it.
        public BurgerBuilder addCheese(boolean addCheese) {
            if (addCheese) {
                this.withCheese = true;
            }
            return this;
        }

        public BurgerBuilder addSide(String side) {
            this.side = side;
            return this;
        }

        public BurgerBuilder addToppings(List<String> toppings) {
            this.toppings = toppings;
            return this;
        }

        public BurgerBuilder addDrik(String drink) {
            this.drink = drink;
            return this;
        }

        public BurgerMeal build() {
            // build() is the single handoff point where the final object is assembled.
            return new BurgerMeal(this);

        }
    }

}

public class Main {

    public static void main(String[] args) {
        // This teaching call is meant to show fluent assembly; the invocation syntax is still illustrative here.
        // The chained style shows why Builder stays readable as optional meal choices increase.
        BurgerMeal meal = new BurgerMeal.BurgerBuilder("wheat", "veg").addCheese(true).build();
    }
}
