package com.example.service;

import com.example.exception.RecordExistException;
import com.example.model.Activity;
import com.example.model.dto.ActivityAddDTO;
import com.example.model.dto.ActivityChangeDTO;

import java.util.List;

public interface ActivityService {
    List<Activity> getActivityList(Long id);

    void addActivityByAdmin(ActivityAddDTO activityAddDTO) throws RecordExistException;

    void deleteActivityByAdmin(Long id);

    void changeActivityByAdmin(ActivityChangeDTO activityChangeDTO) throws RecordExistException;

    Iterable<Activity> activities();

    List<Activity> activityStat();
}
