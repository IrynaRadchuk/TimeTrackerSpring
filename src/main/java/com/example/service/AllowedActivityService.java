package com.example.service;

import com.example.model.AllowedActivity;
import com.example.model.dto.AdminRequestDTO;

import java.util.List;

/**
 * Service to manage allowed activities
 *
 * @author Iryna Radchuk
 */
public interface AllowedActivityService {

    /**
     * Get all allowed activities for a user
     */
    List<AllowedActivity> getAllowedActivities(Long id);

    /**
     * Request new activity by user
     */
    void requestActivity(Long id, String requestActivity);

    /**
     * Get list of all activities approved for a user
     */
    List<AllowedActivity> activityApproved(Long id);

    /**
     * Get list of all activities pending for a user
     */
    List<AllowedActivity> activitiesPending();

    /**
     * Approve activity by administrator
     */
    void approveActivity(AdminRequestDTO adminRequestDTO);

    /**
     * Deny activity by administrator
     */
    void denyActivity(AdminRequestDTO adminRequestDTO);
}
