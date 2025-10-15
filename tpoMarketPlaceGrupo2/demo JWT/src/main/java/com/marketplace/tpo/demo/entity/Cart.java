package com.marketplace.tpo.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "carts") // ✅ Asegura el nombre de tabla correcto
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 👇 Relación uno a uno con User (con FK explícita)
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // 👇 Relación con los ítems del carrito + evita recursión infinita
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> items = new ArrayList<>();

    @Column(precision = 10, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    public void addItem(CartItem item) {
        items.add(item);
    }

    public void removeItem(Long productId) {
        items.removeIf(i -> i.getProduct().getId().equals(productId));
    }

    public void clear() {
        items.clear();
        total = BigDecimal.ZERO;
    }

    // Sobrescribimos el método toString() para evitar recursión infinita y mostrar información más legible
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cart ID: ").append(id).append(", ");
        sb.append("User: ").append(user != null ? user.getEmail() : "No User").append(", ");  // Evitar NullPointerException si user es nulo
        sb.append("Total: ").append(total).append(", ");
        sb.append("Items: [");

        // Agregamos los items del carrito
        for (CartItem item : items) {
            sb.append(item.toString()).append(", ");
        }

        // Eliminar la última coma y espacio extra
        if (!items.isEmpty()) {
            sb.setLength(sb.length() - 2);
        }

        sb.append("]");

        return sb.toString();
    }
}
