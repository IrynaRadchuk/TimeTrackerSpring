package com.example.model.dto;


import java.util.Objects;

public class ActivityScheduleDeleteDTO {
    private String activityDate;
    private String activityName;

    public ActivityScheduleDeleteDTO(String activityDate, String activityName) {
        this.activityDate = activityDate;
        this.activityName = activityName;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityScheduleDeleteDTO)) return false;
        ActivityScheduleDeleteDTO that = (ActivityScheduleDeleteDTO) o;
        return Objects.equals(activityDate, that.activityDate) &&
                Objects.equals(activityName, that.activityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activityDate, activityName);
    }
}
