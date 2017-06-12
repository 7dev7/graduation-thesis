package com.dev.web;

import com.dev.domain.model.DTO.NetworkModelDTO;
import com.dev.domain.model.NetworkModel;
import com.dev.service.DoctorService;
import com.dev.service.NetworkModelJSONService;
import com.dev.service.NetworkModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class NetworkModelController {
    private final NetworkModelService networkModelService;
    private final DoctorService doctorService;
    private final NetworkModelJSONService networkModelJSONService;

    @Autowired
    public NetworkModelController(NetworkModelService networkModelService, DoctorService doctorService, NetworkModelJSONService networkModelJSONService) {
        this.networkModelService = networkModelService;
        this.doctorService = doctorService;
        this.networkModelJSONService = networkModelJSONService;
    }

    @GetMapping("/network_models")
    public String networkModels(Model model) {
        List<NetworkModel> modelsForCurrentDoctor = networkModelService.getModelsForCurrentDoctor();
        model.addAttribute("networkModels", modelsForCurrentDoctor);
        return "network_models";
    }

    @GetMapping("/model")
    public String model(@RequestParam long id, Model model) {
        NetworkModel networkModel = networkModelService.findById(id);
        if (networkModel == null) {
            return "redirect:network_models";
        }
        if (networkModel.getOwner().getId() != doctorService.getCurrentDoctor().getId()) {
            return "redirect:network_models";
        }
        model.addAttribute("networkModel", networkModel);
        model.addAttribute("modelDTO", new NetworkModelDTO());
        return "model";
    }

    @PostMapping("/model")
    public String model(@ModelAttribute("modelDTO") NetworkModelDTO modelDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:model?id=" + modelDTO.getId();
        }
        networkModelService.update(modelDTO);
        return "redirect:model?id=" + modelDTO.getId();
    }

    @GetMapping("/model/compute")
    public String computeModel(@RequestParam long id, Model model) {
        NetworkModel networkModel = networkModelService.findById(id);
        if (networkModel == null) {
            return "redirect:/decision";
        }
        model.addAttribute("model", networkModel);
        return "compute_model";
    }
}
