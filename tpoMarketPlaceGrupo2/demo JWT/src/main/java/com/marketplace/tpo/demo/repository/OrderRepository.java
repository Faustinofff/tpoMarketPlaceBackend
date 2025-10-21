package com.marketplace.tpo.demo.repository;

import com.marketplace.tpo.demo.entity.Order;
import com.marketplace.tpo.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    
    List<Order> findByUser(User user);
}
