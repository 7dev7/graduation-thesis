package com.dev.service.impl;

import com.dev.domain.dao.SpreadsheetRepository;
import com.dev.domain.model.Spreadsheet;
import com.dev.domain.model.doctor.Doctor;
import com.dev.service.DoctorService;
import com.dev.service.SpreadsheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpreadsheetServiceImpl implements SpreadsheetService {

    private final SpreadsheetRepository spreadsheetRepository;
    private final DoctorService doctorService;

    @Autowired
    public SpreadsheetServiceImpl(DoctorService doctorService, SpreadsheetRepository spreadsheetRepository) {
        this.doctorService = doctorService;
        this.spreadsheetRepository = spreadsheetRepository;
    }

    @Override
    public List<Spreadsheet> getActiveSpreadsheetsForDoctor(long doctorId) {
        Doctor author = doctorService.findById(doctorId);
        List<Spreadsheet> spreadsheetsByAuthor = spreadsheetRepository.getSpreadsheetsByAuthor(author);
        return spreadsheetsByAuthor.stream().filter(i -> !i.isClosed()).collect(Collectors.toList());
    }

    @Override
    public List<Spreadsheet> getActiveSpreadsheetsForDoctor(Doctor doctor) {
        List<Spreadsheet> spreadsheetsByAuthor = spreadsheetRepository.getSpreadsheetsByAuthor(doctor);
        return spreadsheetsByAuthor.stream().filter(i -> !i.isClosed()).collect(Collectors.toList());
    }

    @Override
    public void saveSpreadsheet(File file) {
        Spreadsheet spreadsheet = new Spreadsheet();
        spreadsheet.setClosed(false);
        spreadsheet.setExcelFile(file);
        spreadsheet.setAuthor(doctorService.getCurrentDoctor());
        spreadsheetRepository.save(spreadsheet);
    }
}
