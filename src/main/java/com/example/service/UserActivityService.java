package com.example.service;

import com.example.exception.RecordExistException;
import com.example.model.UserActivity;
import com.example.model.dto.ActivityScheduleDTO;
import com.example.model.dto.ActivityScheduleDeleteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service to manage activities schedule for users
 *
 * @author Iryna Radchuk
 */
public interface UserActivityService {

    /**
     * Get all activities for a user
     */
    List<UserActivity> userActivity(Long id);

    /**
     * Delete activity from schedule by user
     */
    void deleteActivityForUser(Long id, ActivityScheduleDeleteDTO activityScheduleDeleteDTO);

    /**
     * Add activity to schedule by user
     */
    void addActivityToSchedule(Long id, ActivityScheduleDTO activityScheduleDTO) throws RecordExistException;

    /**
     * Get page of user activities
     */
    Page<UserActivity> onePageActivities(Pageable pageable);
}
