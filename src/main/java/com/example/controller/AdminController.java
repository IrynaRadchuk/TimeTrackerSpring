package com.example.controller;

import com.example.model.Activity;
import com.example.model.Role;
import com.example.model.User;
import com.example.model.UserActivity;
import com.example.model.repository.ActivityRepository;
import com.example.model.repository.UserActivityRepository;
import com.example.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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
    public String statUsers(Model model,
                            @PageableDefault(size = 8) Pageable pageable) {
        Page<UserActivity> userActivities = userActivityRepository.findAll(pageable);
        model.addAttribute("number", userActivities.getNumber());
        model.addAttribute("totalPages", userActivities.getTotalPages());
        model.addAttribute("totalElements",
                userActivities.getTotalElements());
        model.addAttribute("size", userActivities.getSize());
        model.addAttribute("users", userActivities.getContent());
        model.addAttribute("userActivities", userActivities);
        return "statUsers";
    }

//    @PostMapping
//    public User addUser (@RequestBody User user){
//        userRepository.save(user);
//        return user;
//    }
//    @PostMapping
//    public void deleteUser (@RequestBody User user){
//        userRepository.delete(user);
//    }
//    @GetMapping("/tracker/adminRequests")
//    public String adminRequests(Model model) {
//        Iterable<UserActivity> userActivities = userActivityRepository.findAll();
//        model.addAttribute("userActivities", userActivities);
//        return "adminRequests";
//    }
}
