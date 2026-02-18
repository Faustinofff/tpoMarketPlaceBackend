package com.marketplace.tpo.demo.service;

import com.marketplace.tpo.demo.entity.Cart;

public interface CartService {

    Cart getCart(Long userId);

    Cart addToCart(Long userId, Long productId, int quantity);

    Cart removeFromCart(Long userId, Long productId);

    void clearCart(Long userId);

    Cart getCartByEmail(String email);

    Cart addToCartByEmail(String email, Long productId, int quantity);

    Cart removeFromCartByEmail(String email, Long productId);

    void clearCartByEmail(String email);
}

