package com.dev.domain.dao;

import com.dev.domain.model.doctor.Doctor;
import com.dev.domain.model.spreadsheet.Spreadsheet;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SpreadsheetRepository extends CrudRepository<Spreadsheet, Long> {
    List<Spreadsheet> getSpreadsheetsByAuthor(Doctor author);
}
