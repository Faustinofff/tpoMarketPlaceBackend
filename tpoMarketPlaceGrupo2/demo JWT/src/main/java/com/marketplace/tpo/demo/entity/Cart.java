package com.marketplace.tpo.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con el usuario dueño del carrito
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Relación bidireccional con los ítems del carrito
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    public void addItem(CartItem newItem) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(newItem.getProduct().getId())) {
                item.increaseQuantity(newItem.getQuantity());
                return;
            }
        }
        newItem.setCart(this); // 🔗 importante para la relación bidireccional
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
