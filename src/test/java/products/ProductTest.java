package products;

import order.Order;
import org.junit.jupiter.api.Test;
import paymentStrategy.PaymentPlan;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    ProductFactory factory = new ProductFactory();
    
    @Test
    void testProductCreation() {
        Product shrekMask = factory.createHat("Shrek Mask", 20.0);
        assertNotNull(shrekMask);
        assertEquals("Shrek Mask", shrekMask.getName());
        assertEquals(20.0, shrekMask.getPrice());
    }

    @Test
    void testOrder(){
        Product shrekMask = factory.createHat("Shrek Mask", 20.0);
        Product shrekTShirt = factory.createShirt("Shrek T-Shirt", 30.0);

        Order myOrder = new Order.Builder()
                .addProduct(shrekMask)
                .addProduct(shrekTShirt)
                .build();

        assertTrue(myOrder.getProducts().contains(shrekMask));
        assertTrue(myOrder.getProducts().contains(shrekTShirt));
        assertEquals(50.0, myOrder.getTotalPrice());
    }

    @Test
    void testCheckout(){
        Product shrekMask = factory.createHat("Shrek Mask", 20.0);
        Product shrekTShirt = factory.createShirt("Shrek T-Shirt", 30.0);
        Order myOrder = new Order.Builder()
                .addProduct(shrekMask)
                .addProduct(shrekTShirt)
                .addDiscount(0.10)//10% discount
                .build();
        myOrder.setPayStragety(new PaymentPlan());
        myOrder.checkout();
    }
}
