package com.zonix.dndapp.controller;

import com.zonix.dndapp.entity.User;
import com.zonix.dndapp.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register.html";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        if(userService.save(user)) {
            return "redirect:/user/login";
        }
        return "redirect:/user/register";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String email,
                        @RequestParam String password) {

        return "redirect:/home";
    }
}
