package com.example.controller;

import com.example.exception.RecordExistException;
import com.example.exception.TimeTrackerException;
import com.example.model.User;
import com.example.model.dto.*;
import com.example.security.MyUserDetails;
import com.example.service.ActivityService;
import com.example.service.AllowedActivityService;
import com.example.service.UserActivityService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller that reacts to requests on user pages
 *
 * @author Iryna Radchuk
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private AllowedActivityService allowedActivityService;

    @Autowired
    private UserActivityService userActivityService;


    @GetMapping("/tracker/userProfile")
    public String userProfile(Model model) {
        Long id = getUserSessionId();
        Optional<User> user = userService.userByIdSearch(id);
        user.ifPresent(value -> model.addAttribute("user", value));
        return "userProfile";
    }

    @GetMapping("/tracker/updateProfile")
    public String updateProfile(Model model) {
        Long id = getUserSessionId();
        Optional<User> user = userService.userByIdSearch(id);
        user.ifPresent(value -> model.addAttribute("user", value));
        return "updateProfile";
    }

    @PostMapping("/tracker/updateProfile")
    public String updateProfilePost(@ModelAttribute("user") @Valid UserUpdateDto userUpdateDto,
                                    BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            String collect = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("; "));
            redirect.addFlashAttribute("errors", collect);
            return "redirect:updateProfile";
        }
        Long id = getUserSessionId();
        try {
            userService.updateUser(userUpdateDto, id);
            redirect.addFlashAttribute("messages", "Profile successfully updated");
            return "redirect:userProfile";
        } catch (TimeTrackerException e) {
            redirect.addFlashAttribute("errors", e.getMessage());
            return "redirect:updateProfile";
        }
    }

    @GetMapping("/tracker/userRequests")
    public String userRequests(Model model) {
        Long id = getUserSessionId();
        UserRequestsDTO userRequestsDTO = new UserRequestsDTO(allowedActivityService.getAllowedActivities(id), activityService.getActivityList(id));
        model.addAttribute("activityList", userRequestsDTO);
        return "userRequests";
    }

    @PostMapping("/tracker/request")
    public String request(@RequestParam String requestActivity, RedirectAttributes redirect) {
        allowedActivityService.requestActivity(getUserSessionId(), requestActivity);
        redirect.addFlashAttribute("messages", "Activity successfully requested");
        return "redirect:userRequests";
    }

    @GetMapping("/tracker/schedule")
    public String schedule(Model model) {
        Long id = getUserSessionId();
        ActivityListScheduleDTO activityListScheduleDTO = new ActivityListScheduleDTO(userActivityService.userActivity(id), allowedActivityService.activityApproved(id));
        model.addAttribute("activityList", activityListScheduleDTO);
        return "schedule";
    }

    @PostMapping("/tracker/addSchedule")
    public String addSchedule(@ModelAttribute("activityAdd") @Valid ActivityScheduleDTO activityScheduleDTO,
                              BindingResult result, RedirectAttributes redirect) {
        Long id = getUserSessionId();
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            String collect = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("; "));
            redirect.addFlashAttribute("errors", collect);
            return "redirect:schedule";
        }
        try {
            userActivityService.addActivityToSchedule(id, activityScheduleDTO);
            redirect.addFlashAttribute("messages", "Activity successfully stored to schedule");
            return "redirect:schedule";
        } catch (RecordExistException e) {
            redirect.addFlashAttribute("errors", e.getMessage());
            return "redirect:schedule";
        }
    }

    @PostMapping("/tracker/deleteSchedule")
    public String deleteSchedule(@ModelAttribute("activityDelete") @Valid ActivityScheduleDeleteDTO activityScheduleDeleteDTO,
                                 RedirectAttributes redirect) {
        userActivityService.deleteActivityForUser(getUserSessionId(), activityScheduleDeleteDTO);
        redirect.addFlashAttribute("messages", "Activity successfully deleted from schedule");
        return "redirect:schedule";
    }

    private Long getUserSessionId() {
        MyUserDetails userFromContext = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userFromContext.getUser().getId();
    }
}
