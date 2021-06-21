package com.example.controller;

import com.example.model.User;
import com.example.model.repository.ActivityRepository;
import com.example.model.repository.UserActivityRepository;
import com.example.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserActivityRepository userActivityRepository;

    @GetMapping("/tracker/userProfile")
    public String userProfile(Long id, Model model) {
        Optional<User> user = userRepository.findById(64L);
        user.ifPresent(value -> model.addAttribute("user", value));
        return "userProfile";
    }
    @GetMapping("/tracker/userEdit")
    public String userEdit(Long id, Model model) {
        Optional<User> user = userRepository.findById(id);
        model.addAttribute("user", user);
        return "userEdit";
    }

    @GetMapping("/tracker/updateProfile")
    public String updateProfile(Long id, Model model) {
        Optional<User> user = userRepository.findById(64L);
        user.ifPresent(value -> model.addAttribute("user", value));
        return "updateProfile";
    }

    @PostMapping("/tracker/updateProfile")
    public String updateProfilePost(Long id,
                                @RequestParam String userEmail,
                                @RequestParam String userPassword,
                                @RequestParam String userFirstName,
                                @RequestParam String userLastName,
                                @RequestParam String userPasswordConfirm,
                                Model model) {
        User user = userRepository.findById(64L).orElseThrow();
        model.addAttribute("user", user);
        String pass = user.getUserPassword();
        if(!userPasswordConfirm.equals(pass)){
            return "updateProfile";
        }
        user.setUserEmail(userEmail);
        user.setUserFirstName(userFirstName);
        user.setUserLastName(userLastName);
        user.setUserPassword(userPassword);
        userRepository.save(user);
        return "redirect:userProfile";
    }
}
