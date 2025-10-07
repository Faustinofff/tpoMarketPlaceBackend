package com.marketplace.tpo.demo.repository;

import com.marketplace.tpo.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
