package com.example.model.dto;

import com.example.model.Activity;
import com.example.model.AllowedActivity;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRequestsDTO)) return false;
        UserRequestsDTO that = (UserRequestsDTO) o;
        return Objects.equals(allowedActivity, that.allowedActivity) &&
                Objects.equals(activity, that.activity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(allowedActivity, activity);
    }
}
