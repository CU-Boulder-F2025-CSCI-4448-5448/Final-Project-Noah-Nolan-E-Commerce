package products;

import com.application.products.Product;
import com.application.products.ProductFactory;
import order.Catalog;
import org.junit.jupiter.api.Test;

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

        Catalog myCatalog = new Catalog.Builder()
                .addProduct(shrekMask)
                .addProduct(shrekTShirt)
                .build();

        assertTrue(myCatalog.getProducts().contains(shrekMask));
        assertTrue(myCatalog.getProducts().contains(shrekTShirt));
        assertEquals(50.0, myCatalog.getTotalPrice());
    }
}
