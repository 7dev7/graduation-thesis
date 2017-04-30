package com.dev.web;

import com.dev.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AnalysisController {

    private final ExcelService excelService;

    @Autowired
    public AnalysisController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @GetMapping("/excel_sheet")
    public String excelSheet() {
        return "excel_sheet";
    }

    @GetMapping("/analysis")
    public String analyze() {
        return "analysis";
    }
}
