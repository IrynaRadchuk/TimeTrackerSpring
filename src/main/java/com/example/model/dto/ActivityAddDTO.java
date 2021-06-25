package com.example.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

public class ActivityAddDTO {

    @Pattern(regexp = "^[A-Z][a-z]{1,45}$", message = "Wrong Activity Name format")
    @NotBlank(message = "Activity Name is mandatory")
    private String activityAddName;

    @Pattern(regexp = "^[А-ЩЮЯЇІЄҐ][а-щьюяїієґ]{1,45}$", message = "Wrong Activity Name (Ukrainian) format")
    @NotBlank(message = "Activity Name (Ukrainian) is mandatory")
    private String activityAddUa;

    private String categoryAdd;

    public ActivityAddDTO(String activityAddName, String activityAddUa, String categoryAdd) {
        this.activityAddName = activityAddName;
        this.activityAddUa = activityAddUa;
        this.categoryAdd = categoryAdd;
    }

    public String getActivityAddName() {
        return activityAddName;
    }

    public void setActivityAddName(String activityAddName) {
        this.activityAddName = activityAddName;
    }

    public String getActivityAddUa() {
        return activityAddUa;
    }

    public void setActivityAddUa(String activityAddUa) {
        this.activityAddUa = activityAddUa;
    }

    public String getCategoryAdd() {
        return categoryAdd;
    }

    public void setCategoryAdd(String categoryAdd) {
        this.categoryAdd = categoryAdd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityAddDTO)) return false;
        ActivityAddDTO that = (ActivityAddDTO) o;
        return Objects.equals(activityAddName, that.activityAddName) &&
                Objects.equals(activityAddUa, that.activityAddUa) &&
                Objects.equals(categoryAdd, that.categoryAdd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activityAddName, activityAddUa, categoryAdd);
    }
}
