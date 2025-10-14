package com.marketplace.tpo.demo.service;

import com.marketplace.tpo.demo.entity.*;
import com.marketplace.tpo.demo.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    // 🛒 Obtener carrito del usuario
    public Cart getCart(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    // ⚠️ Guardamos y forzamos el flush para obtener ID inmediato
                    return cartRepository.saveAndFlush(newCart);
                });
    }

    // ➕ Agregar producto al carrito
    public Cart addToCart(Long userId, Long productId, int quantity) {
        Cart cart = getCart(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // ✅ Si el carrito aún no tiene ID, lo guardamos y flusheamos
        if (cart.getId() == null) {
            cart = cartRepository.saveAndFlush(cart);
        }

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
            cart.getItems().add(item);
        }

        // 💰 Calcular total con BigDecimal
        BigDecimal totalBD = cart.getItems().stream()
                .map(i -> i.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotal(totalBD);

        // ✅ Guardar y flushear para garantizar persistencia total
        return cartRepository.saveAndFlush(cart);
    }

    // ❌ Eliminar producto del carrito
    public Cart removeFromCart(Long userId, Long productId) {
        Cart cart = getCart(userId);
        cart.getItems().removeIf(i -> i.getProduct().getId().equals(productId));

        BigDecimal totalBD = cart.getItems().stream()
                .map(i -> i.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotal(totalBD);
        return cartRepository.saveAndFlush(cart);
    }

    // 🧹 Vaciar carrito
    public void clearCart(Long userId) {
        Cart cart = getCart(userId);
        cart.getItems().clear();
        cart.setTotal(BigDecimal.ZERO);
        cartRepository.saveAndFlush(cart);
    }

    // ------------------------------------------------------------------------
    // 🔽 Métodos alternativos por email (opcional, si trabajás con UserDetails)
    // ------------------------------------------------------------------------

    public Cart getCartByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return getCart(user.getId());
    }

    public Cart addToCartByEmail(String email, Long productId, int quantity) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return addToCart(user.getId(), productId, quantity);
    }

    public Cart removeFromCartByEmail(String email, Long productId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return removeFromCart(user.getId(), productId);
    }

    public void clearCartByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        clearCart(user.getId());
    }
}

