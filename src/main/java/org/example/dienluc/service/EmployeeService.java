package org.example.dienluc.service;

import org.example.dienluc.domain.Employee;
import org.example.dienluc.service.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    public List<EmployeeDto> getAllEmployee();

    public Employee addEmployee(EmployeeDto employeeDto);
}
