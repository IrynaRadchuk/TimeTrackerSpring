package com.example.controller;

import com.example.model.*;
import com.example.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserActivityRepository userActivityRepository;

    @Autowired
    private AllowedActivityRepository allowedActivityRepository;

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
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "adminActivities";
    }

    @GetMapping("/tracker/statActivities")
    public String statActivities(Model model) {
        List<Activity> activityStat = activityRepository.activityWithUsersCount();
        model.addAttribute("activityStat", activityStat);
        //List<Integer> users = allowedActivityRepository.countUsers();
        return "statActivities";
    }

    @GetMapping("/tracker/statUsers")
    public String statUsers(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size, Model model) {
        page = Objects.isNull(page)? 0 : page;
        size = Objects.isNull(size)||size==0? 10 : size;
        Pageable sortedByDateDesc =
                PageRequest.of(page, size, Sort.by("activityDate").descending());
        Page<UserActivity> onePageActivities = userActivityRepository.findAll(sortedByDateDesc);
        model.addAttribute("number", onePageActivities.getNumber());
        model.addAttribute("totalPages", onePageActivities.getTotalPages());
        model.addAttribute("totalElements", onePageActivities.getTotalElements());
        model.addAttribute("size", onePageActivities.getSize());
        model.addAttribute("users", onePageActivities.getContent());
        model.addAttribute("userActivities", onePageActivities);
        return "statUsers";
    }

    @GetMapping("/tracker/adminRequests")
    public String adminRequests(Model model) {
        List<AllowedActivity> activities = allowedActivityRepository.activitiesPending();
        model.addAttribute("activities", activities);
        return "adminRequests";
    }

    @PostMapping("/tracker/approveRequest")
    public String approveRequest(@RequestParam Long userId,
                                 @RequestParam String activityName,
                                 Map<String, Object> model) {
        allowedActivityRepository.approveActivity(userId, activityName);
        return "redirect:adminRequests";
    }

    @PostMapping("/tracker/denyRequest")
    public String denyRequest(@RequestParam Long userId,
                              @RequestParam String activityName,
                              Map<String, Object> model) {
        allowedActivityRepository.denyActivity(userId, activityName);
        return "redirect:adminRequests";
    }

    @PostMapping("/tracker/addUser")
    public String addUser(@RequestParam String userAddEmail,
                          @RequestParam String userAddFirstName,
                          @RequestParam String userAddLastName,
                          @RequestParam String roleAdd,
                          Map<String, Object> model) {
        Role role = new Role(RoleName.valueOf(roleAdd));
        User user = new User(userAddEmail, userAddFirstName, userAddLastName, role);
        userRepository.save(user);
        return "redirect:adminUsers";
    }

    @PostMapping("/tracker/deleteUser")
    public String deleteUser(@RequestParam Long id) {
        userRepository.deleteById(id);
        return "redirect:adminUsers";
    }

    @PostMapping("/tracker/changeUser")
    public String changeUser(@RequestParam Long id,
                             @RequestParam String userEmail,
                             @RequestParam String userFirstName,
                             @RequestParam String userLastName,
                             @RequestParam String roleList,
                             Map<String, Object> model) {
        User user = userRepository.findById(id).orElseThrow();
        user.setUserEmail(userEmail);
        user.setUserFirstName(userFirstName);
        user.setUserLastName(userLastName);
        user.setRole(new Role(RoleName.valueOf(roleList)));
        userRepository.save(user);
        return "redirect:adminUsers";
    }


    @PostMapping("/tracker/addActivity")
    public String addActivity(@RequestParam String activityAddName,
                              @RequestParam String activityAddUa,
                              @RequestParam String categoryAdd,
                              Map<String, Object> model) {
        Category category = categoryRepository.categoryIdByName(categoryAdd);
        Activity activity = new Activity(activityAddName, activityAddUa, category);
        activityRepository.save(activity);
        return "redirect:adminActivities";
    }

    @PostMapping("/tracker/deleteActivity")
    public String deleteActivity(@RequestParam Long id) {
        activityRepository.deleteById(id);
        return "redirect:adminActivities";
    }

    @PostMapping("/tracker/changeActivity")
    public String changeActivity(@RequestParam Long id,
                                 @RequestParam String activityName,
                                 @RequestParam String activityUa,
                                 @RequestParam String categoryList,
                                 Map<String, Object> model) {
        Activity activity = activityRepository.findById(id).orElseThrow();
        Category category = categoryRepository.categoryIdByName(categoryList);
        activity.setActivityName(activityName);
        activity.setActivityUa(activityUa);
        activity.setCategory(category);
        activityRepository.save(activity);
        return "redirect:adminActivities";
    }
}
