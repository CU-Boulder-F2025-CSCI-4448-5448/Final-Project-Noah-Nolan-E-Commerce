package products;

public class ProductFactory {

    public Product createHat(String productName, Double price) {
        return new Product(productName, price, Product.Categories.Hats);
    }
    public Product createShirt(String productName, Double price) {
        return new Product(productName, price, Product.Categories.Shirts);
    }
    public Product createMiscellaneousItem(String productName, Double price) {
        return new Product(productName, price, Product.Categories.MiscellaneousItem);
    }
    public Product createSocks(String productName, Double price) {
        return new Product(productName, price, Product.Categories.Socks);
    }
    public Product createJacket(String productName, Double price) {
        return new Product(productName, price, Product.Categories.Jackets);
    }
}

