
import java.util.*;

class Product {

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
}

class ProductBundle {

    private String bundleName;
    private List<Product> prodList = new ArrayList<>();

    public ProductBundle(String bundleName, List<Product> prodList) {
        this.bundleName = bundleName;
        this.prodList = prodList;
    }

    public void addProduct(Product p) {
        prodList.add(p);
    }

    public double getPrice() {
        double total = 0;
        for (Product p : prodList) {
            total += p.getPrice();
        }

        //Discount Logic 
        return total;
    }

    public void dislpay(String indent) {
        System.out.println(indent + " Bundle : " + bundleName);
        for (Product p : prodList) {
            p.getName();
        }
    }
}

public class Problem {

    public static void main(String[] args) {

        // Individual items
        Product book = new Product("Atomic Habits", 499);
        Product phone = new Product("iPhone 15", 79999);
        Product earbuds = new Product("AirPods", 15999);
        Product charger = new Product("20W Charger", 1999);

        // Bundle: iPhone Combo
        ProductBundle iphoneCombo = new ProductBundle(
                "iPhone Essentials Combo",
                new ArrayList<>()
        );
        iphoneCombo.addProduct(phone);
        iphoneCombo.addProduct(earbuds);
        iphoneCombo.addProduct(charger);

        // Bundle: School Kit
        ProductBundle schoolKit = new ProductBundle(
                "Back to School Kit",
                new ArrayList<>()
        );
        schoolKit.addProduct(new Product("Notebook Pack", 249));
        schoolKit.addProduct(new Product("Pen Set", 99));
        schoolKit.addProduct(new Product("Highlighter", 149));

        // Add to cart
        List<Object> cart = new ArrayList<>(); //Problem
        cart.add(book);
        cart.add(iphoneCombo);
        cart.add(schoolKit);

        // Display cart
        System.out.println("=== Your Cart (without Composite Pattern) ===");

        double total = 0;

        for (Object item : cart) {
            //Should Not have this
            if (item instanceof Product) {
                Product product = (Product) item;

                System.out.println(product.getName() + " - ₹" + product.getPrice());
                total += product.getPrice();

            } else if (item instanceof ProductBundle) {
                ProductBundle bundle = (ProductBundle) item;

                bundle.dislpay("  ");
                System.out.println("Bundle Price: ₹" + bundle.getPrice());

                total += bundle.getPrice();
            }
        }

        System.out.println("---------------------------");
        System.out.println("Total Cart Value = ₹" + total);
    }
}
