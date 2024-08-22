package org.example.dienluc.controller;

import org.example.dienluc.payload.ResponseData;
import org.example.dienluc.service.ElectricRecordingService;
import org.example.dienluc.service.dto.account.AccountUpdateDto;
import org.example.dienluc.service.dto.electricRecording.ElectricRecordingCreateDto;
import org.example.dienluc.service.dto.electricRecording.ElectricRecordingUpdateByEmployeeDto;
import org.example.dienluc.service.dto.electricRecording.ElectricRecordingUpdateDto;
import org.example.dienluc.service.dto.statistical.client.ElectricityUsedInYearRequestDto;
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
    @GetMapping("/assigned")
    public ResponseEntity<?> getAssignedElectricRecordings() {
        ResponseData responseData = ResponseData.builder()
                .data(electricRecordingService.getAssignedElectricRecordings())
                .message("get all assigned electric recording")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("/assigned/{employeeId}")
    public ResponseEntity<?> getAssignedElectricRecordingsByEmployeeId(@PathVariable Integer employeeId) {
        ResponseData responseData = ResponseData.builder()
                .data(electricRecordingService.getAssignedElectricRecordingsByEmployeeId(employeeId))
                .message("get all assigned electric recording by employee id")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PostMapping("")
    public ResponseEntity<?> createElectricRecording(@RequestBody ElectricRecordingCreateDto electricRecordingCreateDto) {
        ResponseData responseData = ResponseData.builder()
                .data(electricRecordingService.createElectricRecording(electricRecordingCreateDto))
                .message("create new electric recording")
                .status(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PutMapping("")
    public ResponseEntity<?> updateElectricRecordingFirst(@RequestBody ElectricRecordingCreateDto electricRecordingCreateDto) {
        ResponseData responseData = ResponseData.builder()
                .data(electricRecordingService.updateElectricRecordingFirst(electricRecordingCreateDto))
                .message("update first electric recording")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/{electricRecordingId}")
    public ResponseEntity<?> updateElectricRecording(@PathVariable Integer electricRecordingId, @RequestBody ElectricRecordingUpdateDto electricRecordingUpdateDto) {
        ResponseData responseData = ResponseData.builder()
                .data(electricRecordingService.updateElectricRecording(electricRecordingId, electricRecordingUpdateDto))
                .message("update employee or power meter electric recording")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @DeleteMapping("/{electricRecordingId}")
    public ResponseEntity<?> deleteElectricRecording(@PathVariable Integer electricRecordingId) {
        ResponseData responseData = ResponseData.builder()
                .data(electricRecordingService.deleteElectricRecording(electricRecordingId))
                .message("delete electric recording")
                .status(HttpStatus.NO_CONTENT.value())
                .build();
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/employee/{electricRecordingId}")
    public ResponseEntity<?> deleteRecordingByEmployee(@PathVariable Integer electricRecordingId) {
        ResponseData responseData = ResponseData.builder()
                .data(electricRecordingService.deleteRecordingByEmployee(electricRecordingId))
                .message("delete electric recording")
                .status(HttpStatus.NO_CONTENT.value())
                .build();
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/employee/{electricRecordingId}")
    public ResponseEntity<?> updateElectricRecordingByEmployee(@PathVariable Integer electricRecordingId, @RequestBody ElectricRecordingUpdateByEmployeeDto electricRecordingUpdateByEmployeeDto) {
//        System.out.println("electricRecordingId"  + electricRecordingId);
//        System.out.println("electricRecordingUpdateByEmployeeDto"  + electricRecordingUpdateByEmployeeDto);
        ResponseData responseData = ResponseData.builder()
                .data(electricRecordingService.updateElectricRecordingByEmployee(electricRecordingId, electricRecordingUpdateByEmployeeDto))
                .message("update electric recording by employee")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }


}
