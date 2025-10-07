package com.marketplace.tpo.demo.service;

import com.marketplace.tpo.demo.entity.Cart;
import com.marketplace.tpo.demo.entity.CartItem;
import com.marketplace.tpo.demo.entity.Product;
import com.marketplace.tpo.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {

    private final ProductRepository productRepository;

    // Carritos guardados en memoria (por ID de usuario)
    private final Map<Long, Cart> carts = new HashMap<>();

    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Cart getCart(Long userId) {
        return carts.computeIfAbsent(userId, id -> new Cart());
    }

    public void addToCart(Long userId, Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Cart cart = getCart(userId);
        cart.addItem(new CartItem(product, quantity));
    }

    public void removeFromCart(Long userId, Long productId) {
        getCart(userId).removeItem(productId);
    }

    public void clearCart(Long userId) {
        getCart(userId).clear();
    }
}

