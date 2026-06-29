
import java.util.*;

interface CartItem {

    double getPrice();

    void dislpay(String indent);
}

class Product implements CartItem {

    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void dislpay(String indent) {
        System.out.println(indent + " Product : " + name + " - $ " + price);
    }
}

class ProductBundle implements CartItem {

    private String bundleName;
    private List<CartItem> prodList = new ArrayList<>();

    public ProductBundle(String bundleName) {
        this.bundleName = bundleName;
    }

    public void addProduct(Product p) {
        prodList.add(p);
    }

    public double getPrice() {
        double total = 0;
        for (CartItem p : prodList) {
            total += p.getPrice();
        }

        //Discount Logic 
        return total;
    }

    public void dislpay(String indent) {
        System.out.println(indent + " Bundle : " + bundleName);
        for (CartItem p : prodList) {
            p.dislpay("");
        }
    }
}

public class Main {

    public static void main(String[] args) {

        // Individual Products
        Product book = new Product("Atomic Habits", 499);
        Product phone = new Product("iPhone 15", 79999);
        Product earbuds = new Product("AirPods", 15999);
        Product charger = new Product("20W Charger", 1999);

        // iPhone Essentials Bundle
        ProductBundle iphoneCombo = new ProductBundle("iPhone Essentials Combo");
        iphoneCombo.addProduct(phone);
        iphoneCombo.addProduct(earbuds);
        iphoneCombo.addProduct(charger);

        // School Kit Bundle
        ProductBundle schoolKit = new ProductBundle("Back to School Kit");
        schoolKit.addProduct(new Product("Notebook Pack", 249));
        schoolKit.addProduct(new Product("Pen Set", 99));
        schoolKit.addProduct(new Product("Highlighter", 149));

        // Mega Bundle (Bundle inside another Bundle)
        ProductBundle megaBundle = new ProductBundle("Festival Offer");
        megaBundle.addProduct(book);
        // megaBundle.prodList.add(iphoneCombo);   // or create an addItem() method
        // megaBundle.prodList.add(schoolKit);

        // Shopping Cart
        List<CartItem> cart = new ArrayList<>();
        cart.add(book);
        cart.add(iphoneCombo);
        cart.add(schoolKit);
        cart.add(megaBundle);

        // Display Cart
        System.out.println("===== Your Shopping Cart =====");

        double total = 0;
        // Now Doesn't need to check 
        for (CartItem item : cart) {
            item.dislpay("");
            total += item.getPrice();
            System.out.println();
        }

        System.out.println("------------------------------");
        System.out.println("Total Cart Value = $" + total);
    }
}
