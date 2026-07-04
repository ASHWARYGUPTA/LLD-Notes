/*
 * Decorator solution:
 * Instead of creating one subclass per pizza combination, each extra feature
 * wraps another `Pizza` and adds behavior at runtime. That is why new toppings
 * compose cleanly without changing existing pizza classes or exploding the
 * inheritance tree.
 */
interface Pizza {

    String getDescription();

    double getCost();
}

//Decorator Class
abstract class PizzaDecorator implements Pizza {

    // Every decorator forwards to another pizza and layers one concern on top.
    protected Pizza pizza;

    public PizzaDecorator(Pizza pizza) {
        this.pizza = pizza;
    }
}

class MargerataPizza implements Pizza {

    public String getDescription() {
        return "Margerita";
    }

    public double getCost() {
        return 200.0;
    }
}

class ExtraCheese extends PizzaDecorator {

    public ExtraCheese(Pizza pizza) {
        super(pizza);
    }

    public String getDescription() {
        // Decoration is additive, so the wrapper extends the base description.
        return pizza.getDescription() + " Extra Cheese";
    }

    public double getCost() {
        // Cost is also built incrementally instead of hardcoding a separate subclass price.
        return pizza.getCost() + 40.0;
    }

}

public class Main {

    public static void main(String[] args) {
        // This assembles "margarita + extra cheese" at runtime with no dedicated
        // `CheeseMargaritaPizza` subclass.
        Pizza pizza = new ExtraCheese(new MargerataPizza());
        System.out.println(pizza.getCost());

    }
}
