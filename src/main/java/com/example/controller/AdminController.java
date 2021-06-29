package com.example.controller;

import com.example.exception.RecordExistException;
import com.example.model.Activity;
import com.example.model.AllowedActivity;
import com.example.model.User;
import com.example.model.UserActivity;
import com.example.model.dto.*;
import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Controller that reacts to requests on admin pages
 *
 * @author Iryna Radchuk
 */
@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private AllowedActivityService allowedActivityService;

    @Autowired
    private UserActivityService userActivityService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/tracker/adminUsers")
    public String adminUsers(Model model) {
        Iterable<User> users = userService.usersFind();
        model.addAttribute("users", users);
        return "adminUsers";
    }

    @GetMapping("/tracker/adminActivities")
    public String adminActivities(Model model) {
        ActivityCategoryDTO activityCategoryDTO = new ActivityCategoryDTO(activityService.activities(), categoryService.categories());
        model.addAttribute("activityCategory", activityCategoryDTO);
        return "adminActivities";
    }

    @GetMapping("/tracker/statActivities")
    public String statActivities(Model model) {
        List<Activity> activityStat = activityService.activityStat();
        model.addAttribute("activityStat", activityStat);
        return "statActivities";
    }

    @GetMapping("/tracker/statUsers")
    public String statUsers(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size, Model model) {
        page = Objects.isNull(page) ? 0 : page;
        size = Objects.isNull(size) || size == 0 ? 10 : size;
        Pageable sortedByDateDesc =
                PageRequest.of(page, size, Sort.by("activityDate").descending());
        Page<UserActivity> onePageActivities = userActivityService.onePageActivities(sortedByDateDesc);
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
        List<AllowedActivity> activities = allowedActivityService.activitiesPending();
        model.addAttribute("activities", activities);
        return "adminRequests";
    }

    @PostMapping("/tracker/approveRequest")
    public String approveRequest(@ModelAttribute("request") @Valid AdminRequestDTO adminRequestDTO,
                                 RedirectAttributes redirect) {
        allowedActivityService.approveActivity(adminRequestDTO);
        redirect.addFlashAttribute("messages", "Request successfully approved");
        return "redirect:adminRequests";
    }

    @PostMapping("/tracker/denyRequest")
    public String denyRequest(@ModelAttribute("request") @Valid AdminRequestDTO adminRequestDTO,
                              RedirectAttributes redirect) {
        allowedActivityService.denyActivity(adminRequestDTO);
        redirect.addFlashAttribute("messages", "Request successfully denied");
        return "redirect:adminRequests";
    }

    @PostMapping("/tracker/addUser")
    public String addUser(@ModelAttribute("addUsers") @Valid AdminUserAddDTO adminUserAddDTO,
                          BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            String collect = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("; "));
            redirect.addFlashAttribute("errors", collect);
            return "redirect:adminUsers";
        }
        try {
            userService.userAddByAdmin(adminUserAddDTO);
            redirect.addFlashAttribute("messages", "User successfully added");
            return "redirect:adminUsers";
        } catch (RecordExistException e) {
            redirect.addFlashAttribute("errors", e.getMessage());
            return "redirect:adminUsers";
        }

    }

    @PostMapping("/tracker/deleteUser")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUserById(id);
        return "redirect:adminUsers";
    }

    @PostMapping("/tracker/changeUser")
    public String changeUser(@ModelAttribute("adminUsers") @Valid AdminUserDTO adminUserDTO,
                             BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            String collect = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("; "));
            redirect.addFlashAttribute("errors", collect);
            return "redirect:adminUsers";
        }
        try {
            userService.userChangeByAdmin(adminUserDTO);
            redirect.addFlashAttribute("messages", "User successfully updated");
            return "redirect:adminUsers";
        } catch (RecordExistException e) {
            redirect.addFlashAttribute("errors", e.getMessage());
            return "redirect:adminUsers";
        }
    }


    @PostMapping("/tracker/addActivity")
    public String addActivity(@ModelAttribute("adminActivities") @Valid ActivityAddDTO activityAddDTO,
                              BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            String collect = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("; "));
            redirect.addFlashAttribute("errors", collect);
            return "redirect:adminActivities";
        }
        try {
            activityService.addActivityByAdmin(activityAddDTO);
            redirect.addFlashAttribute("messages", "Activity successfully added");
            return "redirect:adminActivities";
        } catch (RecordExistException e) {
            redirect.addFlashAttribute("errors", e.getMessage());
            return "redirect:adminActivities";
        }
    }

    @PostMapping("/tracker/deleteActivity")
    public String deleteActivity(@RequestParam Long id, RedirectAttributes redirect) {
        activityService.deleteActivityByAdmin(id);
        redirect.addFlashAttribute("messages", "Activity successfully deleted");
        return "redirect:adminActivities";
    }

    @PostMapping("/tracker/changeActivity")
    public String changeActivity(@ModelAttribute("changeActivities") @Valid ActivityChangeDTO activityChangeDTO,
                                 BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            String collect = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("; "));
            redirect.addFlashAttribute("errors", collect);
            return "redirect:adminActivities";
        }
        try {
            activityService.changeActivityByAdmin(activityChangeDTO);
            redirect.addFlashAttribute("messages", "Activity successfully changed");
            return "redirect:adminActivities";
        } catch (RecordExistException e) {
            redirect.addFlashAttribute("errors", e.getMessage());
            return "redirect:adminActivities";
        }

    }
}
