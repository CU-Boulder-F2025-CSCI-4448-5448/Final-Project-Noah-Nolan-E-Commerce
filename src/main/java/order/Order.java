package order;

import products.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private UUID id;
    private List<Product> products;
    private Order(){};

    public List<Product> getProducts() {
        return products;
    }

    public Double getTotalPrice() {
        Double total = 0.0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }


    public static class Builder {
        private List<Product> products = new ArrayList<Product>();
        public Builder() {}

        public Builder addProduct(Product product) {
            products.add(product);
            return this;
        }

        public Order build() {
            Order order = new Order();

            order.products = this.products;
            order.id = UUID.randomUUID();;
            return order;
        }
    }
}
