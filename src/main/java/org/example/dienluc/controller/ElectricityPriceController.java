package org.example.dienluc.controller;

import jakarta.validation.Valid;
import org.example.dienluc.payload.ResponseData;
import org.example.dienluc.service.ElectricityPriceService;
import org.example.dienluc.service.dto.electricityPrice.ElectricityPriceCreateDto;
import org.example.dienluc.service.dto.electricityPrice.ElectricityPriceDeleteDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/electricity-prices")
public class ElectricityPriceController {
    private final ElectricityPriceService electricityPriceService;

    public ElectricityPriceController(ElectricityPriceService electricityPriceService) {
        this.electricityPriceService = electricityPriceService;
    }
    @GetMapping("")
    public ResponseEntity<?> getAllElectricityPrices() {
        ResponseData responseData = ResponseData.builder()
                .data(electricityPriceService.getAllElectricityPrices())
                .message("get all electric prices")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PostMapping("")
    public ResponseEntity<?> createElectricityPrice(@RequestBody ElectricityPriceCreateDto electricityPriceCreateDto) {
        ResponseData responseData = ResponseData.builder()
                .data(electricityPriceService.createElectricityPrice(electricityPriceCreateDto))
                .message("create new electric prices")
                .status(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @DeleteMapping("/electric-types/{electricTypeId}/levels/{levelId}")
    public ResponseEntity<?> deleteElectricityPrice(@PathVariable Integer electricTypeId, @PathVariable Integer levelId) {
        ResponseData responseData = ResponseData.builder()
                .data(electricityPriceService.deleteElectricityPrice(electricTypeId, levelId))
                .message("delete electric price")
                .status(HttpStatus.NO_CONTENT.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
}
