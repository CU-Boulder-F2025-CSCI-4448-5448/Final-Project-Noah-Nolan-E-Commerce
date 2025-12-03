package order;

import paymentStrategy.PayStragety;
import products.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private UUID id;
    private List<Product> products;
    private Order(){};
    private Double discount=0.0;
    private PayStragety payStragety;

    public List<Product> getProducts() {
        return products;
    }

    public Double getTotalPrice() {
        Double total = 0.0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total*(1-discount);
    }

    public void setPayStragety(PayStragety payStragety) {
        this.payStragety = payStragety;
    }

    public void checkout() {
        Double totalPrice = getTotalPrice();
        if (payStragety == null) {
            System.out.println("No payment method selected!");
        } else {
            payStragety.pay(totalPrice);
        }
    }


    public static class Builder {
        private List<Product> products = new ArrayList<Product>();
        private Double discount = 0.0;
        public Builder() {}

        public Builder addProduct(Product product) {
            products.add(product);
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
