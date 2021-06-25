package com.example.controller;

import com.example.model.*;
import com.example.model.dto.*;
import com.example.model.repository.*;
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
import java.util.Optional;
import java.util.stream.Collectors;

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
        ActivityCategoryDTO activityCategoryDTO = new ActivityCategoryDTO(activityRepository.findAll(), categoryRepository.findAll());
        model.addAttribute("activityCategory", activityCategoryDTO);
        return "adminActivities";
    }

    @GetMapping("/tracker/statActivities")
    public String statActivities(Model model) {
        List<Activity> activityStat = activityRepository.activityWithUsersCount();
        model.addAttribute("activityStat", activityStat);
        return "statActivities";
    }

    @GetMapping("/tracker/statUsers")
    public String statUsers(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size, Model model) {
        page = Objects.isNull(page) ? 0 : page;
        size = Objects.isNull(size) || size == 0 ? 10 : size;
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
    public String approveRequest(@ModelAttribute("request") @Valid AdminRequestDTO adminRequestDTO,
                                 RedirectAttributes redirect) {
        allowedActivityRepository.approveActivity(adminRequestDTO.getUserId(), adminRequestDTO.getActivityName());
        redirect.addFlashAttribute("messages", "Request successfully approved");
        return "redirect:adminRequests";
    }

    @PostMapping("/tracker/denyRequest")
    public String denyRequest(@ModelAttribute("request") @Valid AdminRequestDTO adminRequestDTO,
                              RedirectAttributes redirect) {
        allowedActivityRepository.denyActivity(adminRequestDTO.getUserId(), adminRequestDTO.getActivityName());
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
        Optional<User> userFromDB = userRepository.findByUserEmail(adminUserAddDTO.getUserAddEmail());
        if (userFromDB.isPresent()){
            redirect.addFlashAttribute("errors", "This email is already in use");
            return "redirect:adminUsers";
        }
        Role role = new Role(RoleName.valueOf(adminUserAddDTO.getRoleAdd()));
        User user = new User(adminUserAddDTO.getUserAddEmail(), adminUserAddDTO.getUserAddFirstName(), adminUserAddDTO.getUserAddLastName(), role);
        userRepository.save(user);
        redirect.addFlashAttribute("messages", "User successfully added");
        return "redirect:adminUsers";
    }

    @PostMapping("/tracker/deleteUser")
    public String deleteUser(@RequestParam Long id) {
        userRepository.deleteById(id);
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
        Optional<User> userFromDB = userRepository.findByUserEmail(adminUserDTO.getUserEmail());
        if (userFromDB.isPresent()){
            redirect.addFlashAttribute("errors", "This email is already in use");
            return "redirect:adminUsers";
        }
        User user = userRepository.findById(adminUserDTO.getId()).orElseThrow();
        user.setUserEmail(adminUserDTO.getUserEmail());
        user.setUserFirstName(adminUserDTO.getUserFirstName());
        user.setUserLastName(adminUserDTO.getUserLastName());
        user.setRole(new Role(RoleName.valueOf(adminUserDTO.getRoleList())));
        userRepository.save(user);
        redirect.addFlashAttribute("messages", "User successfully updated");
        return "redirect:adminUsers";
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
        Optional<Activity> activityFromDB = activityRepository.findByActivityName(activityAddDTO.getActivityAddName());
        if (activityFromDB.isPresent()){
            redirect.addFlashAttribute("errors", "This activity already exists");
            return "redirect:adminActivities";
        }
        Category category = categoryRepository.categoryIdByName(activityAddDTO.getCategoryAdd());
        Activity activity = new Activity(activityAddDTO.getActivityAddName(), activityAddDTO.getActivityAddUa(), category);
        activityRepository.save(activity);
        redirect.addFlashAttribute("messages", "Activity successfully added");
        return "redirect:adminActivities";
    }

    @PostMapping("/tracker/deleteActivity")
    public String deleteActivity(@RequestParam Long id, RedirectAttributes redirect) {
        activityRepository.deleteById(id);
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
        Optional<Activity> activityFromDB = activityRepository.findByActivityName(activityChangeDTO.getActivityName());
        if (activityFromDB.isPresent()){
            redirect.addFlashAttribute("errors", "This activity already exists");
            return "redirect:adminActivities";
        }
        Category category = categoryRepository.categoryIdByName(activityChangeDTO.getCategoryList());
        Activity activity = activityRepository.findById(activityChangeDTO.getId()).orElseThrow();
        activity.setActivityName(activityChangeDTO.getActivityName());
        activity.setActivityUa(activityChangeDTO.getActivityUa());
        activity.setCategory(category);
        activityRepository.save(activity);
        redirect.addFlashAttribute("messages", "Activity successfully changed");
        return "redirect:adminActivities";
    }
}
