package com.ecommerce.model;

public class Customer {
    private final String name;
    private double balance;

    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }
    public void deductAmount(double amount){
        validateDeduction(amount);
        balance -= amount;
    }

    private void validateDeduction(double amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative");
        if (balance < amount) throw new IllegalArgumentException("Insufficient balance");
    }

}
