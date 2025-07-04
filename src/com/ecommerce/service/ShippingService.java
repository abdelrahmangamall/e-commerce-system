package com.ecommerce.service;

import com.ecommerce.interfaces.Shippable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShippingService {

    private void printItemCounts(List<Shippable> items) {
        Map<String, Integer> itemCounts = new HashMap<>();
        for (Shippable item : items) {
            itemCounts.put(item.getName(), itemCounts.getOrDefault(item.getName(), 0) + 1);
        }
        itemCounts.forEach((name, count) -> System.out.println(count + "x " + name));
    }

    private void printIndividualWeights(List<Shippable> items) {
        items.forEach(item -> System.out.println((int)(item.getWeight() * 1000) + "g"));
    }

    private void printTotalWeight(List<Shippable> items) {
        double totalWeight = items.stream().mapToDouble(Shippable::getWeight).sum();
        System.out.printf("Total package weight %.1fkg%n", totalWeight);
    }


    public void shipItems(List<Shippable> items)
    {
         printItemCounts(items);
         printIndividualWeights(items);
         printTotalWeight(items);

    }
}
