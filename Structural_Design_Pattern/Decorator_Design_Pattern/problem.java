/*
 * Problem version:
 * Using inheritance for every topping combination causes a class explosion.
 * Each new option multiplies the number of subclasses, so adding features like
 * cheese, olives, stuffed crust, or future toppings becomes unmaintainable.
 */
class PlainPizza {
}

class CheezePizza extends PlainPizza {
}

class OlivePizza extends PlainPizza {
}

class StuffedPizza extends PlainPizza {
}

// Combination classes start appearing because inheritance cannot mix features dynamically.
class CheezeStuffedPizza extends CheezePizza {
}

class CheeseOlivePizza extends CheezePizza {
}

class CheeseOliveStuffedPizza extends CheezePizza {
}
//Explostion of classes

public class problem {

    // The file stays intentionally minimal because the anti-pattern is the hierarchy itself.
}
