package order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.application.products.Product;

import static org.junit.jupiter.api.Assertions.*;

public class CartTest {

    @BeforeEach
    void resetCart() {
        Cart.getCart().clear();   // ensure a clean cart before each test
    }

    @Test
    void testAddItem() {
        Cart cart = Cart.getCart();
        Product p = new Product("Test Item", 10.0, Product.Categories.Shirts);
        cart.add(p);
        assertEquals(1, cart.getItems().size());
        assertTrue(cart.getItems().contains(p));
    }

    @Test
    void testRemoveItem() {
        Cart cart = Cart.getCart();
        Product p = new Product("Test Item", 10.0, Product.Categories.Shirts);
        cart.add(p);
        cart.remove(p);
        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    void testReplaceItem() {
        Cart cart = Cart.getCart();
        Product old = new Product("1", 5.0, Product.Categories.Shirts);
        Product lessOld = new Product("2", 7.0, Product.Categories.Shirts);
        cart.add(old);
        cart.replace(old, lessOld);
        assertTrue(cart.getItems().contains(lessOld));
        assertEquals(1, cart.getItems().size());
    }

    @Test
    void testGetTotal() {
        Cart cart = Cart.getCart();
        Product a = new Product("A", 10.0, Product.Categories.Shirts);
        Product b = new Product("B", 5.5, Product.Categories.Shirts);
        cart.add(a);
        cart.add(b);
        assertEquals(15.5, cart.getTotal());
    }

    @Test
    void testClearCart() {
        Cart cart = Cart.getCart();
        cart.add(new Product("X", 9.0, Product.Categories.Shirts));
        cart.clear();
        assertEquals(0, cart.getItems().size());
    }

    @Test
    void testSingletonCart() {
        Cart c1 = Cart.getCart();
        Cart c2 = Cart.getCart();
        assertSame(c1, c2);
    }
}