package com.dev.web.rest;

import com.dev.domain.converter.NetworkModelDTOConverter;
import com.dev.domain.converter.SpreadsheetDataDTOConverter;
import com.dev.domain.model.DTO.*;
import com.dev.domain.model.NetworkModel;
import com.dev.domain.model.spreadsheet.ColumnType;
import com.dev.domain.model.spreadsheet.Spreadsheet;
import com.dev.domain.model.spreadsheet.SpreadsheetColumn;
import com.dev.service.NetworkModelService;
import com.dev.service.SpreadsheetService;
import com.dev.service.exception.StorageException;
import com.dev.service.exception.TrainingException;
import com.dev.service.train.AutoModeTrainService;
import com.dev.service.train.UserModelTrainService;
import com.dev.service.validator.FileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class TrainingDataRestController {
    private static final String SUCCESSFUL_CODE = "OK";
    private final AutoModeTrainService autoModeTrainService;
    private final UserModelTrainService userModelTrainService;
    private final SpreadsheetService spreadsheetService;
    private final NetworkModelService networkModelService;
    private final FileValidator fileValidator;

    @Autowired
    public TrainingDataRestController(FileValidator fileValidator, SpreadsheetService spreadsheetService,
                                      AutoModeTrainService autoModeTrainService, NetworkModelService networkModelService,
                                      UserModelTrainService userModelTrainService) {
        this.fileValidator = fileValidator;
        this.spreadsheetService = spreadsheetService;
        this.autoModeTrainService = autoModeTrainService;
        this.networkModelService = networkModelService;
        this.userModelTrainService = userModelTrainService;
    }

    @PostMapping(value = "/spreadsheet/load", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity analyze(@RequestParam("file") MultipartFile file) {
        String validate = validate(file);
        if (!SUCCESSFUL_CODE.equals(validate)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(validate);
        }
        try {
            Spreadsheet spreadsheet = spreadsheetService.createSpreadsheet(file);
        } catch (StorageException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);
    }

    @PostMapping(value = "/validate", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity validate(@RequestBody ValidateInputsDTO validateInputsDTO) {
        if (!spreadsheetService.validate(validateInputsDTO)) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("ERROR");
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);
    }

    @PostMapping(value = "/train", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity train(@RequestBody AutoModeTrainInfoDTO trainInfoDTO) {
        Optional<Spreadsheet> spreadsheetOptional = spreadsheetService.getActiveSpreadsheetForCurrentDoctor();
        Spreadsheet spreadsheet = spreadsheetOptional.orElseGet(Spreadsheet::new);
        List<NetworkModel> networkModels = null;
        try {
            networkModels = autoModeTrainService.train(trainInfoDTO, spreadsheet);
        } catch (TrainingException e) {
            e.printStackTrace();
        }
        List<NetworkModel> models = shrink(networkModels, trainInfoDTO);
        models.forEach(networkModelService::save);
        List<NetworkModelDTO> modelDTOS = models.stream().map(NetworkModelDTOConverter::convert).collect(Collectors.toCollection(ArrayList::new));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(modelDTOS);
    }

    @PostMapping(value = "/train_user_model", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity trainUserModel(@RequestBody UserModelTrainInfoDTO userModelTrainInfo) {
        Optional<Spreadsheet> spreadsheetOptional = spreadsheetService.getActiveSpreadsheetForCurrentDoctor();
        Spreadsheet spreadsheet = spreadsheetOptional.orElseGet(Spreadsheet::new);

        NetworkModel networkModel = null;
        try {
            networkModel = userModelTrainService.train(userModelTrainInfo, spreadsheet);
        } catch (TrainingException e) {
            e.printStackTrace();
        }
        networkModelService.save(networkModel);
        NetworkModelDTO networkModelDTO = NetworkModelDTOConverter.convert(networkModel);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Collections.singletonList(networkModelDTO));
    }

    private List<NetworkModel> shrink(List<NetworkModel> networkModels, AutoModeTrainInfoDTO trainInfoDTO) {
        PriorityQueue<NetworkModel> queue = new PriorityQueue<>(trainInfoDTO.getNumOfSavedNetworks(), Comparator.comparingDouble(NetworkModel::getError));
        queue.addAll(networkModels);
        List<NetworkModel> result = new ArrayList<>();
        for (int i = 0; i < trainInfoDTO.getNumOfSavedNetworks() && i < networkModels.size(); i++) {
            result.add(queue.poll());
        }
        return result;
    }

    @PostMapping(value = "/spreadsheet/current", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity currentSpreadsheet() {
        Optional<Spreadsheet> spreadsheetOptional = spreadsheetService.getActiveSpreadsheetForCurrentDoctor();
        try {
            Spreadsheet spreadsheet = spreadsheetOptional.orElseThrow(Exception::new);
            SpreadsheetDataDTO dataDTO = SpreadsheetDataDTOConverter.convert(spreadsheet);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(dataDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);
    }

    @PostMapping(value = "/spreadsheet/create")
    public void createSpreadsheet() {
        Spreadsheet spreadsheet = spreadsheetService.createSpreadsheet();
    }

    @PostMapping(value = "/spreadsheet/close")
    public void closeSpreadsheet() {
        Optional<Spreadsheet> spreadsheetOptional = spreadsheetService.getActiveSpreadsheetForCurrentDoctor();
        spreadsheetOptional.ifPresent(i -> {
            i.setLastUpdate(new Date());
            i.setClosed(true);
            spreadsheetService.updateSpreadsheet(i);
        });
    }

    @PostMapping(value = "/column/add")
    public void addColumn(@RequestParam Map<String, Object> map) {
        Optional<Spreadsheet> spreadsheetOptional = spreadsheetService.getActiveSpreadsheetForCurrentDoctor();
        Spreadsheet spreadsheet = spreadsheetOptional.orElseGet(spreadsheetService::createSpreadsheet);

        String columnName = (String) map.get("columnName");
        ColumnType type = ColumnType.values()[Integer.valueOf((String) map.get("columnType"))];
        SpreadsheetColumn column = new SpreadsheetColumn(spreadsheet.getMaxColumnIndex() + 1, columnName, type);
        column.setSpreadsheet(spreadsheet);
        spreadsheet.getColumns().add(column);
        spreadsheetService.updateSpreadsheet(spreadsheet);
    }

    @PostMapping(value = "/column/update")
    public void updateColumn(@RequestParam Map<String, Object> map) {
        Integer index = Integer.valueOf((String) map.get("columnId"));
        ColumnType type = ColumnType.values()[Integer.valueOf((String) map.get("columnType"))];
        String columnName = (String) map.get("columnName");
        String initName = (String) map.get("initName");
        try {
            spreadsheetService.updateColumn(index, initName, columnName, type);
        } catch (StorageException e) {
            e.printStackTrace();
        }
    }

    @PostMapping(value = "/column/remove")
    public void removeColumn(@RequestParam Map<String, Object> map) {
        Integer index = Integer.valueOf((String) map.get("columnId"));
        String initName = (String) map.get("initName");
        try {
            spreadsheetService.removeColumnByIndex(index, initName);
        } catch (StorageException e) {
            e.printStackTrace();
        }
    }

    @PostMapping(value = "/row/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public int addRow(@RequestParam Map<String, Object> map) {
        Optional<Spreadsheet> spreadsheetOptional = spreadsheetService.getActiveSpreadsheetForCurrentDoctor();
        Spreadsheet spreadsheet = spreadsheetOptional.orElseGet(spreadsheetService::createSpreadsheet);
        try {
            return spreadsheetService.addRow(map);
        } catch (StorageException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @PostMapping("/row/edit")
    public void editRow(@RequestParam Map<String, Object> map) {
        Integer index = Integer.valueOf((String) map.get("___#$RowId$#___"));
        try {
            spreadsheetService.editRowByIndex(index, map);
        } catch (StorageException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/row/remove")
    public void removeRow(@RequestParam Map<String, Object> map) {
        int index = Integer.valueOf((String) map.get("id"));
        try {
            spreadsheetService.removeRowByIndex(index);
        } catch (StorageException e) {
            e.printStackTrace();
        }
    }

    private String validate(MultipartFile file) {
        String message = SUCCESSFUL_CODE;
        try {
            fileValidator.validate(file);
        } catch (StorageException e) {
            message = e.getMessage();
        }
        return message;
    }
}
