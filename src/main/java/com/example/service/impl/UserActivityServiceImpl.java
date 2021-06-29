package com.example.service.impl;

import com.example.exception.RecordExistException;
import com.example.model.UserActivity;
import com.example.model.dto.ActivityScheduleDTO;
import com.example.model.dto.ActivityScheduleDeleteDTO;
import com.example.model.repository.UserActivityRepository;
import com.example.service.UserActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Service to manage activities schedule for users
 *
 * @author Iryna Radchuk
 */
@Service
public class UserActivityServiceImpl implements UserActivityService {

    @Autowired
    private UserActivityRepository userActivityRepository;

    @Override
    public List<UserActivity> userActivity(Long id) {
        return userActivityRepository.activitiesByUser(id);
    }

    @Override
    public void deleteActivityForUser(Long id, ActivityScheduleDeleteDTO activityScheduleDeleteDTO) {
        userActivityRepository.deleteActivityForUser(id, activityScheduleDeleteDTO.getActivityName(), LocalDate.parse(activityScheduleDeleteDTO.getActivityDate()));
    }

    @Override
    public void addActivityToSchedule(Long id, ActivityScheduleDTO activityScheduleDTO) throws RecordExistException {
        if (Objects.nonNull(userActivityRepository.activityByUserDate(id, activityScheduleDTO.getActivityNameAdd(), LocalDate.parse(activityScheduleDTO.getActivityDateAdd())))) {
            throw new RecordExistException("This activity is already stored for this date");
        }
        userActivityRepository.saveActivityForUser(id,
                activityScheduleDTO.getActivityNameAdd(),
                LocalDate.parse(activityScheduleDTO.getActivityDateAdd()),
                activityScheduleDTO.getActivityDurationAdd());
    }

    @Override
    public Page<UserActivity> onePageActivities(Pageable pageable) {
        return userActivityRepository.findAll(pageable);
    }
}
