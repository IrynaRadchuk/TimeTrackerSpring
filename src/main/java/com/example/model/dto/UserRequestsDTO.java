package com.example.model.dto;

import com.example.model.Activity;
import com.example.model.AllowedActivity;

import java.util.List;

public class UserRequestsDTO {
    private List<AllowedActivity> allowedActivity;
    private List<Activity> activity;

    public UserRequestsDTO(List<AllowedActivity> allowedActivity, List<Activity> activity) {
        this.allowedActivity = allowedActivity;
        this.activity = activity;
    }

    public List<AllowedActivity> getAllowedActivity() {
        return allowedActivity;
    }

    public void setAllowedActivity(List<AllowedActivity> allowedActivity) {
        this.allowedActivity = allowedActivity;
    }

    public List<Activity> getActivity() {
        return activity;
    }

    public void setActivity(List<Activity> activity) {
        this.activity = activity;
    }
}
