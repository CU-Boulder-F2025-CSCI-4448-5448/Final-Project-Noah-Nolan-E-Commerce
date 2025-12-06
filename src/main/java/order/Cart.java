package order;

import products.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static Cart cartInstance;
    private final List<Product> items = new ArrayList<>();

    private Cart() {}

    public static Cart getCart() {
        if(cartInstance == null) {
            cartInstance = new Cart();
        }

        return cartInstance;
    }

    public void add(Product product) {
        items.add(product);
    }

    public void replace(Product productOld, Product productNew) {
        if(items.contains(productOld)) {
            items.remove(productOld);
            items.add(productNew);
        }
    }

    public void remove(Product product) {
        items.remove(product);
    }
    public List<Product> getItems() {
        return items;
    }

    public double getTotal() {
        return items.stream().mapToDouble(Product::getPrice).sum();
    }

    public void clear() {
        items.clear();
    }
}
