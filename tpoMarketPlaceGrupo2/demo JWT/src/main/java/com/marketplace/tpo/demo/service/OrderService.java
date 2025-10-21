package com.marketplace.tpo.demo.service;

import com.marketplace.tpo.demo.entity.*;
import com.marketplace.tpo.demo.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    public Order createOrder(String userEmail, String customerName) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        
        Order order = new Order();
        order.setUser(user);
        order.setCustomerName(customerName);
        order.setCreatedAt(LocalDateTime.now());
        order.setTotal(cart.getTotal());

        
        order.setItems(cart.getItems().stream().map(ci -> {
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(ci.getProduct());
            oi.setQuantity(ci.getQuantity());
            oi.setPrice(ci.getProduct().getPrice());
            return oi;
        }).collect(Collectors.toList()));

        Order savedOrder = orderRepository.save(order);

        
        cart.getItems().clear();
        cart.setTotal(null);
        cartRepository.save(cart);

        return savedOrder;
    }
}
