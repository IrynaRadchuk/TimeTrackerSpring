package com.example.service.impl;

import com.example.model.Category;
import com.example.model.repository.CategoryRepository;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Iterable<Category> categories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category categoryByName(String name) {
        return categoryRepository.categoryIdByName(name);
    }
}
