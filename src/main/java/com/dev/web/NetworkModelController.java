package com.dev.web;

import com.dev.domain.model.NetworkModel;
import com.dev.service.NetworkModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class NetworkModelController {
    private final NetworkModelService networkModelService;

    @Autowired
    public NetworkModelController(NetworkModelService networkModelService) {
        this.networkModelService = networkModelService;
    }

    @GetMapping("/network_models")
    public String networkModels(Model model) {
        List<NetworkModel> modelsForCurrentDoctor = networkModelService.getModelsForCurrentDoctor();
        model.addAttribute("networkModels", modelsForCurrentDoctor);
        return "network_models";
    }
}
