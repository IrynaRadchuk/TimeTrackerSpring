package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "user_allowed_activity")
public class AllowedActivity {
    @Id
    @Column(name = "user_id")
    private Long id;
    @Column(name = "activity_id")
    private Long activityId;
    @Column(name = "status")
    private String status;
}
