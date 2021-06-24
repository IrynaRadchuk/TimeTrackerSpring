package com.example.controller;

import com.example.model.AllowedActivity;
import com.example.model.User;
import com.example.model.UserActivity;
import com.example.model.dto.UserUpdateDto;
import com.example.model.repository.ActivityRepository;
import com.example.model.repository.AllowedActivityRepository;
import com.example.model.repository.UserActivityRepository;
import com.example.model.repository.UserRepository;
import com.example.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        Long id = getUserSessionId();
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(value -> model.addAttribute("user", value));
        return "userProfile";
    }

    @GetMapping("/tracker/updateProfile")
    public String updateProfile(Model model) {
        Long id = getUserSessionId();
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(value -> model.addAttribute("user", value));
        return "updateProfile";
    }

    @PostMapping("/tracker/updateProfile")
    public String updateProfilePost(@ModelAttribute("user") @Valid UserUpdateDto userUpdateDto,
                                    BindingResult result, RedirectAttributes redirect,
                                    Model model
    ) {
        Long id = getUserSessionId();
        User user = userRepository.findById(id).orElseThrow();
        model.addAttribute("user", user);
        if (!passwordEncoder.matches(userUpdateDto.getUserPasswordConfirm(), user.getUserPassword())) {
            redirect.addFlashAttribute("errors", "Password confirm does not match original user password");
            return "redirect:updateProfile";
        }
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();

            String collect = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("; "));
            redirect.addFlashAttribute("errors", collect);
            return "redirect:updateProfile";
        }
        user.setUserEmail(userUpdateDto.getUserEmail());
        user.setUserFirstName(userUpdateDto.getUserFirstName());
        user.setUserLastName(userUpdateDto.getUserLastName());
        user.setUserPassword(passwordEncoder.encode(userUpdateDto.getUserPassword()));
        userRepository.save(user);
        redirect.addFlashAttribute("messages", "Profile successfully updated");
        return "redirect:userProfile";
    }

    @GetMapping("/tracker/userRequests")
    public String userRequests(Model model) {
        Long id = getUserSessionId();
        List<AllowedActivity> activity = allowedActivityRepository.activitiesByUser(id);
        List<String> allActivities = activityRepository.absentActivities(id);
        List<String> allActivitiesUa = activityRepository.absentActivitiesUa(id);
        model.addAttribute("activity", activity);
        model.addAttribute("allActivities", allActivities);
        model.addAttribute("allActivitiesUa", allActivitiesUa);
        return "userRequests";
    }

    @PostMapping("/tracker/request")
    public String request(@RequestParam String requestActivity) {
        allowedActivityRepository.request(getUserSessionId(), requestActivity);
        return "redirect:userRequests";
    }

    @GetMapping("/tracker/schedule")
    public String schedule(Model model) {
        Long id = getUserSessionId();
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

        userActivityRepository.saveActivityForUser(getUserSessionId(), activityNameAdd, LocalDate.parse(activityDateAdd), activityDurationAdd);
        return "redirect:schedule";
    }

    @PostMapping("/tracker/deleteSchedule")
    public String deleteSchedule(@RequestParam String activityDate,
                                 @RequestParam String activityList) {
        userActivityRepository.deleteActivityForUser(getUserSessionId(), activityList, LocalDate.parse(activityDate));
        return "redirect:schedule";
    }

    private Long getUserSessionId() {
        MyUserDetails userFromContext = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userFromContext.getUser().getId();
    }
}
