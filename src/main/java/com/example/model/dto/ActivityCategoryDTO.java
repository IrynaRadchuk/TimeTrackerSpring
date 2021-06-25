package com.example.model.dto;

import com.example.model.Activity;
import com.example.model.Category;

public class ActivityCategoryDTO {
    private Iterable<Activity> activities;
    private Iterable<Category> categories;

    public ActivityCategoryDTO(Iterable<Activity> activities, Iterable<Category> categories) {
        this.activities = activities;
        this.categories = categories;
    }

    public Iterable<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Iterable<Activity> activities) {
        this.activities = activities;
    }

    public Iterable<Category> getCategories() {
        return categories;
    }

    public void setCategories(Iterable<Category> categories) {
        this.categories = categories;
    }
}
