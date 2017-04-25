package com.dev.web;

import com.dev.service.ExcelService;
import com.dev.service.exception.StorageException;
import com.dev.service.validator.FileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AnalysisController {

    private final ExcelService excelService;
    private final FileValidator fileValidator;

    @Autowired
    public AnalysisController(ExcelService excelService, FileValidator fileValidator) {
        this.excelService = excelService;
        this.fileValidator = fileValidator;
    }

    @GetMapping("/analysis")
    public String analyze() {
        return "analysis";
    }

    @PostMapping("/analysis")
    public String analyze(@RequestParam("file") MultipartFile file,
                          RedirectAttributes redirectAttributes) {
        validate(file, redirectAttributes);
        //TODO implement choosing sheet
        return "redirect:/analysis";
    }

    private String chooseSheet(MultipartFile file) {
        int numOfSheets = excelService.getNumOfSheets(file);
        if (numOfSheets > 1) {

        }
        return "redirect:/setup_excel";
    }

    private void validate(MultipartFile file, RedirectAttributes redirectAttributes) {
        String message = "You successfully uploaded " + file.getOriginalFilename() + "!";
        try {
            fileValidator.validate(file);
        } catch (StorageException e) {
            message = e.getMessage();
        }
        redirectAttributes.addFlashAttribute("message", message);
    }
}
