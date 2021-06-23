package com.example.controller;

import com.example.model.*;
import com.example.model.repository.ActivityRepository;
import com.example.model.repository.AllowedActivityRepository;
import com.example.model.repository.UserActivityRepository;
import com.example.model.repository.UserRepository;
import com.example.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AllowedActivityRepository allowedActivityRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserActivityRepository userActivityRepository;

    @GetMapping("/tracker/userProfile")
    public String userProfile(Model model) {
        MyUserDetails userFromContext = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = userFromContext.getUser().getId();
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(value -> model.addAttribute("user", value));
        return "userProfile";
    }

    @GetMapping("/tracker/updateProfile")
    public String updateProfile(Model model) {
        MyUserDetails userFromContext = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = userFromContext.getUser().getId();
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(value -> model.addAttribute("user", value));
        return "updateProfile";
    }

    @PostMapping("/tracker/updateProfile")
    public String updateProfilePost(@RequestParam String userEmail,
                                    @RequestParam String userPassword,
                                    @RequestParam String userFirstName,
                                    @RequestParam String userLastName,
                                    @RequestParam String userPasswordConfirm,
                                    Model model) {
        MyUserDetails userFromContext = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = userFromContext.getUser().getId();
        User user = userRepository.findById(id).orElseThrow();
        model.addAttribute("user", user);
        String pass = user.getUserPassword();
        if (!userPasswordConfirm.equals(pass)) {
            return "updateProfile";
        }
        user.setUserEmail(userEmail);
        user.setUserFirstName(userFirstName);
        user.setUserLastName(userLastName);
        user.setUserPassword(userPassword);
        userRepository.save(user);
        return "redirect:userProfile";
    }

    @GetMapping("/tracker/userRequests")
    public String userRequests(Model model) {
        MyUserDetails userFromContext = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = userFromContext.getUser().getId();
        List<AllowedActivity> activity = allowedActivityRepository.activitiesByUser(id);
        List<String>allActivities = activityRepository.absentActivities(id);
        List<String>allActivitiesUa = activityRepository.absentActivitiesUa(id);
        model.addAttribute("activity", activity);
        model.addAttribute("allActivities", allActivities);
        model.addAttribute("allActivitiesUa", allActivitiesUa);
        return "userRequests";
    }

    @PostMapping("/tracker/request")
    public String request(@RequestParam String requestActivity) {
        MyUserDetails userFromContext = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = userFromContext.getUser().getId();
        allowedActivityRepository.request(id,requestActivity);
        return "redirect:userRequests";
    }

    @GetMapping("/tracker/schedule")
    public String schedule(Model model) {
        MyUserDetails userFromContext = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = userFromContext.getUser().getId();
        List<UserActivity> userActivity = userActivityRepository.activitiesByUser(id);
        model.addAttribute("userActivity", userActivity);
        List<AllowedActivity> activityApproved = allowedActivityRepository.activitiesByUserApproved(id);
        model.addAttribute("activityApproved", activityApproved);
        return "schedule";
    }

    @PostMapping("/tracker/addSchedule")
    public String addSchedule(@RequestParam String activityDateAdd,
                              @RequestParam String activityNameAdd,
                              @RequestParam Integer activityDurationAdd,
                              Map<String, Object> model) {
        MyUserDetails userFromContext = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = userFromContext.getUser().getId();
        userActivityRepository.saveActivityForUser(id, activityNameAdd, LocalDate.parse(activityDateAdd), activityDurationAdd);
        return "redirect:schedule";
    }

    @PostMapping("/tracker/deleteSchedule")
    public String deleteSchedule(@RequestParam String activityDate,
                                 @RequestParam String activityList) {
        MyUserDetails userFromContext = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = userFromContext.getUser().getId();
        userActivityRepository.deleteActivityForUser(id, activityList, LocalDate.parse(activityDate));
        return "redirect:schedule";
    }

}
