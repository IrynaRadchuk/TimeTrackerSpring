package com.example.service;

import com.example.exception.RecordExistException;
import com.example.model.UserActivity;
import com.example.model.dto.ActivityScheduleDTO;
import com.example.model.dto.ActivityScheduleDeleteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserActivityService {
    List<UserActivity> userActivity(Long id);

    void deleteActivityForUser(Long id, ActivityScheduleDeleteDTO activityScheduleDeleteDTO);

    void addActivityToSchedule(Long id, ActivityScheduleDTO activityScheduleDTO) throws RecordExistException;

    Page<UserActivity> onePageActivities(Pageable pageable);
}
