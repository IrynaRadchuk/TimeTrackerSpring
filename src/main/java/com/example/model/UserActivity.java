package com.example.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user_activity")
public class UserActivity {
    @Id
    @Column(name = "user_id")
    private Long id;
    @Column(name = "activity_id")
    private Long activityId;
    @Column(name = "activity_date")
    private LocalDate activityDate;
    @Column(name = "activity_duration")
    private Integer activityDuration;
}