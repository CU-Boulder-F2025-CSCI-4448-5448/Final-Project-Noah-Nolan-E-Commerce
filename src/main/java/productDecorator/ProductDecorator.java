package productDecorator;

import products.Product;

public abstract class ProductDecorator extends Product {

    protected Product product;
    private String productName;
    private double price;

    protected ProductDecorator(Product product, String productName, double price) {
        super(product.getName(), product.getPrice(), product.getCategory());
        this.product = product;
    }
}
