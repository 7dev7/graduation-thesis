package com.dev.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DecisionController {

    @GetMapping("/decision")
    public String decision() {
        return "decision";
    }
}
