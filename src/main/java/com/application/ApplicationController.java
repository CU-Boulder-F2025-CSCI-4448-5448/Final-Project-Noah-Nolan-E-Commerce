package com.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import order.Cart;
import order.Catalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import paymentStrategy.Cash;
import paymentStrategy.CreditCard;
import paymentStrategy.PaymentPlan;
import paymentStrategy.PaymentStrategy;
import productDecorator.DiscountDecorator;
import productDecorator.GiftWrappedDecorator;
import com.application.products.Product;
import com.application.products.ProductFactory;

@Controller
public class ApplicationController {
    private static final String BANNER_MESSAGE = "Hello and Welcome to the Shrek Merch Shop. Use code \'FARQUAAD\' for 25% off all hats!";
    private static final String DISCOUNT_CODE = "FARQUAAD";
    private static final Double DISCOUNT_AMOUNT = 0.25;
    private final Product.Categories DISCOUNT_CATEGORY = Product.Categories.Hats;
    private final ProductFactory productFactory;
    Catalog catalog;

    @Autowired
    public ApplicationController(ProductFactory productFactory) {
        this.productFactory = productFactory;
    }

    @GetMapping("/")
    public String index(Model model) {
        catalog = new Catalog.Builder()
                .addProduct(productFactory.createHat("Shrek Mask", 20.00))
                .addProduct(productFactory.createHat("Shrek Ears", 5.00))
                .addProduct(productFactory.createShirt("Shrek T-Shirt", 30.00))
                .addProduct(productFactory.createJacket("Leather Vest", 150.00))
                .addProduct(productFactory.createMiscellaneousItem("Donkey Plushie", 12.99))
                .addProduct(productFactory.createSock("Green Sock", 9.99))
                .build();


        model.addAttribute("message", BANNER_MESSAGE);
        model.addAttribute("products", catalog.getProducts());
        model.addAttribute("cart", Cart.getCart().getItems());
        model.addAttribute("cartTotal", Cart.getCart().getTotal());
        return "index";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam String productID) {
        Product product = catalog.getProduct(productID);
        Cart.getCart().add(product);

        return "redirect:/";
    }
    @PostMapping("/checkout")
    public String checkout() {
        Cart.getCart().clear();
        return "paymentResult";
    }

    @PostMapping("/addDiscount")
    public String addDiscount(@RequestParam String discountCode) {
        Cart cart = Cart.getCart();
        List<Product> cartItems = new ArrayList<>(cart.getItems());
        if(Objects.equals(discountCode, DISCOUNT_CODE)) {
            for (Product product : cartItems) {
                if (product.getCategory() == DISCOUNT_CATEGORY) {
                    Product discountedProduct = new DiscountDecorator(product, DISCOUNT_AMOUNT);
                    cart.replace(product, discountedProduct);
                }
            }
        }


        return "redirect:/";
    }

    @PostMapping("/payment")
    public String showPaymentPage(@RequestParam String paymentMethod, @RequestParam(required = false, name="giftWrappedIndices") List<Integer> giftWrapped,  Model model) {
        Cart cart = Cart.getCart();
        double cartTotal = cart.getTotal();

        PaymentStrategy strategy;
        switch (paymentMethod.toLowerCase()) {
            case "creditcard": strategy = new CreditCard(); break;
            case "cash": strategy = new Cash(); break;
            case "paymentplan": strategy = new PaymentPlan(); break;
            default: strategy = null; break;
        }

        applyGiftWrapping(cart, giftWrapped);

        double finalAmount = strategy.calculateFinalAmount(cartTotal);

        model.addAttribute("cart", cart.getItems());
        model.addAttribute("cartTotal", cartTotal);
        model.addAttribute("finalAmount", finalAmount);
        model.addAttribute("paymentMethod", paymentMethod);
        switch (paymentMethod.toLowerCase()) {
            case "creditcard": return "cardPayment";
            case "cash": return "cashPayment";
            case "paymentplan": return "paymentPlanPayment";
            default: return "redirect:/";
    }
}

    private void applyGiftWrapping(Cart cart, List<Integer> giftWrapped) {
        if (giftWrapped == null) {
            return;
        }
        List<Product> items = cart.getItems();
        for (int i = 0; i < items.size(); i++) {
            if (giftWrapped.contains(i)) {
                Product product = items.get(i);
                Product giftWrappedProduct = new GiftWrappedDecorator(product);
                cart.replace(product, giftWrappedProduct);
            }
        }
    }
}

