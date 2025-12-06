package productDecorator;

import com.application.products.Product;

public class GiftWrappedDecorator extends ProductDecorator{

    public GiftWrappedDecorator(Product product) {
        super(product);
    }

    @Override
    public String getName() {
        return product.getName()+ " (Gift Wrapped)";
    }

}
