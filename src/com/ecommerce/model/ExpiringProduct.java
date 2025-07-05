package com.ecommerce.model;

import com.ecommerce.interfaces.Shippable;

import java.time.LocalDate;

public class ExpiringProduct extends Product implements Shippable {

   private final LocalDate expiryDate;
   private final boolean shippable;
   private final double weight;

    public ExpiringProduct(String name, double price, int quantity, LocalDate expireDate, boolean shippable, double weight) {
        super(name, price, quantity);
        this.expiryDate = expireDate;
        this.shippable = shippable;
        this.weight = weight;
    }


    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    @Override
    public boolean requiresShipping() {
        return shippable;
    }

    public double getWeight() {
        if(!requiresShipping())
            throw new UnsupportedOperationException("Product is not shippable");
        return weight;
    }
}
