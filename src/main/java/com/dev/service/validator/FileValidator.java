package com.dev.service.validator;

import com.dev.service.exception.StorageException;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileValidator {
    private static final String EXCEL_CONTENT_TYPE = "application/vnd.ms-excel";
    private static final String EXCEL_2007_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final String EXCEL_EXTENSION = "xls";
    private static final String EXCEL_2007_EXTENSION = "xlsx";

    public void validate(MultipartFile file) throws StorageException {
        validateContentType(file);
        validateFileExtension(file);
        try {
            WorkbookFactory.create(file.getInputStream());
        } catch (Exception e) {
            throw new StorageException(e.getMessage());
        }
    }

    private void validateFileExtension(MultipartFile file) throws StorageException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!EXCEL_EXTENSION.equalsIgnoreCase(extension) && !EXCEL_2007_EXTENSION.equalsIgnoreCase(extension)) {
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
