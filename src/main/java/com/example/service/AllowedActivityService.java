package com.example.service;

import com.example.model.AllowedActivity;
import com.example.model.dto.AdminRequestDTO;

import java.util.List;

public interface AllowedActivityService {
    List<AllowedActivity> getAllowedActivities(Long id);

    void requestActivity(Long id, String requestActivity);

    List<AllowedActivity> activityApproved(Long id);

    List<AllowedActivity> activitiesPending();

    void approveActivity(AdminRequestDTO adminRequestDTO);

    void denyActivity(AdminRequestDTO adminRequestDTO);
}
