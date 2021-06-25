package com.example.service;

import com.example.model.AllowedActivity;

import java.util.List;

public interface AllowedActivityService {
    List<AllowedActivity> getAllowedActivities(Long id);
    void requestActivity(Long id, String requestActivity);
    List<AllowedActivity> activityApproved(Long id);
}
