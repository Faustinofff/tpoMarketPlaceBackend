package com.marketplace.tpo.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketplace.tpo.demo.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> { // datos que JPA nos va a pedir para saber donde y sobre que va a estar trabajando

    @Query(value = "select c from Category c where c.description = ?1")
    List<Category> findByDescription(String description);
}
