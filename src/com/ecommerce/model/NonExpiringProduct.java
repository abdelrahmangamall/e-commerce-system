package com.ecommerce.model;

import com.ecommerce.interfaces.Shippable;

public class NonExpiringProduct extends Product implements Shippable {

    private final boolean shippable;
    private final double weight;

    public NonExpiringProduct(String name, double price, int quantity, boolean shippable, double weight) {
        super(name, price, quantity);
        this.shippable = shippable;
        this.weight = weight;
    }


    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public boolean requiresShipping() {
        return shippable;
    }

    public double getWeight() {
        if(!requiresShipping())
        {
            throw new UnsupportedOperationException("Product is not shippable");
        }
        return weight;
    }
}
