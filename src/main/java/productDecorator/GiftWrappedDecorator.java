package productDecorator;

import products.Product;

public class GiftWrappedDecorator extends ProductDecorator{

    public GiftWrappedDecorator(Product product) {
        super(product, product.getName(), product.getPrice());
    }

    @Override
    public String getName() {
        return product.getName() + " (Gift Wrapped)";
    }

}
