package com.dev.web;

import com.dev.domain.model.DTO.DoctorDTO;
import com.dev.domain.model.doctor.Doctor;
import com.dev.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/doctor")
    public String doctorPage(@RequestParam long id, Model model) {
        Doctor doctor = doctorService.findById(id);
        if (doctor == null) {
            return "redirect:/";
        }
        model.addAttribute("doctor", doctor);
        model.addAttribute("doctorDto", new DoctorDTO());
        return "doctor_page";
    }

    @PostMapping("/doctor")
    public String doctorPage(@ModelAttribute("doctorDTO") DoctorDTO doctorDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:doctor?id=" + doctorDTO.getId();
        }
        doctorService.save(doctorDTO);
        return "redirect:doctor?id=" + doctorDTO.getId();
    }
}
