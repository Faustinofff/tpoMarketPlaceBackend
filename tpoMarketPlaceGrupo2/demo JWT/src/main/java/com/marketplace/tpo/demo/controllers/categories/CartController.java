package com.marketplace.tpo.demo.controllers.categories;


import com.marketplace.tpo.demo.entity.Cart;
import com.marketplace.tpo.demo.entity.User;
import com.marketplace.tpo.demo.service.CartService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 🛒 Ver carrito del usuario autenticado
    @GetMapping
    public Cart getCart(@AuthenticationPrincipal User user) {
        return cartService.getCart(user.getId());
    }

    // ➕ Agregar producto al carrito
    @PostMapping("/add")
    public String addToCart(
            @AuthenticationPrincipal User user,
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") int quantity
    ) {
        cartService.addToCart(user.getId(), productId, quantity);
        return "Producto agregado al carrito";
    }

    // ❌ Eliminar producto del carrito
    @DeleteMapping("/remove/{productId}")
    public String removeFromCart(
            @AuthenticationPrincipal User user,
            @PathVariable Long productId
    ) {
        cartService.removeFromCart(user.getId(), productId);
        return "Producto eliminado del carrito";
    }

    // 🧹 Vaciar carrito
    @DeleteMapping("/clear")
    public String clearCart(@AuthenticationPrincipal User user) {
        cartService.clearCart(user.getId());
        return "Carrito vaciado";
    }

    // 💰 Total del carrito
    @GetMapping("/total")
    public double getTotal(@AuthenticationPrincipal User user) {
        return cartService.getCart(user.getId()).getTotal();
    }
}
