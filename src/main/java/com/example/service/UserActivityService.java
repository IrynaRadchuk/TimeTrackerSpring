package com.example.service;

import com.example.exception.RecordExistException;
import com.example.model.UserActivity;
import com.example.model.dto.ActivityScheduleDTO;
import com.example.model.dto.ActivityScheduleDeleteDTO;

import java.util.List;

public interface UserActivityService {
    List<UserActivity> userActivity(Long id);
    void deleteActivityForUser(Long id, ActivityScheduleDeleteDTO activityScheduleDeleteDTO);
    void addActivityToSchedule(Long id, ActivityScheduleDTO activityScheduleDTO) throws RecordExistException;
}
