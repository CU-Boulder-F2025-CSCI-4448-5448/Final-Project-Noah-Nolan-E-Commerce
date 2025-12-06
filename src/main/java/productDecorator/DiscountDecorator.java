package productDecorator;

import com.application.products.Product;

public class DiscountDecorator extends  ProductDecorator{

    private double discount = 0;

    public DiscountDecorator(Product product, double discount) {
        super(product);
        this.discount = discount;
    }

    @Override
    public String getName() {
        return "Discounted " + product.getName();
    }

    @Override
    public Double getPrice() {
        return product.getPrice() * (1-discount);
    }
}
