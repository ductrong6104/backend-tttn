package org.example.dienluc.controller;

import org.example.dienluc.payload.ResponseData;
import org.example.dienluc.service.ElectricRecordingService;
import org.example.dienluc.service.dto.electricRecording.ElectricRecordingCreateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/electric-recordings")
public class ElectricRecordingController {
    private final ElectricRecordingService electricRecordingService;

    public ElectricRecordingController(ElectricRecordingService electricRecordingService) {
        this.electricRecordingService = electricRecordingService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllElectricRecordings() {
        ResponseData responseData = ResponseData.builder()
                .data(electricRecordingService.getAllElectricRecordings())
                .message("get all electric recording")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PostMapping("")
    public ResponseEntity<?> createElectricRecording(@RequestBody ElectricRecordingCreateDto electricRecordingCreateDto) {
        ResponseData responseData = ResponseData.builder()
                .data(electricRecordingService.createElectricRecording(electricRecordingCreateDto))
                .message("get all electric recording")
                .status(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.ok(responseData);
    }

}
