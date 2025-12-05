package productDecorator;

import products.Product;

public class LimitedEditionDecorator extends ProductDecorator{
    private double limitedEditionAddOn = 0;

    protected LimitedEditionDecorator(Product product, String productName, double price, double limitedEditionAddOn) {
        super(product, productName, price);
        this.limitedEditionAddOn = limitedEditionAddOn;
    }

    @Override
    public String getName() {
        return "Limited Edition " + product.getName();
    }

    @Override
    public Double getPrice() {
        return product.getPrice() * (1+limitedEditionAddOn);
    }
}
