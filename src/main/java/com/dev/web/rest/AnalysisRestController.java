package com.dev.web.rest;

import com.dev.domain.DTO.ExcelInfoDTO;
import com.dev.service.ExcelService;
import com.dev.service.SpreadsheetService;
import com.dev.service.exception.StorageException;
import com.dev.service.validator.FileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AnalysisRestController {
    private static final String SUCCESSFUL_CODE = "OK";
    private final FileValidator fileValidator;
    private final ExcelService excelService;
    private final SpreadsheetService spreadsheetService;

    @Autowired
    public AnalysisRestController(FileValidator fileValidator, ExcelService excelService, SpreadsheetService spreadsheetService) {
        this.fileValidator = fileValidator;
        this.excelService = excelService;
        this.spreadsheetService = spreadsheetService;
    }

    @PostMapping(value = "/analysis", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity analyze(@RequestParam("file") MultipartFile file) {
        String validate = validate(file);
        if (!SUCCESSFUL_CODE.equals(validate)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(validate);
        }
        ExcelInfoDTO fileInfo;
        try {
            fileInfo = excelService.getFileInfo(file);
        } catch (StorageException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
        spreadsheetService.saveSpreadsheet(file);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(fileInfo);
    }

    private String validate(MultipartFile file) {
        String message = SUCCESSFUL_CODE;
        try {
            fileValidator.validate(file);
        } catch (StorageException e) {
            message = e.getMessage();
        }
        return message;
    }
}
