package org.example.dienluc.controller;

import org.example.dienluc.entity.PowerMeter;
import org.example.dienluc.payload.ResponseData;
import org.example.dienluc.service.PowerMeterService;
import org.example.dienluc.service.dto.powerMeter.PowerMeterCreateDto;
import org.example.dienluc.service.dto.powerMeter.UpdateStatusRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/power-meters")
public class PowerMeterController {
    private final PowerMeterService powerMeterService;

    public PowerMeterController(PowerMeterService powerMeterService) {
        this.powerMeterService = powerMeterService;
    }
    @GetMapping("")
    public ResponseEntity<?> getAllPowerMeter() {
        ResponseData responseData = ResponseData.builder()
                .data(powerMeterService.getAllPowerMeter())
                .message("get power meter available")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("/available")
    public ResponseEntity<?> getAvailablePowerMeters() {
        ResponseData responseData = ResponseData.builder()
                .data(powerMeterService.getAvailablePowerMeters())
                .message("get power meter available")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PutMapping("/{powerMeterId}/status")
    public ResponseEntity<?> updateStatusPowerMeter(@PathVariable Integer powerMeterId,@RequestBody UpdateStatusRequest updateStatusRequest) {
        ResponseData responseData = ResponseData.builder()
                .data(powerMeterService.updateStatusPowerMeter(powerMeterId, updateStatusRequest.getStatus()))
                .message("update status power meter")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PostMapping("")
    public ResponseEntity<?> createNewPowerMeter(@RequestBody PowerMeterCreateDto powerMeterCreateDto) {
        ResponseData responseData = ResponseData.builder()
                .data(powerMeterService.createNewPowerMeter(powerMeterCreateDto))
                .message("create new power meter")
                .status(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("/recordable")
    public ResponseEntity<?> getRecordablePowerMeters() {
        ResponseData responseData = ResponseData.builder()
                .data(powerMeterService.getRecordablePowerMeters())
                .message("get power meter recordable")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
}
