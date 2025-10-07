package com.marketplace.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.marketplace.tpo.demo.entity.Category;
import com.marketplace.tpo.demo.exceptions.CategoryDuplicateException;
import com.marketplace.tpo.demo.repository.CategoryRepository;

@Service //se declara el bean servicio para que Spring pueda inyectarlo 
public class CategoryServiceImpl implements CategoryService {  // Implementación de la interfaz CategoryService y sus metodos

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Category> getCategories(PageRequest pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Optional<Category> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public Category createCategory(String description) throws CategoryDuplicateException {
        List<Category> categories = categoryRepository.findByDescription(description);
        if (categories.isEmpty())
            return categoryRepository.save(new Category(description));
        throw new CategoryDuplicateException();
    }
}
