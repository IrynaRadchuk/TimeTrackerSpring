package com.example.controller;

import com.example.model.Activity;
import com.example.model.Role;
import com.example.model.User;
import com.example.model.UserActivity;
import com.example.model.repository.ActivityRepository;
import com.example.model.repository.UserActivityRepository;
import com.example.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserActivityRepository userActivityRepository;

    @GetMapping("/tracker/adminUsers")
    public String adminUsers(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "adminUsers";
    }

    @GetMapping("/tracker/adminActivities")
    public String adminActivities(Model model) {
        Iterable<Activity> activities = activityRepository.findAll();
        model.addAttribute("activities", activities);
        return "adminActivities";
    }

    @GetMapping("/tracker/statActivities")
    public String statActivities(Model model) {
        Iterable<Activity> activityStat = activityRepository.findAll();
        model.addAttribute("activityStat", activityStat);
        return "statActivities";
    }
    @GetMapping("/tracker/statUsers")
    public String statUsers(Model model) {
        Iterable<UserActivity> userActivities = userActivityRepository.findAll();;
        model.addAttribute("userActivities", userActivities);
        return "statUsers";
    }
//    @GetMapping("/tracker/adminRequests")
//    public String adminRequests(Model model) {
//        Iterable<UserActivity> userActivities = userActivityRepository.findAll();
//        model.addAttribute("userActivities", userActivities);
//        return "adminRequests";
//    }
}
