package com.marketplace.tpo.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ Relación con el producto
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // ✅ Relación con el carrito (evita recursión infinita al serializar)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "cart_id", nullable = false)
    @JsonBackReference
    private Cart cart;

    @Column(nullable = false)
    private int quantity;

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }
}



