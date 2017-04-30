package com.dev.web;

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

    @Autowired
    public AnalysisRestController(FileValidator fileValidator) {
        this.fileValidator = fileValidator;
    }

    @PostMapping(value = "/analysis", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity analyze(@RequestParam("file") MultipartFile file) {
        String validate = validate(file);
        if (!SUCCESSFUL_CODE.equals(validate)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(validate);
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("OK");
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
