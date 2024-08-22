package org.example.dienluc.controller;

import jakarta.validation.Valid;
import org.example.dienluc.payload.ResponseData;
import org.example.dienluc.service.EmployeeService;
import org.example.dienluc.service.dto.UpdateEmailRequest;
import org.example.dienluc.service.dto.employee.EmployeeDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing employees.
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping("")
    public ResponseEntity<?> getALlEmployees() {
        ResponseData responseData = ResponseData.builder()
                .data(employeeService.getAllEmployee())
                .message("get all employee")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("/recordable")
    public ResponseEntity<?> getRecordableEmployees() {
        ResponseData responseData = ResponseData.builder()
                .data(employeeService.getRecordableEmployees())
                .message("get employee recordable")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PostMapping("")
    public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        ResponseData responseData = ResponseData.builder()
                .data(employeeService.addEmployee(employeeDto))
                .message("get all employee")
                .status(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.ok(responseData);
    }


    @PutMapping("/{employeeId}")
    public ResponseEntity<?> updateEmployee(@PathVariable Integer employeeId, @RequestBody EmployeeDto employeeDto) {
        ResponseData responseData = ResponseData.builder()
                .data(employeeService.updateEmployee(employeeId, employeeDto))
                .message("get all employee")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PostMapping("/check-email")
    public ResponseEntity<?> checkEmailOfClientExists(@Valid @RequestBody UpdateEmailRequest updateEmailRequest) {
        ResponseData responseData = ResponseData.builder()
                .data(employeeService.checkEmailOfEmployeeExists(updateEmailRequest.getEmail()))
                .message("check email of employee exists")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("/check-phone")
    public ResponseEntity<?> checkPhoneOfEmployeeExists(@RequestParam(name = "phone") String phone) {
        ResponseData responseData = ResponseData.builder()
                .data(employeeService.checkPhoneOfEmployeeExists(phone))
                .message("check email of employee exists")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("/check-identityCard")
    public ResponseEntity<?> checkIdentityCardOfEmployeeExists(@RequestParam(name = "identityCard") String identityCard) {
        ResponseData responseData = ResponseData.builder()
                .data(employeeService.checkIdentityCardOfEmployeeExists(identityCard))
                .message("check email of employee exists")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }

}
