package com.example.service.impl;

import com.example.exception.RecordExistException;
import com.example.model.Activity;
import com.example.model.Category;
import com.example.model.dto.ActivityAddDTO;
import com.example.model.dto.ActivityChangeDTO;
import com.example.model.repository.ActivityRepository;
import com.example.service.ActivityService;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private CategoryService categoryService;

    @Override
    public List<Activity> getActivityList(Long id) {
        return activityRepository.absent(id);
    }

    @Override
    public void addActivityByAdmin(ActivityAddDTO activityAddDTO) throws RecordExistException {
        Optional<Activity> activityFromDB = activityRepository.findByActivityName(activityAddDTO.getActivityAddName());
        if (activityFromDB.isPresent()) {
            throw new RecordExistException("This activity already exists");
        }
        Category category = categoryService.categoryByName(activityAddDTO.getCategoryAdd());
        Activity activity = new Activity(activityAddDTO.getActivityAddName(), activityAddDTO.getActivityAddUa(), category);
        activityRepository.save(activity);
    }

    @Override
    public void deleteActivityByAdmin(Long id) {
        activityRepository.deleteById(id);
    }

    @Override
    public void changeActivityByAdmin(ActivityChangeDTO activityChangeDTO) throws RecordExistException {
        Optional<Activity> activityFromDB = activityRepository.findByActivityName(activityChangeDTO.getActivityName());
        if (activityFromDB.isPresent()) {
            throw new RecordExistException("This activity already exists");
        }
        Category category = categoryService.categoryByName(activityChangeDTO.getCategoryList());
        Activity activity = activityRepository.findById(activityChangeDTO.getId()).orElseThrow();
        activity.setActivityName(activityChangeDTO.getActivityName());
        activity.setActivityUa(activityChangeDTO.getActivityUa());
        activity.setCategory(category);
        activityRepository.save(activity);
    }

    @Override
    public Iterable<Activity> activities() {
        return activityRepository.findAll();
    }

    @Override
    public List<Activity> activityStat() {
        return activityRepository.activityWithUsersCount();
    }

}
