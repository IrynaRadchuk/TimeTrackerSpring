package com.example.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "activity_id")
    private Long id;
    @Column(name = "activity_name")
    private String activityName;
    @Column(name = "activity_ua")
    private String activityUa;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "user_quantity")
    private Integer users;

    public Activity() {
    }

    public Activity(String activityName, String activityUa, Category category) {
        this.activityName = activityName;
        this.activityUa = activityUa;
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityUa() {
        return activityUa;
    }

    public void setActivityUa(String activityUa) {
        this.activityUa = activityUa;
    }

    public Integer getUsers() {
        return users;
    }

    public void setUsers(Integer users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Activity)) return false;
        Activity activity = (Activity) o;
        return Objects.equals(id, activity.id) &&
                Objects.equals(activityName, activity.activityName) &&
                Objects.equals(activityUa, activity.activityUa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, activityName, activityUa);
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", activityName='" + activityName + '\'' +
                ", activityUa='" + activityUa + '\'' +
                '}';
    }
}