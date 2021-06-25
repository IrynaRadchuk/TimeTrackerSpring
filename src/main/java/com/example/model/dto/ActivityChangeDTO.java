package com.example.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ActivityChangeDTO {
    private Long Id;
    @Pattern(regexp="^[A-Z][a-z]{1,45}$", message = "Wrong Activity Name format")
    @NotBlank(message = "Activity Name is mandatory")
    private String activityName;
    @Pattern(regexp="^[А-ЩЮЯЇІЄҐ][а-щьюяїієґ]{1,45}$", message = "Wrong Activity Name (Ukrainian) format")
    @NotBlank(message = "Activity Name (Ukrainian) is mandatory")
    private String activityUa;
    private String categoryList;

    public ActivityChangeDTO(String activityName, String activityUa, String categoryList) {
        this.activityName = activityName;
        this.activityUa = activityUa;
        this.categoryList = categoryList;
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

    public String getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(String categoryList) {
        this.categoryList = categoryList;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
}
