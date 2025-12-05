package productDecorator;

import products.Product;

public class GiftWrappedDecorator extends ProductDecorator{

    protected GiftWrappedDecorator(Product product, String productName, double price) {
        super(product, productName, price);
    }

    @Override
    public String getName() {
        return product.getName() + " (Gift Wrapped)";
    }

}
