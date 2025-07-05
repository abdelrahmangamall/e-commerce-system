package com.ecommerce;

import java.time.LocalDate;
import com.ecommerce.model.*;
import com.ecommerce.service.*;

public class Main {
    public static void main(String[] args) {

        ExpiringProduct cheese = new ExpiringProduct("Cheese", 100, 10,
                LocalDate.now().plusDays(30), true, 0.4);
        ExpiringProduct biscuits = new ExpiringProduct("Biscuits", 150, 5,
                LocalDate.now().plusDays(60), true, 0.7);
        NonExpiringProduct tv = new NonExpiringProduct("TV", 1000, 3, true, 15.0);
        NonExpiringProduct scratchCard = new NonExpiringProduct("Mobile scratch card", 50, 100, false, 0);

        Customer customer = new Customer("John Doe", 2000);

        ShoppingCart cart = new ShoppingCart();
        cart.add(cheese, 2);
        cart.add(biscuits, 1);
        cart.add(scratchCard, 1);

        ShippingService shippingService = new ShippingService();
        CheckoutService checkoutService = new CheckoutService(shippingService);
        checkoutService.checkout(customer, cart);
        testErrorCases();
    }

    private static void testErrorCases() {
        try {
            ShoppingCart cart = new ShoppingCart();
            Customer customer = new Customer("Test", 100);
            new CheckoutService(new ShippingService()).checkout(customer, cart);
        } catch (IllegalStateException e) {
            System.out.println("Test passed: " + e.getMessage());
        }
    }
}