package com.dev.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @RequestMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        Model model) {
        model.addAttribute("error", error != null ? "Неправильный логин или пароль" : null);
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "logout";
    }
}
