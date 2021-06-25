package com.example.model.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class ActivityScheduleDTO {
    @NotBlank(message = "Add date")
    private String activityDateAdd;
    @NotBlank(message = "Add activity name")
    private String activityNameAdd;
    @NotNull(message = "Add duration time (hrs)")
    @Min(value = 1, message = "Time for activity cannot be less then 1 hour")
    @Max(value = 8, message = "Time for activity cannot exceed 8 hours")
    private Integer activityDurationAdd;

    public ActivityScheduleDTO(String activityDateAdd, String activityNameAdd, Integer activityDurationAdd) {
        this.activityDateAdd = activityDateAdd;
        this.activityNameAdd = activityNameAdd;
        this.activityDurationAdd = activityDurationAdd;
    }

    public String getActivityDateAdd() {
        return activityDateAdd;
    }

    public void setActivityDateAdd(String activityDateAdd) {
        this.activityDateAdd = activityDateAdd;
    }

    public String getActivityNameAdd() {
        return activityNameAdd;
    }

    public void setActivityNameAdd(String activityNameAdd) {
        this.activityNameAdd = activityNameAdd;
    }

    public Integer getActivityDurationAdd() {
        return activityDurationAdd;
    }

    public void setActivityDurationAdd(Integer activityDurationAdd) {
        this.activityDurationAdd = activityDurationAdd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityScheduleDTO)) return false;
        ActivityScheduleDTO that = (ActivityScheduleDTO) o;
        return Objects.equals(activityDateAdd, that.activityDateAdd) &&
                Objects.equals(activityNameAdd, that.activityNameAdd) &&
                Objects.equals(activityDurationAdd, that.activityDurationAdd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activityDateAdd, activityNameAdd, activityDurationAdd);
    }
}