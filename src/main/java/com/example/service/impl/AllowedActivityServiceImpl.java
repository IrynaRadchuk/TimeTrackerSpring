package com.example.service.impl;

import com.example.model.AllowedActivity;
import com.example.model.dto.AdminRequestDTO;
import com.example.model.repository.AllowedActivityRepository;
import com.example.service.AllowedActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllowedActivityServiceImpl implements AllowedActivityService {

    @Autowired
    private AllowedActivityRepository allowedActivityRepository;

    @Override
    public List<AllowedActivity> getAllowedActivities(Long id) {
        return allowedActivityRepository.activitiesByUser(id);
    }

    @Override
    public void requestActivity(Long id, String requestActivity) {
        allowedActivityRepository.request(id, requestActivity);
    }

    @Override
    public List<AllowedActivity> activityApproved(Long id) {
        return allowedActivityRepository.activitiesByUserApproved(id);
    }

    @Override
    public List<AllowedActivity> activitiesPending() {
        return allowedActivityRepository.activitiesPending();
    }

    @Override
    public void approveActivity(AdminRequestDTO adminRequestDTO) {
        allowedActivityRepository.approveActivity(adminRequestDTO.getUserId(), adminRequestDTO.getActivityName());
    }

    @Override
    public void denyActivity(AdminRequestDTO adminRequestDTO) {
        allowedActivityRepository.denyActivity(adminRequestDTO.getUserId(), adminRequestDTO.getActivityName());
    }
}
