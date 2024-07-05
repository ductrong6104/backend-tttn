package org.example.dienluc.service.serviceImpl;

import org.example.dienluc.domain.Employee;
import org.example.dienluc.repository.EmployeeRepository;
import org.example.dienluc.service.EmployeeService;
import org.example.dienluc.service.dto.EmployeeDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository1, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository1;
        this.modelMapper = modelMapper;
    }
    @Override
    public List<EmployeeDto> getAllEmployee() {
        return employeeRepository.findAll().stream()
                .map(employee -> modelMapper.map(employee, EmployeeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Employee addEmployee(EmployeeDto employeeDto) {
        return employeeRepository.save(modelMapper.map(employeeDto, Employee.class));
    }
}
