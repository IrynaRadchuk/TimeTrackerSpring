package com.example.model.dto;

import com.example.model.AllowedActivity;
import com.example.model.UserActivity;

import java.util.List;

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

}
