package com.example.service;

import com.example.model.Activity;
import com.example.model.dto.ActivityScheduleDTO;

import java.util.List;

public interface ActivityService {
    List<Activity> getActivityList(Long id);

}
