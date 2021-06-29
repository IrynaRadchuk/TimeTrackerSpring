package com.example.service;

import com.example.exception.RecordExistException;
import com.example.model.Activity;
import com.example.model.dto.ActivityAddDTO;
import com.example.model.dto.ActivityChangeDTO;

import java.util.List;

/**
 * Service to manage activities
 *
 * @author Iryna Radchuk
 */
public interface ActivityService {

    /**
     * Get list of activities that are not assigned to the user
     */
    List<Activity> getActivityList(Long id);

    /**
     * Insert new activity by administrator
     */
    void addActivityByAdmin(ActivityAddDTO activityAddDTO) throws RecordExistException;

    /**
     * Delete existed activity by administrator
     */
    void deleteActivityByAdmin(Long id);

    /**
     * Change existed activity by administrator
     */
    void changeActivityByAdmin(ActivityChangeDTO activityChangeDTO) throws RecordExistException;

    /**
     * Get list of all activities
     */
    Iterable<Activity> activities();

    /**
     * Get list of activities with users sum
     */
    List<Activity> activityStat();
}
