package productDecorator;

import products.Product;

public abstract class ProductDecorator extends Product {

    protected Product product;


    protected ProductDecorator(Product product) {
        super(product.getName(), product.getPrice(), product.getCategory());
        this.product = product;
    }
}
