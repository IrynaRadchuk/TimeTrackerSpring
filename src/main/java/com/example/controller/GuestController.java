package com.example.controller;

import com.example.model.dto.UserRegisterDTO;
import com.example.model.repository.UserRepository;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tracker")
public class GuestController {
    @Autowired
    private UserService service;


    @GetMapping("/index")
    public String greeting() {
        return "greeting";
    }

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }


    @PostMapping("/registration")
    public String registrationPost(@ModelAttribute("users") @Valid UserRegisterDTO userRegisterDTO,
                                   BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            String collect = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("; "));
            redirect.addFlashAttribute("errors", collect);
            return "redirect:registration";
        }
        if (!service.registerUser(userRegisterDTO)) {
            redirect.addFlashAttribute("errors", "This email is already in use");
            return "redirect:registration";
        } else {
            redirect.addFlashAttribute("messages", "User successfully registered");
            return "redirect:/tracker/login";
        }
    }

        @GetMapping("/default")
        public String defaultAfterLogin(HttpServletRequest request) {
            if (request.isUserInRole("ROLE_ADMIN")) {
                return "redirect:/tracker/adminRequests";
            }
            return "redirect:/tracker/schedule";
        }
    }