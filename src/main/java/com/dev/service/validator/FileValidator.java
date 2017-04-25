package com.dev.service.validator;

import com.dev.service.exception.StorageException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.NotOLE2FileException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileValidator {
    private static final String EXCEL_CONTENT_TYPE = "application/vnd.ms-excel";
    private static final String EXCEL_2007_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public void validate(MultipartFile file) throws StorageException {
        validateContentType(file);
        validateFileExtension(file);
        try {
            new HSSFWorkbook(file.getInputStream());
        } catch (NotOLE2FileException e) {
            throw new StorageException("It's no excel file");
        } catch (Exception e) {
            throw new StorageException(e.getMessage());
        }
    }

    private void validateFileExtension(MultipartFile file) throws StorageException {
        String originalFilename = file.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String extension = originalFilename.substring(index + 1);

        if (!"xls".equalsIgnoreCase(extension) && !"xlsx".equalsIgnoreCase(extension)) {
            throw new StorageException("Incorrect file extension");
        }
    }

    private void validateContentType(MultipartFile file) throws StorageException {
        String contentType = file.getContentType();
        if (!EXCEL_CONTENT_TYPE.equalsIgnoreCase(contentType) && !EXCEL_2007_CONTENT_TYPE.equalsIgnoreCase(contentType)) {
            throw new StorageException("Incorrect content type");
        }
    }
}
