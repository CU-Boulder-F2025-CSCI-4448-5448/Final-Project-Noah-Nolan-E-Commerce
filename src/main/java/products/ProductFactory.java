package products;

public class ProductFactory {

    public Product createHat(String productName, Double price) {
        return new Product(productName, price, Product.Categories.Hats);
    }
    public Product createShirt(String productName, Double price) {
        return new Product(productName, price, Product.Categories.Shirts);

    }
}

