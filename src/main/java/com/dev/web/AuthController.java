package com.dev.web;

import com.dev.domain.model.user.Doctor;
import com.dev.service.DoctorService;
import com.dev.service.SecurityService;
import com.dev.service.validator.DoctorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AuthController {

    private final DoctorService doctorService;
    private final DoctorValidator doctorValidator;
    private final SecurityService securityService;

    @Autowired
    public AuthController(DoctorService doctorService, SecurityService securityService, DoctorValidator doctorValidator) {
        this.doctorService = doctorService;
        this.securityService = securityService;
        this.doctorValidator = doctorValidator;
    }

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

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("doctorForm", new Doctor());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("doctorForm") Doctor doctorForm, BindingResult bindingResult) {
        doctorValidator.validate(doctorForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        doctorService.save(doctorForm);
        securityService.autologin(doctorForm.getLogin(), doctorForm.getPasswordConfirm());
        return "redirect:/";
    }
}
