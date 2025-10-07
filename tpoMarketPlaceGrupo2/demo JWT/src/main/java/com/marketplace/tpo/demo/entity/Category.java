package com.marketplace.tpo.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    // 👇 Ya no tiene referencia directa al producto
    // (evitamos bucles y simplificamos la relación)

    public Category() {}

    public Category(String description) {
        this.description = description;
    }
}
