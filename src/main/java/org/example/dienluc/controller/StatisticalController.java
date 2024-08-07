package org.example.dienluc.controller;

import org.example.dienluc.payload.ResponseData;
import org.example.dienluc.service.StatisticalService;
import org.example.dienluc.service.dto.statistical.client.AmountEveryMonthInEveryYearRequestDto;
import org.example.dienluc.service.dto.statistical.client.ConsumptionFromDateToDateDto;
import org.example.dienluc.service.dto.statistical.client.ElectricityUsedInYearRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statisticals")
public class StatisticalController {
    private final StatisticalService statisticalService;

    public StatisticalController(StatisticalService statisticalService) {
        this.statisticalService = statisticalService;
    }

    @GetMapping("/consumption-electric-types")
    public ResponseEntity<?> getConsumptionElectricType() {
        ResponseData responseData = ResponseData.builder()
                .data(statisticalService.getConsumptionElectricTypes())
                .message("get consumption electric type")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/electricity-used")
    public ResponseEntity<?> getElectricityUsedInYearByClientId(@RequestBody ElectricityUsedInYearRequestDto electricityUsedInYearRequestDto) {
        ResponseData responseData = ResponseData.builder()
                .data(statisticalService.getElectricityUsedInYearByClientId(electricityUsedInYearRequestDto.getClientId(), electricityUsedInYearRequestDto.getYear()))
                .message("get electricity used in year by client id")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/amount-month-year")
    public ResponseEntity<?> getAmountEveryMonthInEveryYear(@RequestBody AmountEveryMonthInEveryYearRequestDto amountEveryMonthInEveryYearRequestDto) {
        ResponseData responseData = ResponseData.builder()
                .data(statisticalService.getAmountEveryMonthInEveryYear(amountEveryMonthInEveryYearRequestDto))
                .message("get electricity used in year by client id")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/consumption-from-date-to-date")
    public ResponseEntity<?> getConsumptionFromDateToDate(@RequestBody ConsumptionFromDateToDateDto consumptionFromDateToDateDto) {
        ResponseData responseData = ResponseData.builder()
                .data(statisticalService.getConsumptionFromDateToDate(consumptionFromDateToDateDto))
                .message("get getConsumptionFromDateToDate")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
}