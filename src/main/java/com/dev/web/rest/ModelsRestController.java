package com.dev.web.rest;

import com.dev.domain.converter.NetworkModelDTOConverter;
import com.dev.domain.converter.NetworkModelToJSONConverter;
import com.dev.domain.model.DTO.ComputeModelDataDTO;
import com.dev.domain.model.DTO.ComputeResultDTO;
import com.dev.domain.model.DTO.NetworkModelDTO;
import com.dev.domain.model.DTO.SaveTrainedModelsDTO;
import com.dev.domain.model.NetworkModel;
import com.dev.domain.model.network.Perceptron;
import com.dev.domain.model.network.RadialBasisFunctionsNetwork;
import com.dev.service.NetworkModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @PostMapping("/model/remove")
    public void removeModel(@RequestParam Map<String, Object> map) {
        Integer modelId = Integer.parseInt((String) map.get("modelId"));
        networkModelService.removeModelById(modelId);
    }

    @PostMapping("/model/appropriate")
    public ResponseEntity getModelsByInputs(@RequestParam Map<String, Object> map) {
        Integer numOfInputs = Integer.parseInt((String) map.get("numOfInputs"));

        List<NetworkModel> modelsForCurrentDoctor = networkModelService.getModelsForCurrentDoctor();
        List<NetworkModel> appropriateModels = getAppropriateModels(modelsForCurrentDoctor, numOfInputs);

        List<NetworkModelDTO> modelDTOS = appropriateModels.stream().map(NetworkModelDTOConverter::convert).collect(Collectors.toCollection(ArrayList::new));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(modelDTOS);
    }

    private List<NetworkModel> getAppropriateModels(List<NetworkModel> modelsForCurrentDoctor, Integer numOfInputs) {
        List<NetworkModel> models = new ArrayList<>();
        for (NetworkModel model : modelsForCurrentDoctor) {
            Perceptron perceptron = model.getPerceptron();
            if (perceptron != null) {
                if (perceptron.getInputNeurons() == numOfInputs) {
                    models.add(model);
                }
            }
            RadialBasisFunctionsNetwork rbfNetwork = model.getRbfNetwork();
            if (rbfNetwork != null) {
                if (rbfNetwork.getInputNeurons() == numOfInputs) {
                    models.add(model);
                }
            }
        }
        return models;
    }

    @PostMapping(value = "/model/compute", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity compute(@RequestBody ComputeModelDataDTO computeModelDataDTO) {
        ComputeResultDTO result = networkModelService.compute(computeModelDataDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @PostMapping(value = "/model/file/")
    public void getFile(@RequestParam long modelId,
                        HttpServletResponse response) throws IOException {
        NetworkModel networkModel = networkModelService.findById(modelId);
        String fileName = networkModel.getName() + ".json";
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        String modelJSON = NetworkModelToJSONConverter.convert(networkModel);

        response.getOutputStream().write(modelJSON.getBytes());
        response.flushBuffer();
    }

    @PostMapping(value = "/model/upload/")
    public void upload(@RequestParam("file") MultipartFile file) throws IOException, ClassNotFoundException {
        //TODO implement it
        ObjectInputStream objectInputStream = new ObjectInputStream(file.getInputStream());
        Object o = objectInputStream.readObject();
    }
}
