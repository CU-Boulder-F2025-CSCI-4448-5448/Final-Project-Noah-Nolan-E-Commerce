package order;

import products.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private UUID id;
    private List<Product> products;
    public Order(){};
    private Double discount=0.0;
    private PayStrategy payStragety;

    public List<Product> getProducts() {
        return products;
    }

    public Product getProduct(String ID){
        for(Product p : products){
            if(p.getId().toString().equals(ID))
                return p;
        }
        return null;
    }

    public UUID getId() {
        return id;
    }

    public Double getTotalPrice() {
        Double total = 0.0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total*(1-discount);
    }

    public static class Builder {
        private List<Product> products = new ArrayList<Product>();
        private Double discount = 0.0;
        public Builder() {}

        public Builder addProduct(Product product) {
            products.add(product);
            return this;
        }
        public Builder addProducts(List<Product> products) {

            this.products.addAll(products);
            return this;
        }

        public Builder addDiscount(Double discount){
            this.discount = discount;
            return this;
        }

        public Order build() {
            Order order = new Order();

            order.products = this.products;
            order.id = UUID.randomUUID();;
            order.discount = this.discount;
            return order;
        }
    }
}
