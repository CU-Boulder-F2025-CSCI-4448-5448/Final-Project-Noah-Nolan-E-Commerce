package order;

import com.application.products.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Catalog {
    private List<Product> products;
    private Catalog(){};
    private Double discount=0.0;

    public List<Product> getProducts() {
        if (products == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(products);//returns this for encapsulation
    }

    public Product getProduct(String ID){
        for(Product p : products){
            if(p.getId().toString().equals(ID))
                return p;
        }
        return null;
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

        public Catalog build() {
            Catalog catalog = new Catalog();

            catalog.products = this.products;
            catalog.discount = this.discount;
            return catalog;
        }
    }
}
