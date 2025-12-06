package com.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import order.Cart;
import order.Catalog;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import paymentStrategy.Cash;
import paymentStrategy.CreditCard;
import paymentStrategy.PaymentPlan;
import paymentStrategy.PaymentStrategy;
import productDecorator.DiscountDecorator;
import productDecorator.GiftWrappedDecorator;
import products.Product;
import products.ProductFactory;

@Controller
public class ApplicationController {
    private static final String boarderMessage = "Hello and Welcome to the Shrek Merch Shop. Use code \'FARQUAAD\' for 25% off all hats!";
    private final String DISCOUNT_CODE = "FARQUAAD";
    private final Product.Categories DISCOUNT_CATEGORY = Product.Categories.Hats;
    ProductFactory productFactory = new ProductFactory();
    Catalog catalog = new Catalog();

    @GetMapping("/")
    public String index(Model model) {
        catalog = new Catalog.Builder()
                .addProduct(productFactory.createHat("Shrek Mask", 20.00))
                .addProduct(productFactory.createShirt("Shrek T-Shirt", 30.00))
                .addProduct(productFactory.createJacket("Leather Vest", 150.00))
                .addProduct(productFactory.createMiscellaneousItem("Donkey Plushie", 12.99))
                .build();


        model.addAttribute("message", boarderMessage);
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

    @PostMapping("/addDiscount")
    public String addDiscount(@RequestParam String discountCode) {
        Cart cart = Cart.getCart();
        List<Product> cartItems = new ArrayList<>(cart.getItems());
        if(Objects.equals(discountCode, DISCOUNT_CODE)) {
            for (Product product : cartItems) {
                if(product.getCategory()==DISCOUNT_CATEGORY){
                    Product productNew = new DiscountDecorator(product, 0.25);
                    cart.replace(product, productNew);
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

        for (int i = 0; i < cart.getItems().size(); i++) {
            Product product = cart.getItems().get(i);

            boolean isGiftWrapped =
                    giftWrapped != null && giftWrapped.contains(i);

            if (isGiftWrapped) {
                Product productNew = new GiftWrappedDecorator(product);
                cart.replace(product, productNew);
            }


        }

        double finalAmount =strategy.calculateFinalAmount(cartTotal);

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
}

