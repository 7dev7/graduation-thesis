package com.dev.web.rest;

import com.dev.domain.model.DTO.SaveTrainedModelsDTO;
import com.dev.service.NetworkModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ModelsRestController {
    private final NetworkModelService networkModelService;

    @Autowired
    public ModelsRestController(NetworkModelService networkModelService) {
        this.networkModelService = networkModelService;
    }

    @PostMapping(value = "/models/save_trained", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveTrained(@RequestBody SaveTrainedModelsDTO modelsDTO) {
        List<Long> nonSelectedModels = modelsDTO.getNonSelectedModels();
        nonSelectedModels.forEach(networkModelService::removeModelById);
    }
}
