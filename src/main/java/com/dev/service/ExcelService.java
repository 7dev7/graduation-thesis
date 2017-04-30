package com.dev.service;

import com.dev.domain.DTO.ExcelInfoDTO;
import com.dev.service.exception.StorageException;
import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {
    ExcelInfoDTO getFileInfo(MultipartFile excelFile) throws StorageException;
}
