package com.marketplace.tpo.demo.controllers.categories;

import com.marketplace.tpo.demo.entity.Cart;
import com.marketplace.tpo.demo.service.CartService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 🛒 Obtener carrito del usuario autenticado
    @GetMapping
    public Cart getCart(@AuthenticationPrincipal UserDetails userDetails) {
        return cartService.getCartByEmail(userDetails.getUsername());
    }

    // ➕ Agregar producto al carrito
    @PostMapping("/add")
    public Cart addToCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") int quantity
    ) {
        return cartService.addToCartByEmail(userDetails.getUsername(), productId, quantity);
    }

    // ❌ Eliminar producto del carrito
    @DeleteMapping("/remove/{productId}")
    public Cart removeFromCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long productId
    ) {
        return cartService.removeFromCartByEmail(userDetails.getUsername(), productId);
    }

    // 🧹 Vaciar carrito
    @DeleteMapping("/clear")
    public void clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        cartService.clearCartByEmail(userDetails.getUsername());
    }
}



