package com.marketplace.tpo.demo.repository;

import com.marketplace.tpo.demo.entity.Cart;
import com.marketplace.tpo.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
