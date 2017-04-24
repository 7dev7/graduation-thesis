package com.dev.web;

import com.dev.domain.DTO.DoctorDTO;
import com.dev.domain.model.user.Doctor;
import com.dev.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @RequestMapping(value = "/doctor", method = RequestMethod.GET)
    public String doctorPage(@RequestParam long id, Model model) {
        Doctor doctor = doctorService.findById(id);
        model.addAttribute("doctor", doctor);
        model.addAttribute("doctorDto", new DoctorDTO());
        return "doctor_page";
    }

    @RequestMapping(value = "/doctor", method = RequestMethod.POST)
    public String doctorPage(@ModelAttribute("doctorDTO") DoctorDTO doctorDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:doctor?id=" + doctorDTO.getId();
        }
        doctorService.save(doctorDTO);
        return "redirect:doctor?id=" + doctorDTO.getId();
    }
}
