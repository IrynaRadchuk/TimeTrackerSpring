package com.example.service.impl;

import com.example.model.Activity;
import com.example.model.repository.ActivityRepository;
import com.example.model.repository.UserRepository;
import com.example.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public List<Activity> getActivityList(Long id) {
        return activityRepository.absent(id);
    }
}
