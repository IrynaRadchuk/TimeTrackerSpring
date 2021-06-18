package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "activity_category")
public class Category {
    @Id
    @Column(name = "category_id")
    private Long id;
    @Column(name = "category_name")
    private String categoryName;

}
