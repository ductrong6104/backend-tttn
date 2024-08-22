package org.example.dienluc.controller;

import jakarta.validation.Valid;
import org.example.dienluc.entity.ElectricType;
import org.example.dienluc.payload.ResponseData;
import org.example.dienluc.service.ElectricTypeService;
import org.example.dienluc.service.dto.electricType.ElectricTypeCreateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/electric-types")
public class ElectricTypeController {
    private final ElectricTypeService electricTypeService;

    public ElectricTypeController(ElectricTypeService electricTypeService) {
        this.electricTypeService = electricTypeService;
    }
    @GetMapping("")
    public ResponseEntity<?> getAllElectricTypes() {
        ResponseData responseData = ResponseData.builder()
                .data(electricTypeService.getAllElectricTypes())
                .message("get all electric type")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PostMapping("")
    public ResponseEntity<?> createElectricType(@RequestBody ElectricTypeCreateDto electricTypeCreateDto) {
        ResponseData responseData = ResponseData.builder()
                .data(electricTypeService.createElectricType(electricTypeCreateDto))
                .message("create new electric type")
                .status(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PutMapping("/{electricTypeId}")
    public ResponseEntity<?> updateElectricType(@PathVariable Integer electricTypeId,@RequestBody ElectricTypeCreateDto electricTypeCreateDto) {
        ResponseData responseData = ResponseData.builder()
                .data(electricTypeService.updateElectricType(electricTypeId, electricTypeCreateDto))
                .message("update new electric type")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @DeleteMapping("/{electricTypeId}")
    public ResponseEntity<?> deleteElectricType(@PathVariable Integer electricTypeId) {
        ResponseData responseData = ResponseData.builder()
                .data(electricTypeService.deleteElectricType(electricTypeId))
                .message("delete electric type")
                .status(HttpStatus.NO_CONTENT.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
}
