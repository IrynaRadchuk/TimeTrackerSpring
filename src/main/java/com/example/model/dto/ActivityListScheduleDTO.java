package com.example.model.dto;

import com.example.model.AllowedActivity;
import com.example.model.UserActivity;

import java.util.List;
import java.util.Objects;

public class ActivityListScheduleDTO {
    private List<UserActivity> userActivity;
    private List<AllowedActivity> activityApproved;

    public ActivityListScheduleDTO(List<UserActivity> userActivity, List<AllowedActivity> activityApproved) {
        this.userActivity = userActivity;
        this.activityApproved = activityApproved;
    }

    public List<UserActivity> getUserActivity() {
        return userActivity;
    }

    public void setUserActivity(List<UserActivity> userActivity) {
        this.userActivity = userActivity;
    }

    public List<AllowedActivity> getActivityApproved() {
        return activityApproved;
    }

    public void setActivityApproved(List<AllowedActivity> activityApproved) {
        this.activityApproved = activityApproved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityListScheduleDTO)) return false;
        ActivityListScheduleDTO that = (ActivityListScheduleDTO) o;
        return Objects.equals(userActivity, that.userActivity) &&
                Objects.equals(activityApproved, that.activityApproved);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userActivity, activityApproved);
    }
}
