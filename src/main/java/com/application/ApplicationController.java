package com.application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import order.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import paymentStrategy.Cash;
import paymentStrategy.CreditCard;
import paymentStrategy.PaymentPlan;
import paymentStrategy.PaymentStrategy;
import products.Product;
import products.ProductFactory;

@Controller
public class ApplicationController {
    private static final String boarderMessage = "Hello and Welcome to the Shrek Merch Shop. Use code \'FARQUAAD\' for 25% off all hats!";
    private final AtomicLong counter = new AtomicLong();
    ProductFactory productFactory = new ProductFactory();
    List<Product> cart= new ArrayList<Product>();
    Order catelog = new Order();
    Order myOrder = new Order();

    @GetMapping("/")
    public String index(Model model) {
        catelog = new Order.Builder()
                .addProduct(productFactory.createHat("Shrek Mask", 20.00))
                .addProduct(productFactory.createShirt("Shrek T-Shirt", 30.00))
                .addProduct(productFactory.createJacket("Leather Vest", 150.00))
                .addProduct(productFactory.createMiscellaneousItem("Donkey Plushie", 12.99))
                .build();


        model.addAttribute("message", boarderMessage);
        model.addAttribute("products", catelog.getProducts());
        model.addAttribute("cart", cart);
        return "index";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam String productID) {
        Product product = catelog.getProduct(productID);
        cart.add(product);

        // 3. Redirect back to the home page (refreshes the view)
        return "redirect:/";
    }

    @GetMapping("/{type}")
    public String pay(@PathVariable String type, @RequestParam double amount, Model model) {
        PaymentStrategy strategy;

        switch(type.toLowerCase()) {
            case "creditcard": strategy = new CreditCard(); break;
            case "cash": strategy = new Cash(); break;
            case "paymentplan": strategy = new PaymentPlan(); break;
            default: strategy = null;
        }

        String result = (strategy != null) ? strategy.pay(amount) : "Invalid payment method!";
        model.addAttribute("result", result);
        return "paymentResult";  // This HTML page will display the result
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam String paymentType, Model model) {
        // calculate total cart amount
        double amount = cart.stream().mapToDouble(Product::getPrice).sum();

        // reuse your existing pay logic
        PaymentStrategy strategy;
        switch(paymentType.toLowerCase()) {
            case "creditcard": strategy = new CreditCard(); break;
            case "cash": strategy = new Cash(); break;
            case "paymentplan": strategy = new PaymentPlan(); break;
            default: strategy = null;
        }

        String result = (strategy != null) ? strategy.pay(amount) : "Invalid payment method!";
        model.addAttribute("result", result);

        return "paymentResult"; // show paymentResult.html
    }
}

