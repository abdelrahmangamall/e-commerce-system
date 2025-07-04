package com.ecommerce.model;

public abstract class Product {
   protected String name;
   protected double price;
   protected int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void reduceQuantity(int amount)
    {
        if(amount > quantity)
        {
            throw new IllegalArgumentException("Cannot reduce quantity beyond available stock");
        }
        quantity -= amount;
    }

    public abstract boolean isExpired();
    public abstract boolean requiresShipping();

}
