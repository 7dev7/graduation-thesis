package com.dev.domain.repository;

import com.dev.domain.model.spreadsheet.SpreadsheetRow;
import org.springframework.data.repository.CrudRepository;

public interface SpreadsheetRowRepository extends CrudRepository<SpreadsheetRow, Long> {
}
