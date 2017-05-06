package com.dev.web;

import com.dev.domain.DTO.AutoModeTrainInfoDTO;
import com.dev.domain.model.ActivationFunction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Controller
public class AnalysisController {

    @GetMapping("/excel_sheet")
    public String excelSheet() {
        return "excel_sheet";
    }

    @GetMapping("/analysis")
    public String analyze() {
        return "analysis";
    }

    @PostMapping(value = "/train", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String train(@RequestBody AutoModeTrainInfoDTO trainInfoDTO) {
        //TODO handle it
        List<ActivationFunction> outNeuronsFuncs = trainInfoDTO.getOutNeuronsFuncs();
        return "analysis";
    }
}
