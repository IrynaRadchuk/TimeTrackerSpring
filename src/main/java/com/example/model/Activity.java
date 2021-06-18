package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "activity_id")
    private Long id;
    @Column(name = "activity_name")
    private String activityName;
    @Column(name = "category_id")
    private Long categoryId;
    @Column(name = "activity_ua")
    private String activityUa;
}
