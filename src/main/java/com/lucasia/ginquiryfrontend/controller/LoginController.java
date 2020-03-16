package com.lucasia.ginquiryfrontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    public static final String LOGIN = "login";
    public static String LOGIN_PATH = "/"+ LOGIN;

    public static final String GUEST_USER = "guest";

    @GetMapping("/")
    public String index() {
        return "home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        return "logout";

    }

}
