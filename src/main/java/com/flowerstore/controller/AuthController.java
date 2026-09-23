package com.flowerstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(Model model) {
        return "client/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        return "client/register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        return "client/forgot-password";
    }

    @GetMapping("/change-password")
    public String changePassword(Model model) {
        return "client/change-password";
    }
}
