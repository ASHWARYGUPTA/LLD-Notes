/*
 * Teaching note:
 * This anti-pattern sketch shows why Visitor exists: once client code starts
 * branching on concrete product types, every new operation and every new type
 * tends to trigger more scattered conditionals. Visitor centralizes each
 * operation instead. This file is also intentionally incomplete as a teaching
 * snippet, so comments are added without trying to make the example compile.
 */

/*
 * Teaching note:
 * This file captures the usual pre-Visitor approach: product classes absorb
 * one operation after another, and clients fall back to instanceof checks to
 * branch on concrete types. That fails because adding either a new operation
 * or a new product forces edits in many places. Visitor centralizes each
 * operation instead, with the tradeoff that visitor interfaces grow when the
 * element hierarchy changes.
 */

// PhysicalProduct represents one concrete product type.
// Each time a new operation (e.g., tax calculation, export, discount, warranty)
// is needed, this class must be modified, violating the Open/Closed Principle.
interface Item {
}

class PhysicalProduct implements Item {

    public void printInvoice() {
        // Invoice printing logic
    }
}

// Another concrete product.
// Business operations are distributed across multiple product classes instead
// of being separated into independent visitor objects.
class DigitalProduct implements Item {

    public void printInvoice() {
        // Invoice printing logic
    }
}

// Another product type.
// If more operations are introduced later, this class must also be modified.
class GiftCard implements Item {

    public void printInvoice() {
        // Invoice printing logic
    }
}

public class Problem {

    public static void main(String[] args) {
        Item[] cart = {
            new PhysicalProduct(),
            new DigitalProduct(),
            new GiftCard()
        };

        // This demo is intentionally sketch-like rather than complete code:
        // `Item` and `cart` are referenced to show the shape of the branching problem.
        // Assume cart contains different product objects.
        // The following code checks the runtime type of every object.
        // This is a code smell because the client knows about every concrete class.
        // Whenever a new product type is added (e.g., SubscriptionProduct),
        // this code must also be updated, violating the Open/Closed Principle.
        for (Item item : cart) {

            // Explicit type checking creates tight coupling with concrete classes.
            // Visitor Pattern removes these instanceof checks using double dispatch.
            if (item instanceof PhysicalProduct) {
                // Do physical product specific work

            } else if (item instanceof DigitalProduct) {
                // Do digital product specific work

                // GiftCard is not even handled here.
                // Forgetting to update every instanceof chain is a common bug.
                // As the number of product types grows,
                // these conditionals become larger and harder to maintain.
            }
        }

        /*
         * Problems solved by Visitor Pattern:
         *
         * 1. Avoids instanceof chains.
         * 2. Removes long if-else/switch statements based on object type.
         * 3. New operations (Invoice, Tax, Shipping, Export, Discount, etc.)
         *    can be added without modifying existing product classes.
         * 4. Client code no longer depends on concrete product classes.
         * 5. Uses double dispatch so the correct operation is selected automatically.
         */
    }
}
