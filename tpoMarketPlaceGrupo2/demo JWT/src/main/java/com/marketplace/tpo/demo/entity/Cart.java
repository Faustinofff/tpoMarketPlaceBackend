package com.marketplace.tpo.demo.entity;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartItem> items = new ArrayList<>();

    public List<CartItem> getItems() {
        return items;
    }

    public void addItem(CartItem newItem) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(newItem.getProduct().getId())) {
                item.increaseQuantity(newItem.getQuantity());
                return;
            }
        }
        items.add(newItem);
    }

    public void removeItem(Long productId) {
        items.removeIf(i -> i.getProduct().getId().equals(productId));
    }

    public void clear() {
        items.clear();
    }

    public double getTotal() {
        return items.stream()
                .mapToDouble(i -> i.getProduct().getPrice().doubleValue() * i.getQuantity())
                .sum();
    }
}
