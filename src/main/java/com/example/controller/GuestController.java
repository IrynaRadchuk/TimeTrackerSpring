package com.example.controller;

import com.example.model.User;
import com.example.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tracker")
public class GuestController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    List<String>errors = new ArrayList<>();
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
    public String registration(Model model) {
        model.addAttribute("errors", errors);
        errors = new ArrayList<>();
        return "registration";
    }

/*    @PostMapping("/tracker/login")
    public String loginPost(@RequestParam String userEmail,
                            @RequestParam String userPassword,
                            Model model) {
        System.err.println(userEmail +  userPassword);

        return "redirect:/tracker/adminUser";
    }*/

    @PostMapping("/registration")
    public String registrationPost(@Valid User users, BindingResult result,
                                   @RequestParam String userEmail,
                                   @RequestParam String userPassword,
                                   @RequestParam String userFirstName,
                                   @RequestParam String userLastName) {
//        User userFromDB = userRepository.findByUserEmail(userEmail);
//if (userFromDB!=null){
//    System.err.println("USER EX");
//    model.put("message", "User exists");
//    return "redirect:/tracker/registration";
//}
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();

            String collect = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("; "));
            errors.add(collect);
            return "redirect:/tracker/registration";
        }
//        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        User user = new User(userEmail, passwordEncoder.encode(userPassword), userFirstName, userLastName);
//        Set<ConstraintViolation<User>> validate = validator.validate(user);
//        System.err.println(validate);
        userRepository.save(user);
        return "redirect:/tracker/login";
    }
        @GetMapping("/default")
        public String defaultAfterLogin(HttpServletRequest request) {
            if (request.isUserInRole("ROLE_ADMIN")) {
                return "redirect:/tracker/adminRequests";
            }
            return "redirect:/tracker/schedule";
        }
    }