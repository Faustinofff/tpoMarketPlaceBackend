
package com.marketplace.tpo.demo.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.marketplace.tpo.demo.entity.Category;
import com.marketplace.tpo.demo.exceptions.CategoryDuplicateException;

public interface CategoryService {  // Interfaz con los metodos que necesitamos pero no los implementa
    public Page<Category> getCategories(PageRequest pageRequest); //trae todas las categorias

    public Optional<Category> getCategoryById(Long categoryId);  //trae una categoria por su id

    public Category createCategory(String description) throws CategoryDuplicateException; //crea una nueva categoria
}