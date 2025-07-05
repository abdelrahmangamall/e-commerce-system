package com.ecommerce.service;

import com.ecommerce.interfaces.Shippable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShippingService {

    public void shipItems(List<Shippable> items) {
        if (items == null || items.isEmpty()) return;

        System.out.println("** Shipment notice **");

        Map<String, ItemInfo> itemMap = new LinkedHashMap<>();

        for (Shippable item : items) {
            String name = item.getName();
            double weight = item.getWeight();

            itemMap.putIfAbsent(name, new ItemInfo(0, weight));
            itemMap.get(name).count++;
        }

        for (Map.Entry<String, ItemInfo> entry : itemMap.entrySet()) {
            String name = entry.getKey();
            int count = entry.getValue().count;
            int weightInGrams = (int) (entry.getValue().weight * 1000);
            System.out.printf("%dx %s %dg%n", count, name, weightInGrams);
        }

        double totalWeight = items.stream().mapToDouble(Shippable::getWeight).sum();
        System.out.printf("Total package weight %.1fkg%n", totalWeight);
        System.out.println();
    }

    private static class ItemInfo {
        int count;
        double weight;

        ItemInfo(int count, double weight) {
            this.count = count;
            this.weight = weight;
        }
    }
}
