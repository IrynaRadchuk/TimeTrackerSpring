package com.example.service;

import com.example.model.Category;

public interface CategoryService {
    Iterable<Category> categories();

    Category categoryByName(String name);
}
