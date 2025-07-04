package com.ecommerce.service;

import com.ecommerce.interfaces.Shippable;
import com.ecommerce.model.*;

import java.util.List;

public class CheckoutService {
    private static final double SHIPPING_RATE_PER_KG = 10.0;
    private static final double MINIMUM_SHIPPING_FEE = 30.0;
    private final ShippingService shippingService;

    public CheckoutService(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    public void checkout(Customer customer, ShoppingCart cart) {
        validateCheckout(customer, cart);
        validateCartItems(cart);

        double subtotal = cart.calculateSubtotal();
        double shippingFee = calculateShippingFee(cart);
        double totalAmount = subtotal + shippingFee;

        processShipping(cart);
        processPayment(customer, totalAmount);
        updateInventory(cart);
        printReceipt(cart, subtotal, shippingFee, totalAmount, customer.getBalance());
    }

    private void validateCheckout(Customer customer, ShoppingCart cart) {
        if (customer == null || cart == null) throw new IllegalArgumentException("Customer and cart required");
        if (cart.isEmpty()) throw new IllegalStateException("Cannot checkout with empty cart");
    }

    private void validateCartItems(ShoppingCart cart) {
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            if (product.isExpired()) throw new IllegalStateException(product.getName() + " is expired");
            if (item.getQuantity() > product.getQuantity()) throw new IllegalStateException(product.getName() + " out of stock");
        }
    }

    private double calculateShippingFee(ShoppingCart cart) {
        double totalWeight = 0;

        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            if (product.requiresShipping()) {
                if (product instanceof ExpiringProduct) {
                    totalWeight += ((ExpiringProduct) product).getWeight() * item.getQuantity();
                } else if (product instanceof NonExpiringProduct && ((NonExpiringProduct) product).requiresShipping()) {
                    totalWeight += ((NonExpiringProduct) product).getWeight() * item.getQuantity();
                }
            }
        }

        if (totalWeight == 0) {
            return 0;
        }

        double calculatedFee = totalWeight * SHIPPING_RATE_PER_KG;
        return Math.max(calculatedFee, MINIMUM_SHIPPING_FEE);
    }

    private void processShipping(ShoppingCart cart) {
        List<Shippable> shippableItems = cart.getShippableItems();
        if (!shippableItems.isEmpty()) {
            shippingService.shipItems(shippableItems);
        }
    }

    private void processPayment(Customer customer, double amount) {
        if (customer.getBalance() < amount) {
            throw new IllegalStateException("Insufficient customer balance");
        }
        customer.deductAmount(amount);
    }

    private void updateInventory(ShoppingCart cart) {
        cart.getItems().forEach(item ->
                item.getProduct().reduceQuantity(item.getQuantity()));
    }

    private void printReceipt(ShoppingCart cart, double subtotal,
                              double shippingFee, double totalAmount, double newBalance) {


        System.out.println("** Checkout receipt **");
        cart.getItems().forEach(item ->
                System.out.printf("%-10s %6.0f%n",
                        item.getQuantity() + "x " + item.getProduct().getName(),
                        item.getTotalPrice())
        );
        System.out.println("----------------------");
        System.out.printf("%-10s %6.0f%n", "Subtotal", subtotal);
        System.out.printf("%-10s %6.0f%n", "Shipping", shippingFee);
        System.out.printf("%-10s %6.0f%n", "Amount", totalAmount);

    }
}
