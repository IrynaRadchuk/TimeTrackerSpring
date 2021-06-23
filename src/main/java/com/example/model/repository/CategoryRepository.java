package com.example.model.repository;

import com.example.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends CrudRepository<Category,Long> {
    @Query("Select u from Category u where u.categoryName = :name")
    Category categoryIdByName(@Param("name")String categoryName);
}
