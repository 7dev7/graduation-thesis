package com.dev.service.impl;

import com.dev.service.ExcelService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ExcelServiceImpl implements ExcelService {

    @Override
    public int getNumOfSheets(MultipartFile excelFile) {
        int numOfSheets;
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(excelFile.getInputStream());
            numOfSheets = workbook.getNumberOfSheets();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return numOfSheets;
    }
}
