package products;

import java.util.UUID;

public class Product {
    public enum Categories{
        Hats,
        Shirts,
        Socks,
        Jackets,
        Pants,
        MiscellaneousItem,
        Shoes
    }
    private final UUID id;
    protected String name;
    private double price;
    private Categories category;


    public Product(String name, double price, Categories category) {
        this.id = UUID.randomUUID();;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getId() {
        return id.toString();
    }
    public String getName() {
        return name;
    }
    public Double getPrice() {
        return price;
    }
    public Categories getCategory() {
        return category;
    }

}
