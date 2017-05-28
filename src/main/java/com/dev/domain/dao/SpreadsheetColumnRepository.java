package com.dev.domain.dao;

import com.dev.domain.model.spreadsheet.SpreadsheetColumn;
import org.springframework.data.repository.CrudRepository;

public interface SpreadsheetColumnRepository extends CrudRepository<SpreadsheetColumn, Long> {
}
