/*
 * Teaching note:
 * This file shows Visitor in its "good fit" scenario: the item hierarchy is
 * relatively stable, but new operations like invoices, shipping, tax, or
 * reporting may keep appearing. Visitor keeps those operations out of the item
 * classes. The tradeoff is that adding a brand-new item type forces all
 * visitors to grow another visit method.
 */

/*
 * Teaching note:
 * Visitor is strongest when the item hierarchy is stable but the operations
 * applied to those items keep growing. This example adds invoice and shipping
 * behavior without editing product classes, but the tradeoff is that adding a
 * new item type would force every visitor to change.
 */
/*
==============================================================================
                            VISITOR PATTERN

Problem:
--------
Suppose we have different kinds of items.

PhysicalProduct
DigitalProduct
GiftCard

Today we want to print invoices.
Tomorrow we want shipping cost.
Later we want tax calculation.
Later discount calculation.
Later inventory report.

Without Visitor Pattern we'd write:

if(item instanceof PhysicalProduct)
...
else if(item instanceof DigitalProduct)
...
else if(item instanceof GiftCard)
...

everywhere.

Visitor Pattern removes all these instanceof checks.

==============================================================================
 */


 /*=============================================================================
    ELEMENT

    Every item MUST implement accept().

    Think of accept() as saying:

    "I know what type I am.
     Give me a visitor and I'll call the correct visit() method."

=============================================================================*/
interface Item {

    /*
     * Accepts any visitor.
     *
     * Every concrete item implements this differently.
     */
    void accept(ItemVisitor visitor);
}


/*=============================================================================
    CONCRETE ELEMENT 1

    Represents a physical product.

=============================================================================*/
class PhysicalProduct implements Item {

    private String name;
    private double weight;

    public PhysicalProduct(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    /*
     * THIS IS THE MOST IMPORTANT METHOD.
     *
     * this = current object
     *
     * If current object is PhysicalProduct,
     * then this is PhysicalProduct.
     *
     * So this automatically calls
     *
     * visitor.visit(PhysicalProduct)
     *
     * No instanceof required.
     */
    @Override
    public void accept(ItemVisitor visitor) {
        visitor.visit(this);
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }
}


/*=============================================================================
    CONCRETE ELEMENT 2
=============================================================================*/
class DigitalProduct implements Item {

    private String name;
    private int downloadSizeInMB;

    public DigitalProduct(String name, int downloadSizeInMB) {
        this.name = name;
        this.downloadSizeInMB = downloadSizeInMB;
    }

    /*
     * Since "this" is DigitalProduct,
     * Java automatically calls
     *
     * visit(DigitalProduct)
     */
    @Override
    public void accept(ItemVisitor visitor) {
        visitor.visit(this);
    }

    public String getName() {
        return name;
    }

    public int getDownloadSizeInMB() {
        return downloadSizeInMB;
    }
}


/*=============================================================================
    CONCRETE ELEMENT 3
=============================================================================*/
class GiftCard implements Item {

    private String code;
    private double amount;

    public GiftCard(String code, double amount) {
        this.code = code;
        this.amount = amount;
    }

    @Override
    public void accept(ItemVisitor visitor) {
        visitor.visit(this);
    }

    public String getCode() {
        return code;
    }

    public double getAmount() {
        return amount;
    }
}


/*=============================================================================
    VISITOR

    Every operation on Items is declared here.

    Notice there are NO business implementations here.

    This is only a contract.

=============================================================================*/
interface ItemVisitor {

    /*
     * Different overloaded methods.

     * Which one gets called depends on
     * which object called accept().
     */
    void visit(PhysicalProduct product);

    void visit(DigitalProduct product);

    void visit(GiftCard giftCard);
}


/*=============================================================================
    CONCRETE VISITOR 1

    Responsibility:
    Print invoice.

    Nothing else.

=============================================================================*/
class InvoiceVisitor implements ItemVisitor {

    @Override
    public void visit(PhysicalProduct product) {

        System.out.println("========== Physical Product Invoice ==========");
        System.out.println("Product : " + product.getName());
        System.out.println("Weight  : " + product.getWeight() + " kg");
        System.out.println();
    }

    @Override
    public void visit(DigitalProduct product) {

        System.out.println("========== Digital Product Invoice ==========");
        System.out.println("Product : " + product.getName());
        System.out.println("Download Size : "
                + product.getDownloadSizeInMB() + " MB");
        System.out.println();
    }

    @Override
    public void visit(GiftCard giftCard) {

        System.out.println("========== Gift Card Invoice ==========");
        System.out.println("Code : " + giftCard.getCode());
        System.out.println("Balance : " + giftCard.getAmount());
        System.out.println();
    }
}


/*=============================================================================
    CONCRETE VISITOR 2

    Calculates shipping cost.

    Notice:

    We NEVER modified

        PhysicalProduct
        DigitalProduct
        GiftCard

    We simply added another visitor.

=============================================================================*/
class ShippingCostVisitor implements ItemVisitor {

    @Override
    public void visit(PhysicalProduct product) {

        /*
         * Example shipping logic.
         */
        double shipping = product.getWeight() * 100;

        System.out.println(
                product.getName()
                + " Shipping Cost = ₹"
                + shipping);
    }

    @Override
    public void visit(DigitalProduct product) {

        /*
         * Digital products have no shipping.
         */
        System.out.println(
                product.getName()
                + " Shipping Cost = ₹0");
    }

    @Override
    public void visit(GiftCard giftCard) {

        /*
         * Gift cards are virtual.
         */
        System.out.println(
                giftCard.getCode()
                + " Shipping Cost = ₹0");
    }
}


/*=============================================================================
    CLIENT

    Client doesn't know concrete types.

    It simply asks every item to accept the visitor.

=============================================================================*/
public class Main {

    public static void main(String[] args) {

        Item[] cart = {
            new PhysicalProduct("Laptop", 2.5),
            new DigitalProduct("Java Course", 1500),
            new GiftCard("GC100", 1000)

        };

        /*
        ==========================================================
                    FIRST OPERATION
                    PRINT INVOICE
        ==========================================================
         */
        ItemVisitor invoiceVisitor = new InvoiceVisitor();

        for (Item item : cart) {

            /*
             * Runtime Flow:
             *
             * item.accept(invoiceVisitor)
             *
             * ↓
             *
             * Suppose item is PhysicalProduct
             *
             * ↓
             *
             * PhysicalProduct.accept()
             *
             * ↓
             *
             * visitor.visit(this)
             *
             * ↓
             *
             * InvoiceVisitor.visit(PhysicalProduct)
             *
             * Automatically selected.
             */
            item.accept(invoiceVisitor);
        }

        System.out.println("----------------------------------");

        /*
        ==========================================================
                SECOND OPERATION

                SHIPPING COST

                Existing classes remain unchanged.
        ==========================================================
         */
        ItemVisitor shippingVisitor
                = new ShippingCostVisitor();

        for (Item item : cart) {

            item.accept(shippingVisitor);
        }
    }
} /*
==============================================================================
                        DOUBLE DISPATCH
==============================================================================

Normal Java uses SINGLE DISPATCH.

Example

Animal a = new Dog();

a.sound();

Runtime checks only ONE object.

Dog

and executes Dog.sound().

------------------------------------------------------------

Visitor Pattern uses TWO runtime decisions.

Step 1

item.accept(visitor)

Runtime checks

Is item PhysicalProduct?

DigitalProduct?

GiftCard?

Correct accept() executes.

------------------------------------------------------------

Step 2

Inside accept()

visitor.visit(this)

Now runtime selects

visit(PhysicalProduct)

visit(DigitalProduct)

visit(GiftCard)

Correct overloaded visit() executes.

Two runtime selections

↓

DOUBLE DISPATCH

==============================================================================



WITHOUT VISITOR

for(Item item : cart){

    if(item instanceof PhysicalProduct){

    }

    else if(item instanceof DigitalProduct){

    }

    else if(item instanceof GiftCard){

    }

}

Every new operation repeats this code.

==============================================================================



WITH VISITOR

for(Item item : cart){

    item.accept(visitor);

}

That's it.

==============================================================================



ADVANTAGES

✔ No instanceof

✔ No switch statements

✔ Open Closed Principle

✔ Easy to add new operations

✔ Related logic stays together

Invoice logic → InvoiceVisitor

Shipping logic → ShippingCostVisitor

Tax logic → TaxVisitor

Discount logic → DiscountVisitor



==============================================================================



DISADVANTAGE

Suppose tomorrow we introduce

SubscriptionProduct

Then

ItemVisitor must change

interface ItemVisitor{

    visit(PhysicalProduct)

    visit(DigitalProduct)

    visit(GiftCard)

    visit(SubscriptionProduct) // NEW

}

Now EVERY visitor must implement it.

InvoiceVisitor

ShippingVisitor

TaxVisitor

DiscountVisitor

...

Hence,

Visitor Pattern is best when

✔ Object hierarchy changes rarely.

✔ New operations are added frequently.

This is the classic tradeoff of the Visitor Pattern.

==============================================================================
*/
