package com.marketplace.tpo.demo.service;

import com.marketplace.tpo.demo.entity.Order;

public interface OrderService {

    Order createOrder(String userEmail, String customerName);
}
