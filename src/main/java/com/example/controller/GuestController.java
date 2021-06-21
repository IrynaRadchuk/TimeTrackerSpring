package com.example.controller;

import com.example.model.User;
import com.example.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/tracker")
public class GuestController {

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

/*    @PostMapping("/tracker/login")
    public String loginPost(@RequestParam String userEmail,
                            @RequestParam String userPassword,
                            Model model) {
        System.err.println(userEmail +  userPassword);

        return "redirect:/tracker/adminUser";
    }*/

    @PostMapping("/registration")
    public String registrationPost(@Valid @RequestBody
                                       @RequestParam String userEmail,
                                   @RequestParam String userPassword,
                                   @RequestParam String userFirstName,
                                   @RequestParam String userLastName,
                                   Map<String,Object> model) {
//        User userFromDB = userRepository.findByUserEmail(userEmail);
//if (userFromDB!=null){
//    System.err.println("USER EX");
//    model.put("message", "User exists");
//    return "redirect:/tracker/registration";
//}
//        if (result.hasErrors()) {
//            return "/tracker/registration";
//        }
//        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        User user = new User(userEmail,userPassword,userFirstName,userLastName);
//        Set<ConstraintViolation<User>> validate = validator.validate(user);
//        System.err.println(validate);
        userRepository.save(user);
        return "redirect:/tracker/login";
    }
}