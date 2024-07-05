package org.example.dienluc.controller.manager;

import jakarta.validation.Valid;
import org.example.dienluc.domain.Employee;
import org.example.dienluc.payload.ResponseData;
import org.example.dienluc.service.EmployeeService;
import org.example.dienluc.service.dto.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                .status(200)
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PostMapping("")
    public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        ResponseData responseData = ResponseData.builder()
                .data(employeeService.addEmployee(employeeDto))
                .message("get all employee")
                .status(200)
                .build();
        return ResponseEntity.ok(responseData);
    }

}
