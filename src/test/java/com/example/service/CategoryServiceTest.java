package com.example.service;

import com.example.model.Category;
import com.example.model.repository.CategoryRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CategoryServiceTest {
    private final List<Category> categoriesList = new ArrayList<>();
    private final Category expectedCategory = new Category(3L,"New");

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @Before
    public void setup(){
        categoriesList.add(new Category(1L,"Lesson"));
        categoriesList.add(new Category(2L,"Other"));
    }


    @Test
    void categories() {
        Mockito.when(categoryRepository.findAll()).thenReturn(categoriesList);
        Iterable <Category> actual = categoryService.categories();
        Assert.assertNotNull(actual);
    }

    @Test
    void categoryByName() {
        Mockito.when(categoryRepository.categoryIdByName("New")).thenReturn(expectedCategory);
        Category actual = categoryService.categoryByName("New");
        Assert.assertEquals(expectedCategory,actual);
    }
}