package com.dev.web;

import com.dev.domain.model.DTO.AutoModeTrainInfoDTO;
import com.dev.domain.model.NetworkModel;
import com.dev.domain.model.spreadsheet.Spreadsheet;
import com.dev.service.AutoModeTrainService;
import com.dev.service.NetworkModelService;
import com.dev.service.SpreadsheetService;
import com.dev.service.exception.TrainingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;


@Controller
public class AnalysisController {
    private final SpreadsheetService spreadsheetService;
    private final AutoModeTrainService autoModeTrainService;
    private final NetworkModelService networkModelService;

    @Autowired
    public AnalysisController(SpreadsheetService spreadsheetService, AutoModeTrainService autoModeTrainService, NetworkModelService networkModelService) {
        this.spreadsheetService = spreadsheetService;
        this.autoModeTrainService = autoModeTrainService;
        this.networkModelService = networkModelService;
    }

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
        Optional<Spreadsheet> spreadsheetOptional = spreadsheetService.getActiveSpreadsheetForCurrentDoctor();
        Spreadsheet spreadsheet = spreadsheetOptional.orElseGet(Spreadsheet::new);
        List<NetworkModel> networkModels = null;
        try {
            networkModels = autoModeTrainService.train(trainInfoDTO, spreadsheet.getSpreadsheetData());
        } catch (TrainingException e) {
        }
        PriorityQueue<NetworkModel> models = restrict(networkModels);

        NetworkModel model;
        while ((model = models.poll()) != null) {
            networkModelService.save(model);
        }
        return "analysis";
    }

    private PriorityQueue<NetworkModel> restrict(List<NetworkModel> networkModels) {
        PriorityQueue<NetworkModel> queue = new PriorityQueue<>(10, Comparator.comparingDouble(NetworkModel::getError));
        queue.addAll(networkModels);
        return queue;
    }
}
