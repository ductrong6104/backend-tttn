package org.example.dienluc.controller;

import org.example.dienluc.payload.ResponseData;
import org.example.dienluc.service.ContractService;
import org.example.dienluc.service.dto.contract.ContractCreateDto;
import org.example.dienluc.service.dto.contract.UpdatePowerMeterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.Inet4Address;
import java.nio.file.Path;

@RestController
@RequestMapping("/contracts")
public class ContractController {
    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }
    @GetMapping("")
    public ResponseEntity<?> getAllContract() {
        ResponseData responseData = ResponseData.builder()
                .data(contractService.getAllContract())
                .message("create new contract")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("/client/{clientId}/status")
    public ResponseEntity<?> getContractStatusByClientId(@PathVariable("clientId") Integer clientId) {
        ResponseData responseData = ResponseData.builder()
                .data(contractService.getContractStatusByClientId(clientId))
                .message("get contract status")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @DeleteMapping("/cancel-registers/{clientId}")
    public ResponseEntity<?> cancelRegisterByClientId(@PathVariable("clientId") Integer clientId) {
        ResponseData responseData = ResponseData.builder()
                .data(contractService.cancelRegisterByClientId(clientId))
                .message("cancel register")
                .status(HttpStatus.NO_CONTENT.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PostMapping("")
    public ResponseEntity<?> createContract(@RequestBody ContractCreateDto contractCreateDto) {
        ResponseData responseData = ResponseData.builder()
                .data(contractService.createContract(contractCreateDto))
                .message("create new contract")
                .status(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("/check-contract")
    public ResponseEntity<?> checkContractExists(@RequestParam(name = "clientId") Integer clientId) {
        ResponseData responseData = ResponseData.builder()
                .data(contractService.checkContractExists(clientId))
                .message("check contract of client exists")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("/registrationForms")
    public ResponseEntity<?> getAllRegistrationForm() {
        ResponseData responseData = ResponseData.builder()
                .data(contractService.getAllRegistrationForm())
                .message("get all registration forms")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PutMapping("/{contractId}/employee/{employeeId}")
    public ResponseEntity<?> updatePowerMeterOfContract(@PathVariable Integer contractId, @PathVariable Integer employeeId) {
        ResponseData responseData = ResponseData.builder()
                .data(contractService.updateProcessingEmployeeOfContract(contractId, employeeId))
                .message("update processing employee of contract")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PutMapping("/{contractId}/terminate")
    public ResponseEntity<?> terminateContract(@PathVariable Integer contractId) {
        ResponseData responseData = ResponseData.builder()
                .data(contractService.terminateContract(contractId))
                .message("terminate contract")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PutMapping("/{contractId}/reject/{employeeId}")
    public ResponseEntity<?> rejectContract(@PathVariable Integer contractId, @PathVariable Integer employeeId) {
        ResponseData responseData = ResponseData.builder()
                .data(contractService.rejectContract(contractId, employeeId))
                .message("reject contract")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }


}
