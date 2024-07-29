package org.example.dienluc.controller;

import org.example.dienluc.payload.ResponseData;
import org.example.dienluc.repository.BillRepository;
import org.example.dienluc.service.BillService;
import org.example.dienluc.service.dto.bill.BillCreateDto;
import org.example.dienluc.service.dto.bill.UpdateBillStatusRequest;
import org.example.dienluc.service.dto.electricRecording.ElectricRecordingIdRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bills")
public class BillController {
   private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping("/calculate-total-amount")
    public ResponseEntity<?> getTotalAmountByElectricRecordingId(@RequestBody ElectricRecordingIdRequest electricRecordingIdRequest) {
        ResponseData responseData = ResponseData.builder()
                .data(billService.getTotalAmountByElectricRecordingId(electricRecordingIdRequest.getElectricRecordingId()))
                .message("get total amount by electric recording")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PostMapping("")
    public ResponseEntity<?> createNewBill(@RequestBody BillCreateDto billCreateDto) {
        ResponseData responseData = ResponseData.builder()
                .data(billService.createNewBill(billCreateDto))
                .message("create bill")
                .status(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("")
    public ResponseEntity<?> getAllBill() {
        ResponseData responseData = ResponseData.builder()
                .data(billService.getAllBill())
                .message("get all bill")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getBillByClientId(@PathVariable Integer clientId) {
        ResponseData responseData = ResponseData.builder()
                .data(billService.getBillByClientId(clientId))
                .message("get bill of client")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PutMapping("/{billId}/status")
    public ResponseEntity<?> updateStatusBill(@PathVariable Integer billId, @RequestBody UpdateBillStatusRequest updateBillStatusRequest) {
        ResponseData responseData = ResponseData.builder()
                .data(billService.updateStatusBill(billId, updateBillStatusRequest.getStatus()))
                .message("get bill of client")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("/{billId}/client/{clientId}/pdf")
    public ResponseEntity<?> getBillToGeneratePdf(@PathVariable Integer billId, @PathVariable Integer clientId) {
        ResponseData responseData = ResponseData.builder()
                .data(billService.getBillToGeneratePdf(billId, clientId))
                .message("get bill of client")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
}
