
interface Pizza {

    String getDescription();

    double getCost();
}

//Decorator Class
abstract class PizzaDecorator implements Pizza {

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
        return pizza.getDescription() + " Extra Cheese";
    }

    public double getCost() {
        return pizza.getCost() + 40.0;
    }

}

public class Main {

    public static void main(String[] args) {
        Pizza pizza = new ExtraCheese(new MargerataPizza());
        System.out.println(pizza.getCost());

    }
}
