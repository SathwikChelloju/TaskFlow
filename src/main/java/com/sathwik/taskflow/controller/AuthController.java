package com.sathwik.taskflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sathwik.taskflow.entity.User;
import com.sathwik.taskflow.service.UserService;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String signupPage(Model model) {

        model.addAttribute("user", new User());

        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute User user,
                               Model model) {

        try {

            userService.registerUser(user);

            return "redirect:/login?registered";

        } catch (RuntimeException e) {

            model.addAttribute("user", user);
            model.addAttribute("error", e.getMessage());

            return "signup";
        }
    }
    @GetMapping("/login")
    public String loginPage() {

        return "login";

    }

}