
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
            return new BurgerMeal(this);

        }
    }

}

public class Main {

    public static void main(String[] args) {
        BurgerMeal meal = BurgerMeal.BurgerBuilder("wheat", "veg").addCheese(true).build();
    }
}
