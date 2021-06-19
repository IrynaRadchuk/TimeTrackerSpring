package com.example.controller;

import com.example.model.User;
import com.example.model.repository.ActivityRepository;
import com.example.model.repository.UserActivityRepository;
import com.example.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
        Optional<User> user = userRepository.findById(id);
        model.addAttribute("user", user);
        return "userProfile";
    }

    @GetMapping("/tracker/userEdit")
    public String userEdit(Long id, Model model) {
        Optional<User> user = userRepository.findById(id);
        model.addAttribute("user", user);
        return "userEdit";
    }
}
