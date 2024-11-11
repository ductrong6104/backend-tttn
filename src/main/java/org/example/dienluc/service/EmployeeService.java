package org.example.dienluc.service;

import org.example.dienluc.entity.Employee;
import org.example.dienluc.service.dto.ResponseCheck;
import org.example.dienluc.service.dto.employee.EmployeeChooseDto;
import org.example.dienluc.service.dto.employee.EmployeeDto;
import org.example.dienluc.service.dto.employee.EmployeeGetDto;

import java.util.List;

public interface EmployeeService {
    public List<EmployeeGetDto> getAllEmployee();

    public Employee addEmployee(EmployeeDto employeeDto);

    public List<EmployeeChooseDto> getRecordableEmployees();

    public Employee updateEmployee(Integer employeeId, EmployeeDto employeeDto);

    public ResponseCheck checkEmailOfEmployeeExists(String email);

    public ResponseCheck checkPhoneOfEmployeeExists(String phone);

    public ResponseCheck checkIdentityCardOfEmployeeExists(String identityCard);

    public Employee getEmployeeById(Integer employeeId);
}
