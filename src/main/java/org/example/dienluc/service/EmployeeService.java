package org.example.dienluc.service;

import org.example.dienluc.entity.Employee;
import org.example.dienluc.service.dto.employee.EmployeeChooseDto;
import org.example.dienluc.service.dto.employee.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    public List<EmployeeDto> getAllEmployee();

    public Employee addEmployee(EmployeeDto employeeDto);

    public List<EmployeeChooseDto> getRecordableEmployees();
}
