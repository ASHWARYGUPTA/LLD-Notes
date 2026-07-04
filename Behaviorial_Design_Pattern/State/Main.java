/*
 * Teaching note:
 * This version models each order state as an object, so transition rules live
 * beside the state they belong to instead of inside one giant switch. That
 * makes the workflow easier to extend and reason about. The tradeoff is more
 * classes and the need to keep state transitions explicit and consistent.
 */

interface OrderState {

    void nextState(OrderContext context);

    void cancel(OrderContext context);

    String getStateName();
}

//---------------------------------------------
class OrderContext {

    private OrderState currentState;

    public OrderContext() {
        this.currentState = new OrderPlacedState();
    }

    public void setState(OrderState state) {
        this.currentState = state;
    }

    public void next() {
        // The context delegates behavior to the current state object, which is
        // why adding a new state does not require rewriting one centralized switch.
        currentState.nextState(this);
    }

    public void cancel() {
        // Cancellation rules also vary by state, so the context stays small and
        // the policy lives where it naturally belongs.
        currentState.cancel(this);
    }

    public String getCurrentState() {
        return currentState.getStateName();
    }
}

//---------------------------------------------
// ORDER PLACED
class OrderPlacedState implements OrderState {

    @Override
    public void nextState(OrderContext context) {
        // Transition rules live with the state that owns them, so this change
        // is local instead of being another case inside a central switch.
        context.setState(new OrderPreparingState());
        System.out.println("Order moved to ORDER_PREPARING");
    }

    @Override
    public void cancel(OrderContext context) {
        context.setState(new CancelledState());
        System.out.println("Order is cancelled");
    }

    @Override
    public String getStateName() {
        return "ORDER_PLACED";
    }
}

//---------------------------------------------
// ORDER PREPARING
class OrderPreparingState implements OrderState {

    @Override
    public void nextState(OrderContext context) {
        context.setState(new DeliveryPickingState());
        System.out.println("Order moved to DELIVERY_PICKING");
    }

    @Override
    public void cancel(OrderContext context) {
        context.setState(new CancelledState());
        System.out.println("Order is cancelled");
    }

    @Override
    public String getStateName() {
        return "ORDER_PREPARING";
    }
}

//---------------------------------------------
// DELIVERY PICKING
class DeliveryPickingState implements OrderState {

    @Override
    public void nextState(OrderContext context) {
        context.setState(new OutForDeliveryState());
        System.out.println("Order moved to OUT_FOR_DELIVERY");
    }

    @Override
    public void cancel(OrderContext context) {
        System.out.println("Sorry!! Order cannot be cancelled.");
    }

    @Override
    public String getStateName() {
        return "DELIVERY_PICKING";
    }
}

//---------------------------------------------
// OUT FOR DELIVERY
class OutForDeliveryState implements OrderState {

    @Override
    public void nextState(OrderContext context) {
        context.setState(new DeliveredState());
        System.out.println("Order Delivered");
    }

    @Override
    public void cancel(OrderContext context) {
        System.out.println("Sorry!! Order cannot be cancelled.");
    }

    @Override
    public String getStateName() {
        return "OUT_FOR_DELIVERY";
    }
}

//---------------------------------------------
// DELIVERED
class DeliveredState implements OrderState {

    @Override
    public void nextState(OrderContext context) {
        System.out.println("Order already delivered.");
    }

    @Override
    public void cancel(OrderContext context) {
        System.out.println("Sorry!! Order cannot be cancelled.");
    }

    @Override
    public String getStateName() {
        return "DELIVERED";
    }
}

//---------------------------------------------
// CANCELLED
class CancelledState implements OrderState {

    @Override
    public void nextState(OrderContext context) {
        System.out.println("Cancelled order cannot change state.");
    }

    @Override
    public void cancel(OrderContext context) {
        System.out.println("Order is already cancelled.");
    }

    @Override
    public String getStateName() {
        return "CANCELLED";
    }
}

//---------------------------------------------
public class Main {

    public static void main(String[] args) {

        OrderContext order = new OrderContext();

        System.out.println(order.getCurrentState());

        order.next();
        System.out.println(order.getCurrentState());

        order.next();
        System.out.println(order.getCurrentState());

        order.cancel(); // Not allowed

        order.next();
        System.out.println(order.getCurrentState());

        order.next();
        System.out.println(order.getCurrentState());

        order.cancel(); // Not allowed
    }
}
