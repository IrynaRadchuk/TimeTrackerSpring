package com.example.service.impl;

import com.example.exception.RecordExistException;
import com.example.model.Activity;
import com.example.model.Category;
import com.example.model.dto.ActivityAddDTO;
import com.example.model.dto.ActivityChangeDTO;
import com.example.model.repository.ActivityRepository;
import com.example.service.ActivityService;
import com.example.service.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Service to manage activities
 *
 * @author Iryna Radchuk
 */

@Service
@Log4j2
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
    @Transactional
    public void addActivityByAdmin(ActivityAddDTO activityAddDTO) throws RecordExistException {
        Activity activityFromDB = activityRepository.findByActivityName(activityAddDTO.getActivityAddName());
        if (Objects.nonNull(activityFromDB)) {
            log.error("This activity already exists");
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
    @Transactional
    public void changeActivityByAdmin(ActivityChangeDTO activityChangeDTO) throws RecordExistException {
        Category category = categoryService.categoryByName(activityChangeDTO.getCategoryList());
        Activity activity = activityRepository.findById(activityChangeDTO.getId()).orElseThrow();
        Activity activityFromDB = activityRepository.findByActivityName(activityChangeDTO.getActivityName());
        if (Objects.nonNull(activityFromDB) && !activityFromDB.equals(activity)) {
            log.error("This activity already exists");
            throw new RecordExistException("This activity already exists");
        }
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
