package com.dev.service;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {
    int getNumOfSheets(MultipartFile excelFile);
}
