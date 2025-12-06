package com.application;

import java.util.ArrayList;
import java.util.List;
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
import productDecorator.GiftWrappedDecorator;
import products.Product;
import products.ProductFactory;

@Controller
public class ApplicationController {
    private static final String boarderMessage = "Hello and Welcome to the Shrek Merch Shop. Use code \'FARQUAAD\' for 25% off all hats!";
    private final AtomicLong counter = new AtomicLong();
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

        // 3. Redirect back to the home page (refreshes the view)
        return "redirect:/";
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam String paymentType, @RequestParam(required = false, name="giftWrapped") List<String> giftWrapped, Model model) {
        // calculate total cart amount
        Cart cart = Cart.getCart();
        double amount = cart.getTotal();

        // reuse your existing pay logic
        PaymentStrategy strategy;
        switch(paymentType.toLowerCase()) {
            case "creditcard": strategy = new CreditCard(); break;
            case "cash": strategy = new Cash(); break;
            case "paymentplan": strategy = new PaymentPlan(); break;
            default: strategy = null;
        }

        double finalAmount = strategy.calculateFinalAmount(amount);
        model.addAttribute("result", finalAmount);
        model.addAttribute("cart", Cart.getCart().getItems());


        List<Product> finalProducts = new ArrayList<>();

        for (int i = 0; i < cart.getItems().size(); i++) {
            Product product = cart.getItems().get(i);

            boolean isGiftWrapped =
                    giftWrapped != null && giftWrapped.contains("giftWrapped[" + i + "]");

            if (isGiftWrapped) {
                product = new GiftWrappedDecorator(product);
            }

            finalProducts.add(product);
        }

        cart.clear();

        return "paymentResult"; // show paymentResult.html
    }

    @PostMapping("/payment")
    public String showPaymentPage(@RequestParam String paymentMethod, Model model) {
        model.addAttribute("cart", Cart.getCart().getItems());
        model.addAttribute("cartTotal", Cart.getCart().getTotal());

        switch (paymentMethod.toLowerCase()) {
            case "creditcard":
                return "cardPayment";
            case "cash":
                return "cashPayment";
            case "paymentplan":
                return "paymentPlanPayment";
            default:
                return "redirect:/";
        }
    }
}

