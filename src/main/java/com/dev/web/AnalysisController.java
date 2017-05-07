package com.dev.web;

import com.dev.domain.model.DTO.AutoModeTrainInfoDTO;
import com.dev.domain.model.TrainedNetworkInfo;
import com.dev.domain.model.spreadsheet.Spreadsheet;
import com.dev.service.AutoModeTrainService;
import com.dev.service.SpreadsheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;


@Controller
public class AnalysisController {
    private final SpreadsheetService spreadsheetService;
    private final AutoModeTrainService autoModeTrainService;

    @Autowired
    public AnalysisController(SpreadsheetService spreadsheetService, AutoModeTrainService autoModeTrainService) {
        this.spreadsheetService = spreadsheetService;
        this.autoModeTrainService = autoModeTrainService;
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
        List<TrainedNetworkInfo> trainInfo = autoModeTrainService.train(trainInfoDTO, spreadsheet.getSpreadsheetData());
        return "analysis";
    }
}
