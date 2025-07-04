package com.ecommerce.model;

import com.ecommerce.interfaces.Shippable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShoppingCart {
    private final List<CartItem> items = new ArrayList<>();

    private void valideAddToCart(Product product, int quantity)
    {
        if(product == null) throw new IllegalArgumentException("Product can not be null");
        if(quantity > product.getQuantity()) throw new IllegalArgumentException("not enough stock");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        if (product.isExpired()) throw new IllegalArgumentException("Product is expired");
    }

    public void add(Product product, int quantity){
        valideAddToCart(product,quantity);
        items.add(new CartItem(product,quantity));
    }

    public List<CartItem> getItems() {
       return Collections.unmodifiableList(items);
    }
    public boolean isEmpty(){return items.isEmpty();
    }

    public double calculateSubtotal() {
        return items.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }
    public List<Shippable> getShippableItems() {
        List<Shippable> shippableItems = new ArrayList<>();
        for (CartItem item : items) {
            Product product = item.getProduct();
            if (product.requiresShipping() && product instanceof Shippable) {
                Shippable shippable = (Shippable) product;
                for (int i = 0; i < item.getQuantity(); i++) {
                    shippableItems.add(shippable);
                }
            }
        }
        return shippableItems;
    }
}
