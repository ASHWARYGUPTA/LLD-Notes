
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
        BurgerMeal burgerMeal = new BurgerMeal("wheat", "veg");
    }
}
