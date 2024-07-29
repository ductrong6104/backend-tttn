package org.example.dienluc.controller;

import org.example.dienluc.payload.ResponseData;
import org.example.dienluc.service.LevelService;
import org.example.dienluc.service.dto.electricType.ElectricTypeCreateDto;
import org.example.dienluc.service.dto.level.LevelCreateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/levels")
public class LevelController {
    private final LevelService levelService;

    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    @GetMapping("/not-use/electric-type/{electricTypeId}")
    public ResponseEntity<?> getLevelNotUseByElectricType(@PathVariable Integer electricTypeId) {
        ResponseData responseData = ResponseData.builder()
                .data(levelService.getLevelNotUseByElectricType(electricTypeId))
                .message("get level not use by electric type")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("")
    public ResponseEntity<?> getAllLevel() {
        ResponseData responseData = ResponseData.builder()
                .data(levelService.getAllLevel())
                .message("get all level")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PostMapping("")
    public ResponseEntity<?> createLevel(@RequestBody LevelCreateDto levelCreateDto) {
        ResponseData responseData = ResponseData.builder()
                .data(levelService.createLevel(levelCreateDto))
                .message("create new level")
                .status(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @DeleteMapping("{levelId}")
    public ResponseEntity<?> deleteLevelById(@PathVariable Integer levelId) {
        ResponseData responseData = ResponseData.builder()
                .data(levelService.deleteLevelById(levelId))
                .message("delete level by id")
                .status(HttpStatus.NO_CONTENT.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
}
