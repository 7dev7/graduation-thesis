package com.dev.domain.repository;

import com.dev.domain.model.spreadsheet.SpreadsheetCell;
import org.springframework.data.repository.CrudRepository;

public interface SpreadsheetCellRepository extends CrudRepository<SpreadsheetCell, Long> {
}
