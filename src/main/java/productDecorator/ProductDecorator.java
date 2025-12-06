package productDecorator;

import products.Product;

public abstract class ProductDecorator extends Product {

    protected Product product;
    private String productName;
    private double price;

    protected ProductDecorator(Product product) {
        super(product.getName(), product.getPrice(), product.getCategory());
        this.product = product;
    }
}
