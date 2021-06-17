package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GreetingController {

    @GetMapping("/tracker/index")
    public String greeting(Model model) {
        model.addAttribute("title","Time Tracker");
        return "greeting";
    }
    @GetMapping("/tracker/login")
    public String login(Model model) {
        model.addAttribute("name");
        return "login";
    }
    @GetMapping("/tracker/registration")
    public String registration(Model model) {
        model.addAttribute("name");
        return "registration";
    }
}