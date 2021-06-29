package com.example.service;

import com.example.model.Category;

/**
 * Service to manage categories of activities
 *
 * @author Iryna Radchuk
 */
public interface CategoryService {

    /**
     * Get all activity categories
     */
    Iterable<Category> categories();

    /**
     * Get category by category name
     */
    Category categoryByName(String name);
}
