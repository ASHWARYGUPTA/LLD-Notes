/*
 * Teaching note:
 * This file keeps the entire order workflow inside one mutable String field and
 * one centralized switch. That makes the example easy to start, but every new
 * state or rule forces edits in the same class and increases the chance of
 * invalid transitions or typo-driven bugs. The State pattern fixes that by
 * moving behavior into dedicated state objects, with the tradeoff of more types.
 */

//1. Breaks SRP
//2. Bussiness logic much are all at one
class Order {

    // String state names are easy to mistype and provide no compile-time help,
    // which is why state-heavy code often becomes fragile in this style.
    private String state;

    public Order() {
        this.state = "ORDER_PLACED";
    }

    public void cancelOrder() {
        // Even this rule is duplicated knowledge about state names instead of a
        // behavior owned by dedicated state objects.
        if (this.state.equals("ORDER_PLACED") || this.state.equals("ORDER_PREPARING")) {
            this.state = "CANCELLED";
            System.out.println("Order is cancelled");
        } else {
            System.out.println("Sorry !! Order cannot be cancelled");
        }
    }

    public void nextState() {
        // Every transition rule is centralized here. As the workflow grows,
        // this switch becomes the hotspot for changes and regressions.
        switch (this.state) {
            case "ORDER_PLACED":
                this.state = "ORDER_PREPARING";
                break;
            case "ORDER_PREPARING":
                this.state = "DELIVARY_PICKING";
                break;
            case "DELIVARY_PICKING":
                this.state = "OUT_OF_DELIVARY";
                break;
            case "OUT_OF_DELIVARY":
                this.state = "DELIVERED";
                break;
            default:
                throw new AssertionError();
        }
    }

    public String getState() {
        return this.state;
    }
}

public class Problem {

}
